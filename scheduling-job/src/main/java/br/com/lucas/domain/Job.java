package br.com.lucas.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Job {
	
	@Id
	private String id;		

	private String descricao;
	
	private Date dtMaxConclusao;
	
	private Long tempoEstimado;
	
}
