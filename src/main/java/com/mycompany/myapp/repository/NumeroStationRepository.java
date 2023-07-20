package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.NumeroStation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the NumeroStation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NumeroStationRepository extends MongoRepository<NumeroStation, String> {}
