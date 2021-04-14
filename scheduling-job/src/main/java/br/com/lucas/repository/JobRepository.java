package br.com.lucas.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.lucas.domain.Job;


@Repository
public interface JobRepository extends MongoRepository<Job, String> {
	
	//@Aggregation(pipeline = { "{$group: { _id: '', total: {$max: $sequencial }}}" })
	public Job findFirstByOrderBySequencialDesc();

	//public Job findTopByOrderBySequencialDesc();
}
