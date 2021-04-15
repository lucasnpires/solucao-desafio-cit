package br.com.lucas.resource;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucas.domain.Job;
import br.com.lucas.domain.request.IntervaloExecucaoRequest;
import br.com.lucas.domain.request.JobRequest;
import br.com.lucas.domain.response.IntervalExecutionResponse;
import br.com.lucas.service.JobService;

@RestController
@RequestMapping(value = "/jobs", produces = APPLICATION_JSON_VALUE)
public class JobController implements JobDefinition {
	
	@Autowired
	private JobService jobService;
	
    @GetMapping
    @Override
	public ResponseEntity<List<Job>> consultar() {
		return jobService.findAll();
	}
	
	
    @PostMapping
    @Override
	public ResponseEntity<Job> addJob(@Valid @RequestBody JobRequest jobRequest) {
		return jobService.addJob(jobRequest);
	}


    @DeleteMapping
    @Override
	public ResponseEntity<Void> deleteJob(String codigo) {
    	return jobService.deleteJob(codigo);
	}

    
    @PostMapping(value = "/execution")
	@Override
	public ResponseEntity<IntervalExecutionResponse> findJobsPorIntervaloExecucao(@Valid @RequestBody IntervaloExecucaoRequest intervaloExecucaoRequest) {
		return jobService.findJobsPorIntervaloExecucao(intervaloExecucaoRequest);
	}
}
