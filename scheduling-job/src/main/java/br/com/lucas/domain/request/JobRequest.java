package br.com.lucas.domain.request;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

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
@ApiModel(value = "JobRequest", description = "Request do job para ser persistido")
public class JobRequest {

    @NotEmpty
    @ApiModelProperty(value = "Descrição do Job. Ex.: Importação de dados de integração", example = "Importação de dados de integração", required = true)
	private String descricao;

    @ApiModelProperty(value = "Data Máxima de Conclusão do Job. Ex.: 2019-11-11 08:00:00", example = "2019-11-11 08:00:00", required = true)
    @NotEmpty
	private String dtMaxConclusao;

    @ApiModelProperty(value = "Tempo estimado de execução do Job. Ex.: 2", example = "2", required = true)
    @NotEmpty
	private Integer tempoEstimado;

}
