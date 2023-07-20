package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Equipement;
import com.mycompany.myapp.repository.EquipementRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Equipement}.
 */
@RestController
@RequestMapping("/api")
public class EquipementResource {

    private final Logger log = LoggerFactory.getLogger(EquipementResource.class);

    private static final String ENTITY_NAME = "equipement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EquipementRepository equipementRepository;

    public EquipementResource(EquipementRepository equipementRepository) {
        this.equipementRepository = equipementRepository;
    }

    /**
     * {@code POST  /equipements} : Create a new equipement.
     *
     * @param equipement the equipement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new equipement, or with status {@code 400 (Bad Request)} if the equipement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/equipements")
    public ResponseEntity<Equipement> createEquipement(@Valid @RequestBody Equipement equipement) throws URISyntaxException {
        log.debug("REST request to save Equipement : {}", equipement);
        if (equipement.getId() != null) {
            throw new BadRequestAlertException("A new equipement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Equipement result = equipementRepository.save(equipement);
        return ResponseEntity
            .created(new URI("/api/equipements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /equipements/:id} : Updates an existing equipement.
     *
     * @param id the id of the equipement to save.
     * @param equipement the equipement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipement,
     * or with status {@code 400 (Bad Request)} if the equipement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the equipement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/equipements/{id}")
    public ResponseEntity<Equipement> updateEquipement(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody Equipement equipement
    ) throws URISyntaxException {
        log.debug("REST request to update Equipement : {}, {}", id, equipement);
        if (equipement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, equipement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!equipementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Equipement result = equipementRepository.save(equipement);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, equipement.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /equipements/:id} : Partial updates given fields of an existing equipement, field will ignore if it is null
     *
     * @param id the id of the equipement to save.
     * @param equipement the equipement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipement,
     * or with status {@code 400 (Bad Request)} if the equipement is not valid,
     * or with status {@code 404 (Not Found)} if the equipement is not found,
     * or with status {@code 500 (Internal Server Error)} if the equipement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/equipements/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Equipement> partialUpdateEquipement(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody Equipement equipement
    ) throws URISyntaxException {
        log.debug("REST request to partial update Equipement partially : {}, {}", id, equipement);
        if (equipement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, equipement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!equipementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Equipement> result = equipementRepository
            .findById(equipement.getId())
            .map(existingEquipement -> {
                if (equipement.getAdressePhysique() != null) {
                    existingEquipement.setAdressePhysique(equipement.getAdressePhysique());
                }
                if (equipement.getEquipementType() != null) {
                    existingEquipement.setEquipementType(equipement.getEquipementType());
                }
                if (equipement.getIp() != null) {
                    existingEquipement.setIp(equipement.getIp());
                }
                if (equipement.getBiosVersion() != null) {
                    existingEquipement.setBiosVersion(equipement.getBiosVersion());
                }
                if (equipement.getCommentaire() != null) {
                    existingEquipement.setCommentaire(equipement.getCommentaire());
                }
                if (equipement.getAsset() != null) {
                    existingEquipement.setAsset(equipement.getAsset());
                }
                if (equipement.getNumeroSerie() != null) {
                    existingEquipement.setNumeroSerie(equipement.getNumeroSerie());
                }
                if (equipement.getModele() != null) {
                    existingEquipement.setModele(equipement.getModele());
                }
                if (equipement.getGeneration() != null) {
                    existingEquipement.setGeneration(equipement.getGeneration());
                }
                if (equipement.getMarque() != null) {
                    existingEquipement.setMarque(equipement.getMarque());
                }
                if (equipement.getFonctionnel() != null) {
                    existingEquipement.setFonctionnel(equipement.getFonctionnel());
                }
                if (equipement.getCleAntiVolAdmin() != null) {
                    existingEquipement.setCleAntiVolAdmin(equipement.getCleAntiVolAdmin());
                }
                if (equipement.getCleAntiVolCollaborateur() != null) {
                    existingEquipement.setCleAntiVolCollaborateur(equipement.getCleAntiVolCollaborateur());
                }

                return existingEquipement;
            })
            .map(equipementRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, equipement.getId())
        );
    }

    /**
     * {@code GET  /equipements} : get all the equipements.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of equipements in body.
     */
    @GetMapping("/equipements")
    public List<Equipement> getAllEquipements(@RequestParam(required = false) String filter) {
        if ("numerostation-is-null".equals(filter)) {
            log.debug("REST request to get all Equipements where numeroStation is null");
            return StreamSupport
                .stream(equipementRepository.findAll().spliterator(), false)
                .filter(equipement -> equipement.getNumeroStation() == null)
                .toList();
        }

        if ("teletravail-is-null".equals(filter)) {
            log.debug("REST request to get all Equipements where teletravail is null");
            return StreamSupport
                .stream(equipementRepository.findAll().spliterator(), false)
                .filter(equipement -> equipement.getTeletravail() == null)
                .toList();
        }
        log.debug("REST request to get all Equipements");
        return equipementRepository.findAll();
    }

    /**
     * {@code GET  /equipements/:id} : get the "id" equipement.
     *
     * @param id the id of the equipement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the equipement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/equipements/{id}")
    public ResponseEntity<Equipement> getEquipement(@PathVariable String id) {
        log.debug("REST request to get Equipement : {}", id);
        Optional<Equipement> equipement = equipementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(equipement);
    }

    /**
     * {@code DELETE  /equipements/:id} : delete the "id" equipement.
     *
     * @param id the id of the equipement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/equipements/{id}")
    public ResponseEntity<Void> deleteEquipement(@PathVariable String id) {
        log.debug("REST request to delete Equipement : {}", id);
        equipementRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
