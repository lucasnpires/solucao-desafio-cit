package br.com.lucas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

	public ResponseEntity<List<Job>> findAll() {
		List<Job> listJobs = new ArrayList<Job>();
		
		try {
			 listJobs = jobRepository.findAll();
		}catch (Exception e) {
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
}
