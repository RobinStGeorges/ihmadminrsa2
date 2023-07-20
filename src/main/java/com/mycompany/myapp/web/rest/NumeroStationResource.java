package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.NumeroStation;
import com.mycompany.myapp.repository.NumeroStationRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.NumeroStation}.
 */
@RestController
@RequestMapping("/api")
public class NumeroStationResource {

    private final Logger log = LoggerFactory.getLogger(NumeroStationResource.class);

    private static final String ENTITY_NAME = "numeroStation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NumeroStationRepository numeroStationRepository;

    public NumeroStationResource(NumeroStationRepository numeroStationRepository) {
        this.numeroStationRepository = numeroStationRepository;
    }

    /**
     * {@code POST  /numero-stations} : Create a new numeroStation.
     *
     * @param numeroStation the numeroStation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new numeroStation, or with status {@code 400 (Bad Request)} if the numeroStation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/numero-stations")
    public ResponseEntity<NumeroStation> createNumeroStation(@Valid @RequestBody NumeroStation numeroStation) throws URISyntaxException {
        log.debug("REST request to save NumeroStation : {}", numeroStation);
        if (numeroStation.getId() != null) {
            throw new BadRequestAlertException("A new numeroStation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NumeroStation result = numeroStationRepository.save(numeroStation);
        return ResponseEntity
            .created(new URI("/api/numero-stations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /numero-stations/:id} : Updates an existing numeroStation.
     *
     * @param id the id of the numeroStation to save.
     * @param numeroStation the numeroStation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated numeroStation,
     * or with status {@code 400 (Bad Request)} if the numeroStation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the numeroStation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/numero-stations/{id}")
    public ResponseEntity<NumeroStation> updateNumeroStation(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody NumeroStation numeroStation
    ) throws URISyntaxException {
        log.debug("REST request to update NumeroStation : {}, {}", id, numeroStation);
        if (numeroStation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, numeroStation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!numeroStationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NumeroStation result = numeroStationRepository.save(numeroStation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, numeroStation.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /numero-stations/:id} : Partial updates given fields of an existing numeroStation, field will ignore if it is null
     *
     * @param id the id of the numeroStation to save.
     * @param numeroStation the numeroStation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated numeroStation,
     * or with status {@code 400 (Bad Request)} if the numeroStation is not valid,
     * or with status {@code 404 (Not Found)} if the numeroStation is not found,
     * or with status {@code 500 (Internal Server Error)} if the numeroStation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/numero-stations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NumeroStation> partialUpdateNumeroStation(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody NumeroStation numeroStation
    ) throws URISyntaxException {
        log.debug("REST request to partial update NumeroStation partially : {}, {}", id, numeroStation);
        if (numeroStation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, numeroStation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!numeroStationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NumeroStation> result = numeroStationRepository
            .findById(numeroStation.getId())
            .map(existingNumeroStation -> {
                if (numeroStation.getNumeroStation() != null) {
                    existingNumeroStation.setNumeroStation(numeroStation.getNumeroStation());
                }

                return existingNumeroStation;
            })
            .map(numeroStationRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, numeroStation.getId())
        );
    }

    /**
     * {@code GET  /numero-stations} : get all the numeroStations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of numeroStations in body.
     */
    @GetMapping("/numero-stations")
    public List<NumeroStation> getAllNumeroStations() {
        log.debug("REST request to get all NumeroStations");
        return numeroStationRepository.findAll();
    }

    /**
     * {@code GET  /numero-stations/:id} : get the "id" numeroStation.
     *
     * @param id the id of the numeroStation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the numeroStation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/numero-stations/{id}")
    public ResponseEntity<NumeroStation> getNumeroStation(@PathVariable String id) {
        log.debug("REST request to get NumeroStation : {}", id);
        Optional<NumeroStation> numeroStation = numeroStationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(numeroStation);
    }

    /**
     * {@code DELETE  /numero-stations/:id} : delete the "id" numeroStation.
     *
     * @param id the id of the numeroStation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/numero-stations/{id}")
    public ResponseEntity<Void> deleteNumeroStation(@PathVariable String id) {
        log.debug("REST request to delete NumeroStation : {}", id);
        numeroStationRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
