package br.com.lucas.resource;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucas.domain.Job;
import br.com.lucas.domain.request.JobRequest;
import br.com.lucas.service.JobService;

@RestController
@RequestMapping(value = "/jobs", produces = APPLICATION_JSON_VALUE)
public class JobController implements JobDefinition {
	
	@Autowired
	private JobService jobService;
	
    @GetMapping
    @Override
	public ResponseEntity<List<Job>> consultar() {
		return new ResponseEntity<List<Job>>(jobService.findAll(), HttpStatus.OK);
	}
	
	
    @PostMapping
    @Override
	public ResponseEntity<Job> addJob(@Valid @RequestBody JobRequest jobRequest) {
		return new ResponseEntity<Job>(jobService.addJob(jobRequest), HttpStatus.CREATED);
	}


    @DeleteMapping
    @Override
	public ResponseEntity<Void> deleteJob(String codigo) {
    	return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}