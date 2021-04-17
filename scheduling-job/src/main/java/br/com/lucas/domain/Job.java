package br.com.lucas.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document
public class Job {

	@Id
	private String id;

	private Integer sequencial;

	private String descricao;

	private String dtMaxConclusao;

	private Integer tempoEstimado;

	@JsonProperty("checkValue")
	private String checkValue;

	/**
	 * Check Value da chave.
	 *
	 * @return checkValue
	 **/
	@ApiModelProperty(example = "08XDE7", value = "Check Value da chave. ")
	public String getCheckValue() {
		return checkValue;
	}

}
