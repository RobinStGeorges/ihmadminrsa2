package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.NumeroStation;
import com.mycompany.myapp.repository.NumeroStationRepository;
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
 * Integration tests for the {@link NumeroStationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NumeroStationResourceIT {

    private static final String DEFAULT_NUMERO_STATION = "Z702-8205377";
    private static final String UPDATED_NUMERO_STATION = "Z009-9831843";

    private static final String ENTITY_API_URL = "/api/numero-stations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private NumeroStationRepository numeroStationRepository;

    @Autowired
    private MockMvc restNumeroStationMockMvc;

    private NumeroStation numeroStation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NumeroStation createEntity() {
        NumeroStation numeroStation = new NumeroStation().numeroStation(DEFAULT_NUMERO_STATION);
        return numeroStation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NumeroStation createUpdatedEntity() {
        NumeroStation numeroStation = new NumeroStation().numeroStation(UPDATED_NUMERO_STATION);
        return numeroStation;
    }

    @BeforeEach
    public void initTest() {
        numeroStationRepository.deleteAll();
        numeroStation = createEntity();
    }

    @Test
    void createNumeroStation() throws Exception {
        int databaseSizeBeforeCreate = numeroStationRepository.findAll().size();
        // Create the NumeroStation
        restNumeroStationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(numeroStation))
            )
            .andExpect(status().isCreated());

        // Validate the NumeroStation in the database
        List<NumeroStation> numeroStationList = numeroStationRepository.findAll();
        assertThat(numeroStationList).hasSize(databaseSizeBeforeCreate + 1);
        NumeroStation testNumeroStation = numeroStationList.get(numeroStationList.size() - 1);
        assertThat(testNumeroStation.getNumeroStation()).isEqualTo(DEFAULT_NUMERO_STATION);
    }

    @Test
    void createNumeroStationWithExistingId() throws Exception {
        // Create the NumeroStation with an existing ID
        numeroStation.setId("existing_id");

        int databaseSizeBeforeCreate = numeroStationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNumeroStationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(numeroStation))
            )
            .andExpect(status().isBadRequest());

        // Validate the NumeroStation in the database
        List<NumeroStation> numeroStationList = numeroStationRepository.findAll();
        assertThat(numeroStationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllNumeroStations() throws Exception {
        // Initialize the database
        numeroStationRepository.save(numeroStation);

        // Get all the numeroStationList
        restNumeroStationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(numeroStation.getId())))
            .andExpect(jsonPath("$.[*].numeroStation").value(hasItem(DEFAULT_NUMERO_STATION)));
    }

    @Test
    void getNumeroStation() throws Exception {
        // Initialize the database
        numeroStationRepository.save(numeroStation);

        // Get the numeroStation
        restNumeroStationMockMvc
            .perform(get(ENTITY_API_URL_ID, numeroStation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(numeroStation.getId()))
            .andExpect(jsonPath("$.numeroStation").value(DEFAULT_NUMERO_STATION));
    }

    @Test
    void getNonExistingNumeroStation() throws Exception {
        // Get the numeroStation
        restNumeroStationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingNumeroStation() throws Exception {
        // Initialize the database
        numeroStationRepository.save(numeroStation);

        int databaseSizeBeforeUpdate = numeroStationRepository.findAll().size();

        // Update the numeroStation
        NumeroStation updatedNumeroStation = numeroStationRepository.findById(numeroStation.getId()).orElseThrow();
        updatedNumeroStation.numeroStation(UPDATED_NUMERO_STATION);

        restNumeroStationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNumeroStation.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNumeroStation))
            )
            .andExpect(status().isOk());

        // Validate the NumeroStation in the database
        List<NumeroStation> numeroStationList = numeroStationRepository.findAll();
        assertThat(numeroStationList).hasSize(databaseSizeBeforeUpdate);
        NumeroStation testNumeroStation = numeroStationList.get(numeroStationList.size() - 1);
        assertThat(testNumeroStation.getNumeroStation()).isEqualTo(UPDATED_NUMERO_STATION);
    }

    @Test
    void putNonExistingNumeroStation() throws Exception {
        int databaseSizeBeforeUpdate = numeroStationRepository.findAll().size();
        numeroStation.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNumeroStationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, numeroStation.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(numeroStation))
            )
            .andExpect(status().isBadRequest());

        // Validate the NumeroStation in the database
        List<NumeroStation> numeroStationList = numeroStationRepository.findAll();
        assertThat(numeroStationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchNumeroStation() throws Exception {
        int databaseSizeBeforeUpdate = numeroStationRepository.findAll().size();
        numeroStation.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNumeroStationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(numeroStation))
            )
            .andExpect(status().isBadRequest());

        // Validate the NumeroStation in the database
        List<NumeroStation> numeroStationList = numeroStationRepository.findAll();
        assertThat(numeroStationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamNumeroStation() throws Exception {
        int databaseSizeBeforeUpdate = numeroStationRepository.findAll().size();
        numeroStation.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNumeroStationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(numeroStation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NumeroStation in the database
        List<NumeroStation> numeroStationList = numeroStationRepository.findAll();
        assertThat(numeroStationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateNumeroStationWithPatch() throws Exception {
        // Initialize the database
        numeroStationRepository.save(numeroStation);

        int databaseSizeBeforeUpdate = numeroStationRepository.findAll().size();

        // Update the numeroStation using partial update
        NumeroStation partialUpdatedNumeroStation = new NumeroStation();
        partialUpdatedNumeroStation.setId(numeroStation.getId());

        restNumeroStationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNumeroStation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNumeroStation))
            )
            .andExpect(status().isOk());

        // Validate the NumeroStation in the database
        List<NumeroStation> numeroStationList = numeroStationRepository.findAll();
        assertThat(numeroStationList).hasSize(databaseSizeBeforeUpdate);
        NumeroStation testNumeroStation = numeroStationList.get(numeroStationList.size() - 1);
        assertThat(testNumeroStation.getNumeroStation()).isEqualTo(DEFAULT_NUMERO_STATION);
    }

    @Test
    void fullUpdateNumeroStationWithPatch() throws Exception {
        // Initialize the database
        numeroStationRepository.save(numeroStation);

        int databaseSizeBeforeUpdate = numeroStationRepository.findAll().size();

        // Update the numeroStation using partial update
        NumeroStation partialUpdatedNumeroStation = new NumeroStation();
        partialUpdatedNumeroStation.setId(numeroStation.getId());

        partialUpdatedNumeroStation.numeroStation(UPDATED_NUMERO_STATION);

        restNumeroStationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNumeroStation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNumeroStation))
            )
            .andExpect(status().isOk());

        // Validate the NumeroStation in the database
        List<NumeroStation> numeroStationList = numeroStationRepository.findAll();
        assertThat(numeroStationList).hasSize(databaseSizeBeforeUpdate);
        NumeroStation testNumeroStation = numeroStationList.get(numeroStationList.size() - 1);
        assertThat(testNumeroStation.getNumeroStation()).isEqualTo(UPDATED_NUMERO_STATION);
    }

    @Test
    void patchNonExistingNumeroStation() throws Exception {
        int databaseSizeBeforeUpdate = numeroStationRepository.findAll().size();
        numeroStation.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNumeroStationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, numeroStation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(numeroStation))
            )
            .andExpect(status().isBadRequest());

        // Validate the NumeroStation in the database
        List<NumeroStation> numeroStationList = numeroStationRepository.findAll();
        assertThat(numeroStationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchNumeroStation() throws Exception {
        int databaseSizeBeforeUpdate = numeroStationRepository.findAll().size();
        numeroStation.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNumeroStationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(numeroStation))
            )
            .andExpect(status().isBadRequest());

        // Validate the NumeroStation in the database
        List<NumeroStation> numeroStationList = numeroStationRepository.findAll();
        assertThat(numeroStationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamNumeroStation() throws Exception {
        int databaseSizeBeforeUpdate = numeroStationRepository.findAll().size();
        numeroStation.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNumeroStationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(numeroStation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NumeroStation in the database
        List<NumeroStation> numeroStationList = numeroStationRepository.findAll();
        assertThat(numeroStationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteNumeroStation() throws Exception {
        // Initialize the database
        numeroStationRepository.save(numeroStation);

        int databaseSizeBeforeDelete = numeroStationRepository.findAll().size();

        // Delete the numeroStation
        restNumeroStationMockMvc
            .perform(delete(ENTITY_API_URL_ID, numeroStation.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NumeroStation> numeroStationList = numeroStationRepository.findAll();
        assertThat(numeroStationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
