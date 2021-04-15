package br.com.lucas.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.lucas.domain.Job;
import br.com.lucas.domain.RecuperarListDTO;
import br.com.lucas.domain.request.IntervaloExecucaoRequest;
import br.com.lucas.domain.request.JobRequest;
import br.com.lucas.domain.response.IntervalExecutionResponse;
import br.com.lucas.domain.response.JobResponse;
import br.com.lucas.repository.JobRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JobService {

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private JobUtil jobUtil;

	public ResponseEntity<List<Job>> findAll() {
		List<Job> listJobs = new ArrayList<Job>();

		try {
			listJobs = jobRepository.findAll();
		} catch (Exception e) {
			log.error(e.getMessage());
			new ResponseEntity<List<Job>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<Job>>(listJobs, HttpStatus.OK);
	}

	public ResponseEntity<Job> addJob(@Valid JobRequest jobRequest) {
		Job save = new Job();

		try {
			save = jobRepository.save(jobUtil.convertJobRequestToJob(jobRequest));
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<Job>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Job>(save, HttpStatus.CREATED);
	}

	public ResponseEntity<Void> deleteJob(String id) {
		Job jobFindById = jobRepository.findById(id).orElse(new Job());

		if (Objects.isNull(jobFindById.getId())) {
			log.info("Não existe job com este id {} para deletar", id);
			return new ResponseEntity<Void>(HttpStatus.BAD_GATEWAY);
		} else {
			jobRepository.delete(jobFindById);
		}
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	public ResponseEntity<IntervalExecutionResponse> findJobsPorIntervaloExecucao(
			IntervaloExecucaoRequest intervaloExecucaoRequest) {
		return new ResponseEntity<IntervalExecutionResponse>(
				IntervalExecutionResponse.builder().jobsASeremExecutados(getListJobs()).build(), HttpStatus.OK);
	}

	private List<List<JobResponse>> getListJobs() {
		List<List<JobResponse>> lista = new LinkedList<List<JobResponse>>();

		List<Job> jobs = jobRepository.findAll();
		
		List<Integer> listTempo = carregarListaTempo(jobs);
		
		Integer totalHoras = listTempo
		        .stream()
		        .reduce(Integer::sum)
		        .get();
		
		Integer qtdListas = (totalHoras / 8) + 1;
		
		Integer indice = 0;
		
		for(int contListas = 0; contListas < qtdListas; contListas++) {
			RecuperarListDTO recuperarList = new RecuperarListDTO();
			recuperarList = recuperarList(indice, jobs);
			indice = recuperarList.getIndice();
			lista.add(recuperarList.getLista());
		}

		return lista;
	}
	
	private RecuperarListDTO recuperarList(Integer indice, List<Job> jobs) {
		Integer soma = 0;
		Integer indiceFinal = 0; 
		List<JobResponse> listRetorno = new ArrayList<JobResponse>();
		RecuperarListDTO retorno = new RecuperarListDTO();
		
		for(int i = indice; i < jobs.size(); i++) {
			Job job = jobs.get(i);
			soma = soma + job.getTempoEstimado();
			
			if(soma <= 8) {
				listRetorno.add(JobResponse.builder().sequencialJob(job.getSequencial()).build());
			} else {
				indiceFinal = i;
				break;				
			} 
		}
		
		retorno.setIndice(indiceFinal);
		retorno.setLista(listRetorno);
		return retorno;
	}
	
	private List<Integer> carregarListaTempo(List<Job> jobs) {
		List<Integer> listTempo = new ArrayList<Integer>();
		
		for(Job job : jobs) {
			listTempo.add(job.getTempoEstimado());
		}
		return listTempo;	
	}


	public Integer getMaxSequencial() {
		Integer max=0;
		Job job = jobRepository.findFirstByOrderBySequencialDesc();
		
		if(job == null) {
			max=1;
		}
		
		max = job.getSequencial()+1;
		
		log.info("maxSequencial: {}",max);
		
		return max;
	}
	
	public Integer getSequencial() {
		return getMaxSequencial();
	}
}
