package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Localisation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Localisation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocalisationRepository extends MongoRepository<Localisation, String> {}
