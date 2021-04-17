package br.com.lucas.service;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.Test;
import java.util.ArrayList;
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

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertThrows;
import static java.util.Arrays.asList;
import static java.util.Optional.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import br.com.lucas.domain.Job;
import br.com.lucas.domain.request.IntervaloExecucaoRequest;
import br.com.lucas.domain.request.JobRequest;
import br.com.lucas.domain.response.IntervalExecutionResponse;
import br.com.lucas.service.JobService;

import br.com.lucas.repository.JobRepository;

@RunWith(MockitoJUnitRunner.class)
public class JobServiceTest {

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
	private JobRepository repository;

	@Mock
	private JobUtil jobUtil;

	@InjectMocks
	private JobService service;

	private Job jobDatabase;
	private JobRequest jobRequest;
	private IntervaloExecucaoRequest intervaloExecucaoRequest;
	private Job jobUm;
	private Job jobDois;
	private List<Job> jobsDBResponse;

	@Before
	public void setUp() {
		jobDatabase = Job.builder().descricao(DESCRICAO_JOB).dtMaxConclusao(DT_MAX_CONCLUSAO).id(ID_JOB)
				.sequencial(SEQUENCIAL).tempoEstimado(TEMPO_ESTIMADO).build();

		jobRequest = JobRequest.builder().descricao(DESCRICAO_JOB).dtMaxConclusao(DT_MAX_CONCLUSAO)
				.tempoEstimado(TEMPO_ESTIMADO).build();
		
		intervaloExecucaoRequest = IntervaloExecucaoRequest.builder().dtFim(DT_FIM_REQUEST).dtInicio(DT_INICIO_REQUEST)
				.horaInicio(HORA_INICIO_REQUEST).horaFim(HORA_FIM_REQUEST).build();
		
		jobUm = Job.builder().descricao("jobUm").dtMaxConclusao(DT_MAX_CONCLUSAO).id("id1").sequencial(1)
				.tempoEstimado(TEMPO_ESTIMADO).build();

		jobDois = Job.builder().descricao("jobDois").dtMaxConclusao(DT_MAX_CONCLUSAO).id("id2").sequencial(2)
				.tempoEstimado(TEMPO_ESTIMADO).build();
		
		jobsDBResponse = asList(jobUm, jobDois);

	}

	@Test
	public void deveSalvarJob() {
		when(repository.save(any(Job.class))).thenReturn(jobDatabase);

		when(jobUtil.convertJobRequestToJob(any(JobRequest.class))).thenReturn(jobDatabase);

		this.service.addJob(jobRequest);

		ArgumentCaptor<Job> jobArgumentCapture = ArgumentCaptor.forClass(Job.class);

		verify(repository).save(jobArgumentCapture.capture());

		Job jobCaptured = jobArgumentCapture.getValue();

		assertThat(jobCaptured.getDescricao()).isEqualTo(jobRequest.getDescricao());

		assertThat(jobCaptured.getDtMaxConclusao()).isEqualTo(jobRequest.getDtMaxConclusao());

		assertThat(jobCaptured.getTempoEstimado()).isEqualTo(jobRequest.getTempoEstimado());

		assertThat(jobCaptured.getCheckValue()).isEqualTo(jobRequest.getCheckValue());
	}

	@Test(expected = Exception.class)
	public void deveLancarExcecaoAoSalvarJob() {
		when(repository.save(any(Job.class))).thenThrow(Exception.class);
	}

	@Test
	public void deveListarTodosJobs() {
		
		when(repository.findAll()).thenReturn(jobsDBResponse);

		ResponseEntity<List<Job>> responseEntity = this.service.findAll();

		assertThat(responseEntity.getBody()).isNotNull();
		assertThat(responseEntity.getBody()).isNotEmpty();
		assertThat(responseEntity.getBody().get(0).getId()).isEqualTo(jobUm.getId());
		assertThat(responseEntity.getBody().get(0).getDescricao()).isSameAs(jobUm.getDescricao());

	}

	@Test
	public void deveDeletarUmJob() {

		when(repository.findById(eq(ID_JOB))).thenReturn(of(jobDatabase));

		this.service.deleteJob(ID_JOB);

		ArgumentCaptor<Job> jobArgumentCaptor = ArgumentCaptor.forClass(Job.class);

		verify(repository).delete(jobArgumentCaptor.capture());

		assertThat(jobArgumentCaptor.getValue()).isSameAs(jobDatabase);

	}

	@Test
	public void deveRetornarJobsPorIntervaloExecucao() {
		
		when(repository.findAll()).thenReturn(jobsDBResponse);
		
		this.service.findJobsPorIntervaloExecucao(intervaloExecucaoRequest);

	}
}
