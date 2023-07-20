package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Equipement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Equipement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EquipementRepository extends MongoRepository<Equipement, String> {}
