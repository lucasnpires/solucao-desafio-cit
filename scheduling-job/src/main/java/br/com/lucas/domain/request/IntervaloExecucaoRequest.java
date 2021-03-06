package br.com.lucas.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "IntervaloExecucao", description = "Request de filtragem do intervalo de execução do Job")
public class IntervaloExecucaoRequest {
	
	@ApiModelProperty(value = "Data Inicio", example = "2019-11-10", required = true, position = 1)
	private String dtInicio;
	
	@ApiModelProperty(value = "Hora Inicio", example = "09:00:00", required = true, position = 2)
	private String horaInicio;
	
	@ApiModelProperty(value = "Data Fim", example = "2019-11-11", required = true, position = 3)
	private String dtFim;
	
	@ApiModelProperty(value = "Hora Fim", example = "12:00:00", required = true, position = 4)
	private String horaFim;

}
