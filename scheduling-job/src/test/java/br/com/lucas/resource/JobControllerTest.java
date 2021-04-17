package br.com.lucas.resource;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.lucas.domain.Job;
import br.com.lucas.domain.request.IntervaloExecucaoRequest;
import br.com.lucas.domain.request.JobRequest;
import br.com.lucas.domain.response.IntervalExecutionResponse;
import br.com.lucas.service.JobService;

@RunWith(MockitoJUnitRunner.class)
public class JobControllerTest {

	public static final String ID_JOB = "dadas34323fdfsdf";
	public static final String DESCRICAO_JOB = "Importação de dados de integração";
	public static final String DT_MAX_CONCLUSAO = "2019-11-11 08:00:00";
	public static final Integer TEMPO_ESTIMADO = 2;
	public static final Integer SEQUENCIAL = 1;

	public static final String DT_INICIO_REQUEST = "2019-11-10";
	public static final String HORA_INICIO_REQUEST = "09:00:00";
	public static final String DT_FIM_REQUEST = "2019-11-11";
	public static final String HORA_FIM_REQUEST = "12:00:00";

	@Mock
	private JobService service;

	@InjectMocks
	private JobController jobController;
	private JobRequest jobRequest;
	private IntervaloExecucaoRequest intervaloExecucaoRequest;

	@Before
	public void setUp() {
		jobRequest = JobRequest.builder().descricao(DESCRICAO_JOB).dtMaxConclusao(DT_MAX_CONCLUSAO)
				.tempoEstimado(TEMPO_ESTIMADO).build();
		
		when(service.addJob(eq(jobRequest))).thenReturn(new ResponseEntity<Job>(HttpStatus.OK));

		intervaloExecucaoRequest = IntervaloExecucaoRequest.builder().dtFim(DT_FIM_REQUEST).dtInicio(DT_INICIO_REQUEST)
				.horaInicio(HORA_INICIO_REQUEST).horaFim(HORA_FIM_REQUEST).build();

		when(service.findJobsPorIntervaloExecucao(eq(intervaloExecucaoRequest)))
				.thenReturn(new ResponseEntity<IntervalExecutionResponse>(HttpStatus.OK));
	}

	@Test
	public void deveChamarFluxoDeAdicionarJob() {
		this.jobController.addJob(jobRequest);

		ArgumentCaptor<JobRequest> jobRequestModelArgumentCaptor = ArgumentCaptor.forClass(JobRequest.class);

		verify(service).addJob(jobRequestModelArgumentCaptor.capture());

		assertThat(jobRequestModelArgumentCaptor.getValue()).isEqualToComparingFieldByField(jobRequest);
	}

	@Test
	public void deveChamarFluxoDeBuscarJobs() {

		Job JobResponse = criarJob();

		when(service.findAll()).thenReturn(new ResponseEntity<List<Job>>(singletonList(JobResponse), HttpStatus.OK));

		ResponseEntity<List<Job>> buscarTodasBasesResponse = this.jobController.consultar();

		assertThat(buscarTodasBasesResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		assertThat(buscarTodasBasesResponse.getBody().get(0)).isEqualToComparingFieldByField(JobResponse);

	}

	@Test
	public void deveChamarFluxoDeDeletarUmJob() {

		this.jobController.deleteJob(ID_JOB);

		ArgumentCaptor<String> idJobCaptor = ArgumentCaptor.forClass(String.class);

		verify(service).deleteJob(idJobCaptor.capture());

		assertThat(idJobCaptor.getValue()).isEqualTo(ID_JOB);
	}

	@Test
	public void deveChamarFluxoDeBuscarPorIntervaloExecucao() {
		this.jobController.findJobsPorIntervaloExecucao(intervaloExecucaoRequest);

		ArgumentCaptor<IntervaloExecucaoRequest> intervaloExecucaoRequestModelArgumentCaptor = ArgumentCaptor
				.forClass(IntervaloExecucaoRequest.class);

		verify(service).findJobsPorIntervaloExecucao(intervaloExecucaoRequestModelArgumentCaptor.capture());
		
		assertThat(intervaloExecucaoRequestModelArgumentCaptor.getValue()).isEqualToComparingFieldByField(intervaloExecucaoRequest);
	}

	private Job criarJob() {
		return Job.builder().id(ID_JOB).descricao(DESCRICAO_JOB).dtMaxConclusao(DT_MAX_CONCLUSAO).sequencial(SEQUENCIAL)
				.tempoEstimado(TEMPO_ESTIMADO).build();
	}
}
