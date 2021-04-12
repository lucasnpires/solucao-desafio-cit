package br.com.lucas.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.lucas.domain.Job;

public interface JobRepository extends MongoRepository<Job, String> {

}
