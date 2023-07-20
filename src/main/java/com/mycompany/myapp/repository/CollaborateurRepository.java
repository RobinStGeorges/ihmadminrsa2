package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Collaborateur;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Collaborateur entity.
 */
@Repository
public interface CollaborateurRepository extends MongoRepository<Collaborateur, String> {
    @Query("{}")
    Page<Collaborateur> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Collaborateur> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Collaborateur> findOneWithEagerRelationships(String id);
}
