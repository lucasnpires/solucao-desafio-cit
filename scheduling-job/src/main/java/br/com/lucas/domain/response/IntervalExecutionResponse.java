package br.com.lucas.domain.response;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
@Data
public class IntervalExecutionResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	
	//private List<Job> jobs;
	
	private List<List<JobResponse>> jobs;

}
