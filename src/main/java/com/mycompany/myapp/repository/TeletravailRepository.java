package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Teletravail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Teletravail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeletravailRepository extends MongoRepository<Teletravail, String> {}
