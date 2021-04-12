package br.com.lucas.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.lucas.domain.Job;
import br.com.lucas.domain.request.JobRequest;
import br.com.lucas.repository.JobRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JobService {
	
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private JobUtil jobUtil;

	public List<Job> findAll() {
		return jobRepository.findAll();
	}

	public Job addJob(@Valid JobRequest jobRequest) {
		return jobRepository.save(jobUtil.convertJobRequestToJob(jobRequest));
	}

	public ResponseEntity<Void> deleteJob(String id) {
		Job jobFindById = jobRepository.findById(id).orElse(new Job());
		
		if(Objects.isNull(jobFindById.getId())) {
			log.info("Não existe job com este id {} para deletar", id);
			return new ResponseEntity<Void>(HttpStatus.BAD_GATEWAY);
		} else {
			jobRepository.delete(jobFindById);			
		}			
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
