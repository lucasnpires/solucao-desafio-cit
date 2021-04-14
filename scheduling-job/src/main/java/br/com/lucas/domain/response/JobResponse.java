package br.com.lucas.domain.response;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Data
public class JobResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer sequencialJob;
}
