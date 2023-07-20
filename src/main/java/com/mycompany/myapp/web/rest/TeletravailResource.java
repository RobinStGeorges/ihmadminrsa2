package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Teletravail;
import com.mycompany.myapp.repository.TeletravailRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Teletravail}.
 */
@RestController
@RequestMapping("/api")
public class TeletravailResource {

    private final Logger log = LoggerFactory.getLogger(TeletravailResource.class);

    private static final String ENTITY_NAME = "teletravail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TeletravailRepository teletravailRepository;

    public TeletravailResource(TeletravailRepository teletravailRepository) {
        this.teletravailRepository = teletravailRepository;
    }

    /**
     * {@code POST  /teletravails} : Create a new teletravail.
     *
     * @param teletravail the teletravail to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new teletravail, or with status {@code 400 (Bad Request)} if the teletravail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/teletravails")
    public ResponseEntity<Teletravail> createTeletravail(@Valid @RequestBody Teletravail teletravail) throws URISyntaxException {
        log.debug("REST request to save Teletravail : {}", teletravail);
        if (teletravail.getId() != null) {
            throw new BadRequestAlertException("A new teletravail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Teletravail result = teletravailRepository.save(teletravail);
        return ResponseEntity
            .created(new URI("/api/teletravails/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /teletravails/:id} : Updates an existing teletravail.
     *
     * @param id the id of the teletravail to save.
     * @param teletravail the teletravail to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teletravail,
     * or with status {@code 400 (Bad Request)} if the teletravail is not valid,
     * or with status {@code 500 (Internal Server Error)} if the teletravail couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/teletravails/{id}")
    public ResponseEntity<Teletravail> updateTeletravail(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody Teletravail teletravail
    ) throws URISyntaxException {
        log.debug("REST request to update Teletravail : {}, {}", id, teletravail);
        if (teletravail.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, teletravail.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!teletravailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Teletravail result = teletravailRepository.save(teletravail);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, teletravail.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /teletravails/:id} : Partial updates given fields of an existing teletravail, field will ignore if it is null
     *
     * @param id the id of the teletravail to save.
     * @param teletravail the teletravail to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teletravail,
     * or with status {@code 400 (Bad Request)} if the teletravail is not valid,
     * or with status {@code 404 (Not Found)} if the teletravail is not found,
     * or with status {@code 500 (Internal Server Error)} if the teletravail couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/teletravails/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Teletravail> partialUpdateTeletravail(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody Teletravail teletravail
    ) throws URISyntaxException {
        log.debug("REST request to partial update Teletravail partially : {}, {}", id, teletravail);
        if (teletravail.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, teletravail.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!teletravailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Teletravail> result = teletravailRepository
            .findById(teletravail.getId())
            .map(existingTeletravail -> {
                if (teletravail.getIpDGFIPFixe() != null) {
                    existingTeletravail.setIpDGFIPFixe(teletravail.getIpDGFIPFixe());
                }
                if (teletravail.getIpTeletravail() != null) {
                    existingTeletravail.setIpTeletravail(teletravail.getIpTeletravail());
                }
                if (teletravail.getIpIPSEC() != null) {
                    existingTeletravail.setIpIPSEC(teletravail.getIpIPSEC());
                }
                if (teletravail.getIsActif() != null) {
                    existingTeletravail.setIsActif(teletravail.getIsActif());
                }

                return existingTeletravail;
            })
            .map(teletravailRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, teletravail.getId())
        );
    }

    /**
     * {@code GET  /teletravails} : get all the teletravails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of teletravails in body.
     */
    @GetMapping("/teletravails")
    public List<Teletravail> getAllTeletravails() {
        log.debug("REST request to get all Teletravails");
        return teletravailRepository.findAll();
    }

    /**
     * {@code GET  /teletravails/:id} : get the "id" teletravail.
     *
     * @param id the id of the teletravail to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the teletravail, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/teletravails/{id}")
    public ResponseEntity<Teletravail> getTeletravail(@PathVariable String id) {
        log.debug("REST request to get Teletravail : {}", id);
        Optional<Teletravail> teletravail = teletravailRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(teletravail);
    }

    /**
     * {@code DELETE  /teletravails/:id} : delete the "id" teletravail.
     *
     * @param id the id of the teletravail to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/teletravails/{id}")
    public ResponseEntity<Void> deleteTeletravail(@PathVariable String id) {
        log.debug("REST request to delete Teletravail : {}", id);
        teletravailRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
