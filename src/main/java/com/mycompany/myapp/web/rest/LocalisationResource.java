package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Localisation;
import com.mycompany.myapp.repository.LocalisationRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Localisation}.
 */
@RestController
@RequestMapping("/api")
public class LocalisationResource {

    private final Logger log = LoggerFactory.getLogger(LocalisationResource.class);

    private static final String ENTITY_NAME = "localisation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocalisationRepository localisationRepository;

    public LocalisationResource(LocalisationRepository localisationRepository) {
        this.localisationRepository = localisationRepository;
    }

    /**
     * {@code POST  /localisations} : Create a new localisation.
     *
     * @param localisation the localisation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new localisation, or with status {@code 400 (Bad Request)} if the localisation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/localisations")
    public ResponseEntity<Localisation> createLocalisation(@Valid @RequestBody Localisation localisation) throws URISyntaxException {
        log.debug("REST request to save Localisation : {}", localisation);
        if (localisation.getId() != null) {
            throw new BadRequestAlertException("A new localisation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Localisation result = localisationRepository.save(localisation);
        return ResponseEntity
            .created(new URI("/api/localisations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /localisations/:id} : Updates an existing localisation.
     *
     * @param id the id of the localisation to save.
     * @param localisation the localisation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated localisation,
     * or with status {@code 400 (Bad Request)} if the localisation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the localisation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/localisations/{id}")
    public ResponseEntity<Localisation> updateLocalisation(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody Localisation localisation
    ) throws URISyntaxException {
        log.debug("REST request to update Localisation : {}, {}", id, localisation);
        if (localisation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, localisation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!localisationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Localisation result = localisationRepository.save(localisation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, localisation.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /localisations/:id} : Partial updates given fields of an existing localisation, field will ignore if it is null
     *
     * @param id the id of the localisation to save.
     * @param localisation the localisation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated localisation,
     * or with status {@code 400 (Bad Request)} if the localisation is not valid,
     * or with status {@code 404 (Not Found)} if the localisation is not found,
     * or with status {@code 500 (Internal Server Error)} if the localisation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/localisations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Localisation> partialUpdateLocalisation(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody Localisation localisation
    ) throws URISyntaxException {
        log.debug("REST request to partial update Localisation partially : {}, {}", id, localisation);
        if (localisation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, localisation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!localisationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Localisation> result = localisationRepository
            .findById(localisation.getId())
            .map(existingLocalisation -> {
                if (localisation.getVille() != null) {
                    existingLocalisation.setVille(localisation.getVille());
                }
                if (localisation.getSite() != null) {
                    existingLocalisation.setSite(localisation.getSite());
                }
                if (localisation.getBatiment() != null) {
                    existingLocalisation.setBatiment(localisation.getBatiment());
                }
                if (localisation.getEtage() != null) {
                    existingLocalisation.setEtage(localisation.getEtage());
                }
                if (localisation.getBureau() != null) {
                    existingLocalisation.setBureau(localisation.getBureau());
                }
                if (localisation.getPlace() != null) {
                    existingLocalisation.setPlace(localisation.getPlace());
                }

                return existingLocalisation;
            })
            .map(localisationRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, localisation.getId())
        );
    }

    /**
     * {@code GET  /localisations} : get all the localisations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of localisations in body.
     */
    @GetMapping("/localisations")
    public List<Localisation> getAllLocalisations() {
        log.debug("REST request to get all Localisations");
        return localisationRepository.findAll();
    }

    /**
     * {@code GET  /localisations/:id} : get the "id" localisation.
     *
     * @param id the id of the localisation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the localisation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/localisations/{id}")
    public ResponseEntity<Localisation> getLocalisation(@PathVariable String id) {
        log.debug("REST request to get Localisation : {}", id);
        Optional<Localisation> localisation = localisationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(localisation);
    }

    /**
     * {@code DELETE  /localisations/:id} : delete the "id" localisation.
     *
     * @param id the id of the localisation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/localisations/{id}")
    public ResponseEntity<Void> deleteLocalisation(@PathVariable String id) {
        log.debug("REST request to delete Localisation : {}", id);
        localisationRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
