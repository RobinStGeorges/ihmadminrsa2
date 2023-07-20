package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Equipement;
import com.mycompany.myapp.domain.enumeration.EquipementType;
import com.mycompany.myapp.repository.EquipementRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link EquipementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EquipementResourceIT {

    private static final String DEFAULT_ADRESSE_PHYSIQUE = "FY:5Z-aA-av-g2-y";
    private static final String UPDATED_ADRESSE_PHYSIQUE = "rL:Lf:XL-1A|mmD";

    private static final EquipementType DEFAULT_EQUIPEMENT_TYPE = EquipementType.Ordinateur_Portable_DGFIP;
    private static final EquipementType UPDATED_EQUIPEMENT_TYPE = EquipementType.Ordinateur_Portable_Domicile;

    private static final String DEFAULT_IP = "AAAAAAAAAA";
    private static final String UPDATED_IP = "BBBBBBBBBB";

    private static final String DEFAULT_BIOS_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_BIOS_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    private static final String DEFAULT_ASSET = "S28601255";
    private static final String UPDATED_ASSET = "S5749890";

    private static final String DEFAULT_NUMERO_SERIE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_SERIE = "BBBBBBBBBB";

    private static final String DEFAULT_MODELE = "AAAAAAAAAA";
    private static final String UPDATED_MODELE = "BBBBBBBBBB";

    private static final String DEFAULT_GENERATION = "AAAAAAAAAA";
    private static final String UPDATED_GENERATION = "BBBBBBBBBB";

    private static final String DEFAULT_MARQUE = "AAAAAAAAAA";
    private static final String UPDATED_MARQUE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FONCTIONNEL = false;
    private static final Boolean UPDATED_FONCTIONNEL = true;

    private static final String DEFAULT_CLE_ANTI_VOL_ADMIN = "AAAAAAAAAA";
    private static final String UPDATED_CLE_ANTI_VOL_ADMIN = "BBBBBBBBBB";

    private static final String DEFAULT_CLE_ANTI_VOL_COLLABORATEUR = "AAAAAAAAAA";
    private static final String UPDATED_CLE_ANTI_VOL_COLLABORATEUR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/equipements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private EquipementRepository equipementRepository;

    @Autowired
    private MockMvc restEquipementMockMvc;

    private Equipement equipement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Equipement createEntity() {
        Equipement equipement = new Equipement()
            .adressePhysique(DEFAULT_ADRESSE_PHYSIQUE)
            .equipementType(DEFAULT_EQUIPEMENT_TYPE)
            .ip(DEFAULT_IP)
            .biosVersion(DEFAULT_BIOS_VERSION)
            .commentaire(DEFAULT_COMMENTAIRE)
            .asset(DEFAULT_ASSET)
            .numeroSerie(DEFAULT_NUMERO_SERIE)
            .modele(DEFAULT_MODELE)
            .generation(DEFAULT_GENERATION)
            .marque(DEFAULT_MARQUE)
            .fonctionnel(DEFAULT_FONCTIONNEL)
            .cleAntiVolAdmin(DEFAULT_CLE_ANTI_VOL_ADMIN)
            .cleAntiVolCollaborateur(DEFAULT_CLE_ANTI_VOL_COLLABORATEUR);
        return equipement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Equipement createUpdatedEntity() {
        Equipement equipement = new Equipement()
            .adressePhysique(UPDATED_ADRESSE_PHYSIQUE)
            .equipementType(UPDATED_EQUIPEMENT_TYPE)
            .ip(UPDATED_IP)
            .biosVersion(UPDATED_BIOS_VERSION)
            .commentaire(UPDATED_COMMENTAIRE)
            .asset(UPDATED_ASSET)
            .numeroSerie(UPDATED_NUMERO_SERIE)
            .modele(UPDATED_MODELE)
            .generation(UPDATED_GENERATION)
            .marque(UPDATED_MARQUE)
            .fonctionnel(UPDATED_FONCTIONNEL)
            .cleAntiVolAdmin(UPDATED_CLE_ANTI_VOL_ADMIN)
            .cleAntiVolCollaborateur(UPDATED_CLE_ANTI_VOL_COLLABORATEUR);
        return equipement;
    }

    @BeforeEach
    public void initTest() {
        equipementRepository.deleteAll();
        equipement = createEntity();
    }

    @Test
    void createEquipement() throws Exception {
        int databaseSizeBeforeCreate = equipementRepository.findAll().size();
        // Create the Equipement
        restEquipementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(equipement))
            )
            .andExpect(status().isCreated());

        // Validate the Equipement in the database
        List<Equipement> equipementList = equipementRepository.findAll();
        assertThat(equipementList).hasSize(databaseSizeBeforeCreate + 1);
        Equipement testEquipement = equipementList.get(equipementList.size() - 1);
        assertThat(testEquipement.getAdressePhysique()).isEqualTo(DEFAULT_ADRESSE_PHYSIQUE);
        assertThat(testEquipement.getEquipementType()).isEqualTo(DEFAULT_EQUIPEMENT_TYPE);
        assertThat(testEquipement.getIp()).isEqualTo(DEFAULT_IP);
        assertThat(testEquipement.getBiosVersion()).isEqualTo(DEFAULT_BIOS_VERSION);
        assertThat(testEquipement.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
        assertThat(testEquipement.getAsset()).isEqualTo(DEFAULT_ASSET);
        assertThat(testEquipement.getNumeroSerie()).isEqualTo(DEFAULT_NUMERO_SERIE);
        assertThat(testEquipement.getModele()).isEqualTo(DEFAULT_MODELE);
        assertThat(testEquipement.getGeneration()).isEqualTo(DEFAULT_GENERATION);
        assertThat(testEquipement.getMarque()).isEqualTo(DEFAULT_MARQUE);
        assertThat(testEquipement.getFonctionnel()).isEqualTo(DEFAULT_FONCTIONNEL);
        assertThat(testEquipement.getCleAntiVolAdmin()).isEqualTo(DEFAULT_CLE_ANTI_VOL_ADMIN);
        assertThat(testEquipement.getCleAntiVolCollaborateur()).isEqualTo(DEFAULT_CLE_ANTI_VOL_COLLABORATEUR);
    }

    @Test
    void createEquipementWithExistingId() throws Exception {
        // Create the Equipement with an existing ID
        equipement.setId("existing_id");

        int databaseSizeBeforeCreate = equipementRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEquipementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(equipement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Equipement in the database
        List<Equipement> equipementList = equipementRepository.findAll();
        assertThat(equipementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllEquipements() throws Exception {
        // Initialize the database
        equipementRepository.save(equipement);

        // Get all the equipementList
        restEquipementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipement.getId())))
            .andExpect(jsonPath("$.[*].adressePhysique").value(hasItem(DEFAULT_ADRESSE_PHYSIQUE)))
            .andExpect(jsonPath("$.[*].equipementType").value(hasItem(DEFAULT_EQUIPEMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP)))
            .andExpect(jsonPath("$.[*].biosVersion").value(hasItem(DEFAULT_BIOS_VERSION)))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE)))
            .andExpect(jsonPath("$.[*].asset").value(hasItem(DEFAULT_ASSET)))
            .andExpect(jsonPath("$.[*].numeroSerie").value(hasItem(DEFAULT_NUMERO_SERIE)))
            .andExpect(jsonPath("$.[*].modele").value(hasItem(DEFAULT_MODELE)))
            .andExpect(jsonPath("$.[*].generation").value(hasItem(DEFAULT_GENERATION)))
            .andExpect(jsonPath("$.[*].marque").value(hasItem(DEFAULT_MARQUE)))
            .andExpect(jsonPath("$.[*].fonctionnel").value(hasItem(DEFAULT_FONCTIONNEL.booleanValue())))
            .andExpect(jsonPath("$.[*].cleAntiVolAdmin").value(hasItem(DEFAULT_CLE_ANTI_VOL_ADMIN)))
            .andExpect(jsonPath("$.[*].cleAntiVolCollaborateur").value(hasItem(DEFAULT_CLE_ANTI_VOL_COLLABORATEUR)));
    }

    @Test
    void getEquipement() throws Exception {
        // Initialize the database
        equipementRepository.save(equipement);

        // Get the equipement
        restEquipementMockMvc
            .perform(get(ENTITY_API_URL_ID, equipement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(equipement.getId()))
            .andExpect(jsonPath("$.adressePhysique").value(DEFAULT_ADRESSE_PHYSIQUE))
            .andExpect(jsonPath("$.equipementType").value(DEFAULT_EQUIPEMENT_TYPE.toString()))
            .andExpect(jsonPath("$.ip").value(DEFAULT_IP))
            .andExpect(jsonPath("$.biosVersion").value(DEFAULT_BIOS_VERSION))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE))
            .andExpect(jsonPath("$.asset").value(DEFAULT_ASSET))
            .andExpect(jsonPath("$.numeroSerie").value(DEFAULT_NUMERO_SERIE))
            .andExpect(jsonPath("$.modele").value(DEFAULT_MODELE))
            .andExpect(jsonPath("$.generation").value(DEFAULT_GENERATION))
            .andExpect(jsonPath("$.marque").value(DEFAULT_MARQUE))
            .andExpect(jsonPath("$.fonctionnel").value(DEFAULT_FONCTIONNEL.booleanValue()))
            .andExpect(jsonPath("$.cleAntiVolAdmin").value(DEFAULT_CLE_ANTI_VOL_ADMIN))
            .andExpect(jsonPath("$.cleAntiVolCollaborateur").value(DEFAULT_CLE_ANTI_VOL_COLLABORATEUR));
    }

    @Test
    void getNonExistingEquipement() throws Exception {
        // Get the equipement
        restEquipementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingEquipement() throws Exception {
        // Initialize the database
        equipementRepository.save(equipement);

        int databaseSizeBeforeUpdate = equipementRepository.findAll().size();

        // Update the equipement
        Equipement updatedEquipement = equipementRepository.findById(equipement.getId()).orElseThrow();
        updatedEquipement
            .adressePhysique(UPDATED_ADRESSE_PHYSIQUE)
            .equipementType(UPDATED_EQUIPEMENT_TYPE)
            .ip(UPDATED_IP)
            .biosVersion(UPDATED_BIOS_VERSION)
            .commentaire(UPDATED_COMMENTAIRE)
            .asset(UPDATED_ASSET)
            .numeroSerie(UPDATED_NUMERO_SERIE)
            .modele(UPDATED_MODELE)
            .generation(UPDATED_GENERATION)
            .marque(UPDATED_MARQUE)
            .fonctionnel(UPDATED_FONCTIONNEL)
            .cleAntiVolAdmin(UPDATED_CLE_ANTI_VOL_ADMIN)
            .cleAntiVolCollaborateur(UPDATED_CLE_ANTI_VOL_COLLABORATEUR);

        restEquipementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEquipement.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEquipement))
            )
            .andExpect(status().isOk());

        // Validate the Equipement in the database
        List<Equipement> equipementList = equipementRepository.findAll();
        assertThat(equipementList).hasSize(databaseSizeBeforeUpdate);
        Equipement testEquipement = equipementList.get(equipementList.size() - 1);
        assertThat(testEquipement.getAdressePhysique()).isEqualTo(UPDATED_ADRESSE_PHYSIQUE);
        assertThat(testEquipement.getEquipementType()).isEqualTo(UPDATED_EQUIPEMENT_TYPE);
        assertThat(testEquipement.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testEquipement.getBiosVersion()).isEqualTo(UPDATED_BIOS_VERSION);
        assertThat(testEquipement.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testEquipement.getAsset()).isEqualTo(UPDATED_ASSET);
        assertThat(testEquipement.getNumeroSerie()).isEqualTo(UPDATED_NUMERO_SERIE);
        assertThat(testEquipement.getModele()).isEqualTo(UPDATED_MODELE);
        assertThat(testEquipement.getGeneration()).isEqualTo(UPDATED_GENERATION);
        assertThat(testEquipement.getMarque()).isEqualTo(UPDATED_MARQUE);
        assertThat(testEquipement.getFonctionnel()).isEqualTo(UPDATED_FONCTIONNEL);
        assertThat(testEquipement.getCleAntiVolAdmin()).isEqualTo(UPDATED_CLE_ANTI_VOL_ADMIN);
        assertThat(testEquipement.getCleAntiVolCollaborateur()).isEqualTo(UPDATED_CLE_ANTI_VOL_COLLABORATEUR);
    }

    @Test
    void putNonExistingEquipement() throws Exception {
        int databaseSizeBeforeUpdate = equipementRepository.findAll().size();
        equipement.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, equipement.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(equipement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Equipement in the database
        List<Equipement> equipementList = equipementRepository.findAll();
        assertThat(equipementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchEquipement() throws Exception {
        int databaseSizeBeforeUpdate = equipementRepository.findAll().size();
        equipement.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(equipement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Equipement in the database
        List<Equipement> equipementList = equipementRepository.findAll();
        assertThat(equipementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamEquipement() throws Exception {
        int databaseSizeBeforeUpdate = equipementRepository.findAll().size();
        equipement.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipementMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(equipement))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Equipement in the database
        List<Equipement> equipementList = equipementRepository.findAll();
        assertThat(equipementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateEquipementWithPatch() throws Exception {
        // Initialize the database
        equipementRepository.save(equipement);

        int databaseSizeBeforeUpdate = equipementRepository.findAll().size();

        // Update the equipement using partial update
        Equipement partialUpdatedEquipement = new Equipement();
        partialUpdatedEquipement.setId(equipement.getId());

        partialUpdatedEquipement
            .ip(UPDATED_IP)
            .commentaire(UPDATED_COMMENTAIRE)
            .numeroSerie(UPDATED_NUMERO_SERIE)
            .fonctionnel(UPDATED_FONCTIONNEL)
            .cleAntiVolAdmin(UPDATED_CLE_ANTI_VOL_ADMIN)
            .cleAntiVolCollaborateur(UPDATED_CLE_ANTI_VOL_COLLABORATEUR);

        restEquipementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEquipement.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEquipement))
            )
            .andExpect(status().isOk());

        // Validate the Equipement in the database
        List<Equipement> equipementList = equipementRepository.findAll();
        assertThat(equipementList).hasSize(databaseSizeBeforeUpdate);
        Equipement testEquipement = equipementList.get(equipementList.size() - 1);
        assertThat(testEquipement.getAdressePhysique()).isEqualTo(DEFAULT_ADRESSE_PHYSIQUE);
        assertThat(testEquipement.getEquipementType()).isEqualTo(DEFAULT_EQUIPEMENT_TYPE);
        assertThat(testEquipement.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testEquipement.getBiosVersion()).isEqualTo(DEFAULT_BIOS_VERSION);
        assertThat(testEquipement.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testEquipement.getAsset()).isEqualTo(DEFAULT_ASSET);
        assertThat(testEquipement.getNumeroSerie()).isEqualTo(UPDATED_NUMERO_SERIE);
        assertThat(testEquipement.getModele()).isEqualTo(DEFAULT_MODELE);
        assertThat(testEquipement.getGeneration()).isEqualTo(DEFAULT_GENERATION);
        assertThat(testEquipement.getMarque()).isEqualTo(DEFAULT_MARQUE);
        assertThat(testEquipement.getFonctionnel()).isEqualTo(UPDATED_FONCTIONNEL);
        assertThat(testEquipement.getCleAntiVolAdmin()).isEqualTo(UPDATED_CLE_ANTI_VOL_ADMIN);
        assertThat(testEquipement.getCleAntiVolCollaborateur()).isEqualTo(UPDATED_CLE_ANTI_VOL_COLLABORATEUR);
    }

    @Test
    void fullUpdateEquipementWithPatch() throws Exception {
        // Initialize the database
        equipementRepository.save(equipement);

        int databaseSizeBeforeUpdate = equipementRepository.findAll().size();

        // Update the equipement using partial update
        Equipement partialUpdatedEquipement = new Equipement();
        partialUpdatedEquipement.setId(equipement.getId());

        partialUpdatedEquipement
            .adressePhysique(UPDATED_ADRESSE_PHYSIQUE)
            .equipementType(UPDATED_EQUIPEMENT_TYPE)
            .ip(UPDATED_IP)
            .biosVersion(UPDATED_BIOS_VERSION)
            .commentaire(UPDATED_COMMENTAIRE)
            .asset(UPDATED_ASSET)
            .numeroSerie(UPDATED_NUMERO_SERIE)
            .modele(UPDATED_MODELE)
            .generation(UPDATED_GENERATION)
            .marque(UPDATED_MARQUE)
            .fonctionnel(UPDATED_FONCTIONNEL)
            .cleAntiVolAdmin(UPDATED_CLE_ANTI_VOL_ADMIN)
            .cleAntiVolCollaborateur(UPDATED_CLE_ANTI_VOL_COLLABORATEUR);

        restEquipementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEquipement.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEquipement))
            )
            .andExpect(status().isOk());

        // Validate the Equipement in the database
        List<Equipement> equipementList = equipementRepository.findAll();
        assertThat(equipementList).hasSize(databaseSizeBeforeUpdate);
        Equipement testEquipement = equipementList.get(equipementList.size() - 1);
        assertThat(testEquipement.getAdressePhysique()).isEqualTo(UPDATED_ADRESSE_PHYSIQUE);
        assertThat(testEquipement.getEquipementType()).isEqualTo(UPDATED_EQUIPEMENT_TYPE);
        assertThat(testEquipement.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testEquipement.getBiosVersion()).isEqualTo(UPDATED_BIOS_VERSION);
        assertThat(testEquipement.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testEquipement.getAsset()).isEqualTo(UPDATED_ASSET);
        assertThat(testEquipement.getNumeroSerie()).isEqualTo(UPDATED_NUMERO_SERIE);
        assertThat(testEquipement.getModele()).isEqualTo(UPDATED_MODELE);
        assertThat(testEquipement.getGeneration()).isEqualTo(UPDATED_GENERATION);
        assertThat(testEquipement.getMarque()).isEqualTo(UPDATED_MARQUE);
        assertThat(testEquipement.getFonctionnel()).isEqualTo(UPDATED_FONCTIONNEL);
        assertThat(testEquipement.getCleAntiVolAdmin()).isEqualTo(UPDATED_CLE_ANTI_VOL_ADMIN);
        assertThat(testEquipement.getCleAntiVolCollaborateur()).isEqualTo(UPDATED_CLE_ANTI_VOL_COLLABORATEUR);
    }

    @Test
    void patchNonExistingEquipement() throws Exception {
        int databaseSizeBeforeUpdate = equipementRepository.findAll().size();
        equipement.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, equipement.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(equipement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Equipement in the database
        List<Equipement> equipementList = equipementRepository.findAll();
        assertThat(equipementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchEquipement() throws Exception {
        int databaseSizeBeforeUpdate = equipementRepository.findAll().size();
        equipement.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(equipement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Equipement in the database
        List<Equipement> equipementList = equipementRepository.findAll();
        assertThat(equipementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamEquipement() throws Exception {
        int databaseSizeBeforeUpdate = equipementRepository.findAll().size();
        equipement.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipementMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(equipement))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Equipement in the database
        List<Equipement> equipementList = equipementRepository.findAll();
        assertThat(equipementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteEquipement() throws Exception {
        // Initialize the database
        equipementRepository.save(equipement);

        int databaseSizeBeforeDelete = equipementRepository.findAll().size();

        // Delete the equipement
        restEquipementMockMvc
            .perform(delete(ENTITY_API_URL_ID, equipement.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Equipement> equipementList = equipementRepository.findAll();
        assertThat(equipementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
