package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Localisation;
import com.mycompany.myapp.domain.enumeration.Site;
import com.mycompany.myapp.domain.enumeration.Ville;
import com.mycompany.myapp.repository.LocalisationRepository;
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
 * Integration tests for the {@link LocalisationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LocalisationResourceIT {

    private static final Ville DEFAULT_VILLE = Ville.Noisy;
    private static final Ville UPDATED_VILLE = Ville.Noisiel;

    private static final Site DEFAULT_SITE = Site.Maille_Nord;
    private static final Site UPDATED_SITE = Site.Montaigne;

    private static final String DEFAULT_BATIMENT = "AAAAAAAAAA";
    private static final String UPDATED_BATIMENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_ETAGE = 1;
    private static final Integer UPDATED_ETAGE = 2;

    private static final String DEFAULT_BUREAU = "AAAAAAAAAA";
    private static final String UPDATED_BUREAU = "BBBBBBBBBB";

    private static final String DEFAULT_PLACE = "AAAAAAAAAA";
    private static final String UPDATED_PLACE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/localisations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private LocalisationRepository localisationRepository;

    @Autowired
    private MockMvc restLocalisationMockMvc;

    private Localisation localisation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Localisation createEntity() {
        Localisation localisation = new Localisation()
            .ville(DEFAULT_VILLE)
            .site(DEFAULT_SITE)
            .batiment(DEFAULT_BATIMENT)
            .etage(DEFAULT_ETAGE)
            .bureau(DEFAULT_BUREAU)
            .place(DEFAULT_PLACE);
        return localisation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Localisation createUpdatedEntity() {
        Localisation localisation = new Localisation()
            .ville(UPDATED_VILLE)
            .site(UPDATED_SITE)
            .batiment(UPDATED_BATIMENT)
            .etage(UPDATED_ETAGE)
            .bureau(UPDATED_BUREAU)
            .place(UPDATED_PLACE);
        return localisation;
    }

    @BeforeEach
    public void initTest() {
        localisationRepository.deleteAll();
        localisation = createEntity();
    }

    @Test
    void createLocalisation() throws Exception {
        int databaseSizeBeforeCreate = localisationRepository.findAll().size();
        // Create the Localisation
        restLocalisationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(localisation))
            )
            .andExpect(status().isCreated());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeCreate + 1);
        Localisation testLocalisation = localisationList.get(localisationList.size() - 1);
        assertThat(testLocalisation.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testLocalisation.getSite()).isEqualTo(DEFAULT_SITE);
        assertThat(testLocalisation.getBatiment()).isEqualTo(DEFAULT_BATIMENT);
        assertThat(testLocalisation.getEtage()).isEqualTo(DEFAULT_ETAGE);
        assertThat(testLocalisation.getBureau()).isEqualTo(DEFAULT_BUREAU);
        assertThat(testLocalisation.getPlace()).isEqualTo(DEFAULT_PLACE);
    }

    @Test
    void createLocalisationWithExistingId() throws Exception {
        // Create the Localisation with an existing ID
        localisation.setId("existing_id");

        int databaseSizeBeforeCreate = localisationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocalisationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(localisation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkVilleIsRequired() throws Exception {
        int databaseSizeBeforeTest = localisationRepository.findAll().size();
        // set the field null
        localisation.setVille(null);

        // Create the Localisation, which fails.

        restLocalisationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(localisation))
            )
            .andExpect(status().isBadRequest());

        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllLocalisations() throws Exception {
        // Initialize the database
        localisationRepository.save(localisation);

        // Get all the localisationList
        restLocalisationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localisation.getId())))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE.toString())))
            .andExpect(jsonPath("$.[*].site").value(hasItem(DEFAULT_SITE.toString())))
            .andExpect(jsonPath("$.[*].batiment").value(hasItem(DEFAULT_BATIMENT)))
            .andExpect(jsonPath("$.[*].etage").value(hasItem(DEFAULT_ETAGE)))
            .andExpect(jsonPath("$.[*].bureau").value(hasItem(DEFAULT_BUREAU)))
            .andExpect(jsonPath("$.[*].place").value(hasItem(DEFAULT_PLACE)));
    }

    @Test
    void getLocalisation() throws Exception {
        // Initialize the database
        localisationRepository.save(localisation);

        // Get the localisation
        restLocalisationMockMvc
            .perform(get(ENTITY_API_URL_ID, localisation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(localisation.getId()))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE.toString()))
            .andExpect(jsonPath("$.site").value(DEFAULT_SITE.toString()))
            .andExpect(jsonPath("$.batiment").value(DEFAULT_BATIMENT))
            .andExpect(jsonPath("$.etage").value(DEFAULT_ETAGE))
            .andExpect(jsonPath("$.bureau").value(DEFAULT_BUREAU))
            .andExpect(jsonPath("$.place").value(DEFAULT_PLACE));
    }

    @Test
    void getNonExistingLocalisation() throws Exception {
        // Get the localisation
        restLocalisationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingLocalisation() throws Exception {
        // Initialize the database
        localisationRepository.save(localisation);

        int databaseSizeBeforeUpdate = localisationRepository.findAll().size();

        // Update the localisation
        Localisation updatedLocalisation = localisationRepository.findById(localisation.getId()).orElseThrow();
        updatedLocalisation
            .ville(UPDATED_VILLE)
            .site(UPDATED_SITE)
            .batiment(UPDATED_BATIMENT)
            .etage(UPDATED_ETAGE)
            .bureau(UPDATED_BUREAU)
            .place(UPDATED_PLACE);

        restLocalisationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLocalisation.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLocalisation))
            )
            .andExpect(status().isOk());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeUpdate);
        Localisation testLocalisation = localisationList.get(localisationList.size() - 1);
        assertThat(testLocalisation.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testLocalisation.getSite()).isEqualTo(UPDATED_SITE);
        assertThat(testLocalisation.getBatiment()).isEqualTo(UPDATED_BATIMENT);
        assertThat(testLocalisation.getEtage()).isEqualTo(UPDATED_ETAGE);
        assertThat(testLocalisation.getBureau()).isEqualTo(UPDATED_BUREAU);
        assertThat(testLocalisation.getPlace()).isEqualTo(UPDATED_PLACE);
    }

    @Test
    void putNonExistingLocalisation() throws Exception {
        int databaseSizeBeforeUpdate = localisationRepository.findAll().size();
        localisation.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocalisationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, localisation.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(localisation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchLocalisation() throws Exception {
        int databaseSizeBeforeUpdate = localisationRepository.findAll().size();
        localisation.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocalisationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(localisation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamLocalisation() throws Exception {
        int databaseSizeBeforeUpdate = localisationRepository.findAll().size();
        localisation.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocalisationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(localisation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateLocalisationWithPatch() throws Exception {
        // Initialize the database
        localisationRepository.save(localisation);

        int databaseSizeBeforeUpdate = localisationRepository.findAll().size();

        // Update the localisation using partial update
        Localisation partialUpdatedLocalisation = new Localisation();
        partialUpdatedLocalisation.setId(localisation.getId());

        partialUpdatedLocalisation
            .ville(UPDATED_VILLE)
            .site(UPDATED_SITE)
            .batiment(UPDATED_BATIMENT)
            .etage(UPDATED_ETAGE)
            .bureau(UPDATED_BUREAU);

        restLocalisationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocalisation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLocalisation))
            )
            .andExpect(status().isOk());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeUpdate);
        Localisation testLocalisation = localisationList.get(localisationList.size() - 1);
        assertThat(testLocalisation.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testLocalisation.getSite()).isEqualTo(UPDATED_SITE);
        assertThat(testLocalisation.getBatiment()).isEqualTo(UPDATED_BATIMENT);
        assertThat(testLocalisation.getEtage()).isEqualTo(UPDATED_ETAGE);
        assertThat(testLocalisation.getBureau()).isEqualTo(UPDATED_BUREAU);
        assertThat(testLocalisation.getPlace()).isEqualTo(DEFAULT_PLACE);
    }

    @Test
    void fullUpdateLocalisationWithPatch() throws Exception {
        // Initialize the database
        localisationRepository.save(localisation);

        int databaseSizeBeforeUpdate = localisationRepository.findAll().size();

        // Update the localisation using partial update
        Localisation partialUpdatedLocalisation = new Localisation();
        partialUpdatedLocalisation.setId(localisation.getId());

        partialUpdatedLocalisation
            .ville(UPDATED_VILLE)
            .site(UPDATED_SITE)
            .batiment(UPDATED_BATIMENT)
            .etage(UPDATED_ETAGE)
            .bureau(UPDATED_BUREAU)
            .place(UPDATED_PLACE);

        restLocalisationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocalisation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLocalisation))
            )
            .andExpect(status().isOk());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeUpdate);
        Localisation testLocalisation = localisationList.get(localisationList.size() - 1);
        assertThat(testLocalisation.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testLocalisation.getSite()).isEqualTo(UPDATED_SITE);
        assertThat(testLocalisation.getBatiment()).isEqualTo(UPDATED_BATIMENT);
        assertThat(testLocalisation.getEtage()).isEqualTo(UPDATED_ETAGE);
        assertThat(testLocalisation.getBureau()).isEqualTo(UPDATED_BUREAU);
        assertThat(testLocalisation.getPlace()).isEqualTo(UPDATED_PLACE);
    }

    @Test
    void patchNonExistingLocalisation() throws Exception {
        int databaseSizeBeforeUpdate = localisationRepository.findAll().size();
        localisation.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocalisationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, localisation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(localisation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchLocalisation() throws Exception {
        int databaseSizeBeforeUpdate = localisationRepository.findAll().size();
        localisation.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocalisationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(localisation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamLocalisation() throws Exception {
        int databaseSizeBeforeUpdate = localisationRepository.findAll().size();
        localisation.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocalisationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(localisation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteLocalisation() throws Exception {
        // Initialize the database
        localisationRepository.save(localisation);

        int databaseSizeBeforeDelete = localisationRepository.findAll().size();

        // Delete the localisation
        restLocalisationMockMvc
            .perform(delete(ENTITY_API_URL_ID, localisation.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
