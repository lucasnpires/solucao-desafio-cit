package br.com.lucas.resource;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.springframework.http.ResponseEntity;

import br.com.lucas.domain.Job;
import br.com.lucas.domain.request.JobRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "/jobs", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE,  tags = { "jobs" })
public interface JobDefinition {
	
	
	@ApiOperation(value = "Consultar Job Schedulling",
            notes = "Consultar Job",
            httpMethod = "GET")
	public ResponseEntity<List<Job>> consultar();
	
	@ApiOperation(value = "Adicionar um Job",
            notes = "Adicionar Job",
            httpMethod = "POST")
	public ResponseEntity<Job> addJob(JobRequest jobRequest);
	
	@ApiOperation(value = "Deletar um Job",
            notes = "Deletar um Job",
            httpMethod = "DELETE")
	public ResponseEntity<Void> deleteJob(String codigo);

}
