package br.com.lucas.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.lucas.domain.Job;
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
				IntervalExecutionResponse.builder().jobs(getListJobs()).build(), HttpStatus.OK);
	}

	private List<List<JobResponse>> getListJobs() {
		List<List<JobResponse>> lista = new LinkedList<List<JobResponse>>();

		List<JobResponse> listaResponse = new ArrayList<JobResponse>();
		List<JobResponse> listaResponseRestante = new ArrayList<JobResponse>();

		List<Job> jobs = jobRepository.findAll();

//		jobs.forEach(job -> {
//			horas.add(job.getTempoEstimado());
//		});

		// dentro de cada lista pode ter no máximo 8 horas de duração de jobs

//		Double qtdListas = somaHora / 8;
//		log.info("soma hora: {}", somaHora);
//		log.info("qtd listas: {}", qtdListas);
		
		
		//Map<Integer, List<JobResponse>> map = new HashMap<Integer, List<JobResponse>>();
		//Integer contador=1;
		
		Double somaHora = 0d;
		
		for(Job job: jobs) {
			
			//map.put(contador, listaResponse);
			
					
			//getSomaHoras(horas);
			
			somaHora = somaHora + job.getTempoEstimado();
			
			log.info("somaHora: {}", somaHora);
			
			if(somaHora > 8) {
				listaResponseRestante.add(JobResponse.builder().sequencialJob(job.getSequencial()).build());
			} else {
				listaResponse.add(JobResponse.builder().sequencialJob(job.getSequencial()).build());			
			} 
		}

		log.info("tamanho lista restante: {}",listaResponseRestante.size());
		log.info("tamanho lista: {}",listaResponse.size());
		lista.add(listaResponse);
		lista.add(listaResponseRestante);

		return lista;
	}

	private Double getSomaHoras(List<Long> horas) {
		Long somaHora = 0L;

		for (Long hora : horas) {
			somaHora = somaHora + hora;
		}

		return ((Number) somaHora).doubleValue();
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
