package br.com.lucas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucas.domain.Job;
import br.com.lucas.domain.request.JobRequest;

@Service
public class JobUtil {
	
	@Autowired
	private JobService jobService;

	public Job convertJobRequestToJob(JobRequest jobRequest) {
		return Job.builder().descricao(jobRequest.getDescricao()).sequencial(jobService.getSequencial()).dtMaxConclusao(jobRequest.getDtMaxConclusao())
				.tempoEstimado(jobRequest.getTempoEstimado()).build();
	}

	

}
