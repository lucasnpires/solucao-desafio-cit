package br.com.lucas.service;

import org.springframework.stereotype.Service;

import br.com.lucas.domain.Job;
import br.com.lucas.domain.request.JobRequest;

@Service
public class JobUtil {

	public Job convertJobRequestToJob(JobRequest jobRequest) {
		return Job.builder().descricao(jobRequest.getDescricao()).dtMaxConclusao(jobRequest.getDtMaxConclusao())
				.tempoEstimado(jobRequest.getTempoEstimado()).build();
	}

}
