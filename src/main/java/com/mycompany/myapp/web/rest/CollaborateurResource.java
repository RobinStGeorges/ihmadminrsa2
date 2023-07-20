package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Collaborateur;
import com.mycompany.myapp.repository.CollaborateurRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Collaborateur}.
 */
@RestController
@RequestMapping("/api")
public class CollaborateurResource {

    private final Logger log = LoggerFactory.getLogger(CollaborateurResource.class);

    private static final String ENTITY_NAME = "collaborateur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CollaborateurRepository collaborateurRepository;

    public CollaborateurResource(CollaborateurRepository collaborateurRepository) {
        this.collaborateurRepository = collaborateurRepository;
    }

    /**
     * {@code POST  /collaborateurs} : Create a new collaborateur.
     *
     * @param collaborateur the collaborateur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new collaborateur, or with status {@code 400 (Bad Request)} if the collaborateur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/collaborateurs")
    public ResponseEntity<Collaborateur> createCollaborateur(@Valid @RequestBody Collaborateur collaborateur) throws URISyntaxException {
        log.debug("REST request to save Collaborateur : {}", collaborateur);
        if (collaborateur.getId() != null) {
            throw new BadRequestAlertException("A new collaborateur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Collaborateur result = collaborateurRepository.save(collaborateur);
        return ResponseEntity
            .created(new URI("/api/collaborateurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /collaborateurs/:id} : Updates an existing collaborateur.
     *
     * @param id the id of the collaborateur to save.
     * @param collaborateur the collaborateur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collaborateur,
     * or with status {@code 400 (Bad Request)} if the collaborateur is not valid,
     * or with status {@code 500 (Internal Server Error)} if the collaborateur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/collaborateurs/{id}")
    public ResponseEntity<Collaborateur> updateCollaborateur(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody Collaborateur collaborateur
    ) throws URISyntaxException {
        log.debug("REST request to update Collaborateur : {}, {}", id, collaborateur);
        if (collaborateur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, collaborateur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!collaborateurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Collaborateur result = collaborateurRepository.save(collaborateur);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, collaborateur.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /collaborateurs/:id} : Partial updates given fields of an existing collaborateur, field will ignore if it is null
     *
     * @param id the id of the collaborateur to save.
     * @param collaborateur the collaborateur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collaborateur,
     * or with status {@code 400 (Bad Request)} if the collaborateur is not valid,
     * or with status {@code 404 (Not Found)} if the collaborateur is not found,
     * or with status {@code 500 (Internal Server Error)} if the collaborateur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/collaborateurs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Collaborateur> partialUpdateCollaborateur(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody Collaborateur collaborateur
    ) throws URISyntaxException {
        log.debug("REST request to partial update Collaborateur partially : {}, {}", id, collaborateur);
        if (collaborateur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, collaborateur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!collaborateurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Collaborateur> result = collaborateurRepository
            .findById(collaborateur.getId())
            .map(existingCollaborateur -> {
                if (collaborateur.getIdentifiant() != null) {
                    existingCollaborateur.setIdentifiant(collaborateur.getIdentifiant());
                }
                if (collaborateur.getMatricule() != null) {
                    existingCollaborateur.setMatricule(collaborateur.getMatricule());
                }
                if (collaborateur.getSociete() != null) {
                    existingCollaborateur.setSociete(collaborateur.getSociete());
                }
                if (collaborateur.getEmail() != null) {
                    existingCollaborateur.setEmail(collaborateur.getEmail());
                }
                if (collaborateur.getTel() != null) {
                    existingCollaborateur.setTel(collaborateur.getTel());
                }
                if (collaborateur.getProfil() != null) {
                    existingCollaborateur.setProfil(collaborateur.getProfil());
                }
                if (collaborateur.getDateEntree() != null) {
                    existingCollaborateur.setDateEntree(collaborateur.getDateEntree());
                }
                if (collaborateur.getDateSortie() != null) {
                    existingCollaborateur.setDateSortie(collaborateur.getDateSortie());
                }
                if (collaborateur.getStatut() != null) {
                    existingCollaborateur.setStatut(collaborateur.getStatut());
                }
                if (collaborateur.getCommentaire() != null) {
                    existingCollaborateur.setCommentaire(collaborateur.getCommentaire());
                }

                return existingCollaborateur;
            })
            .map(collaborateurRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, collaborateur.getId())
        );
    }

    /**
     * {@code GET  /collaborateurs} : get all the collaborateurs.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of collaborateurs in body.
     */
    @GetMapping("/collaborateurs")
    public List<Collaborateur> getAllCollaborateurs(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Collaborateurs");
        if (eagerload) {
            return collaborateurRepository.findAllWithEagerRelationships();
        } else {
            return collaborateurRepository.findAll();
        }
    }

    /**
     * {@code GET  /collaborateurs/:id} : get the "id" collaborateur.
     *
     * @param id the id of the collaborateur to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the collaborateur, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/collaborateurs/{id}")
    public ResponseEntity<Collaborateur> getCollaborateur(@PathVariable String id) {
        log.debug("REST request to get Collaborateur : {}", id);
        Optional<Collaborateur> collaborateur = collaborateurRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(collaborateur);
    }

    /**
     * {@code DELETE  /collaborateurs/:id} : delete the "id" collaborateur.
     *
     * @param id the id of the collaborateur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/collaborateurs/{id}")
    public ResponseEntity<Void> deleteCollaborateur(@PathVariable String id) {
        log.debug("REST request to delete Collaborateur : {}", id);
        collaborateurRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
