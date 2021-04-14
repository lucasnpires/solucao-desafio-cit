package br.com.lucas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import br.com.lucas.domain.Job;
import br.com.lucas.domain.response.JobResponse;

public class TestesListas {
	
	
	public static void main(String[] args) {
		List<Job> jobsBase = new ArrayList<Job>();	
		
		
		List<JobResponse> jobsResponse = new ArrayList<JobResponse>();
		List<JobResponse> jobsResponseAux = new ArrayList<JobResponse>();
		Map<Integer, List<JobResponse>> mapSaida = new HashMap<Integer, List<JobResponse>>();
		
		jobsBase = carregarListaJobs();		
		
		Integer somaHoras = 0;
		
		
		for(int i = jobsBase.size() -1; i >= 0 ; i--) {
			Job job = new Job();
			job = jobsBase.get(i);
			
			somaHoras = somaHoras + job.getTempoEstimado();		
			
			if(somaHoras > 8) {	
				jobsResponseAux.add(JobResponse.builder().sequencialJob(job.getSequencial()).build());				
			} else {
				jobsResponse.add(JobResponse.builder().sequencialJob(job.getSequencial()).build());		
				jobsBase.remove(i);
			}
			
			System.out.println("somaHoras: "+somaHoras);
		}
		System.out.println("jobsResponse.size(): "+jobsResponse.size());
		System.out.println("jobsResponseAux.size(): "+jobsResponseAux.size());
	}

	private static List<Job> carregarListaJobs() {
		List<Job> retorno = new ArrayList<Job>();
		Job job = new Job();
		
		job  = Job.builder().dtMaxConclusao("2019-11-10 12:00:00").id("43423432423423432").sequencial(1).descricao("Importação de arquivos de fundos").tempoEstimado(2).build();
		retorno.add(job);
		
		job = Job.builder().dtMaxConclusao("2019-11-11 12:00:00").id("fdfdfsdfsdfsdfsdfs").sequencial(2).descricao("Importação de dados da Base Legada").tempoEstimado(4).build();
		retorno.add(job);
		
		job = Job.builder().dtMaxConclusao("2019-11-11 08:00:00").id("423redrefrt4234432").sequencial(3).descricao("Importação de dados de integração").tempoEstimado(6).build();
		retorno.add(job);
		return retorno;
	}

}
