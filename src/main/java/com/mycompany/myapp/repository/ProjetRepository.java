package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Projet;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Projet entity.
 */
@Repository
public interface ProjetRepository extends MongoRepository<Projet, String> {
    @Query("{}")
    Page<Projet> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Projet> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Projet> findOneWithEagerRelationships(String id);
}
