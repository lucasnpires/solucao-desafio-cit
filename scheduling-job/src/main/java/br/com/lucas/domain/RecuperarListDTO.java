package br.com.lucas.domain;

import java.util.List;

import br.com.lucas.domain.response.JobResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RecuperarListDTO {
	
	private Integer indice;
	
	private List<JobResponse> lista;

}
