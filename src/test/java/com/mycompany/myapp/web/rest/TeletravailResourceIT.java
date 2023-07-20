package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Teletravail;
import com.mycompany.myapp.repository.TeletravailRepository;
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
 * Integration tests for the {@link TeletravailResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TeletravailResourceIT {

    private static final String DEFAULT_IP_DGFIP_FIXE = "0.9.93.8";
    private static final String UPDATED_IP_DGFIP_FIXE = "244.98.67.8";

    private static final String DEFAULT_IP_TELETRAVAIL = "5.643.001.223";
    private static final String UPDATED_IP_TELETRAVAIL = "74.89.522.7";

    private static final String DEFAULT_IP_IPSEC = "99.534.1.1";
    private static final String UPDATED_IP_IPSEC = "7.227.348.22";

    private static final Boolean DEFAULT_IS_ACTIF = false;
    private static final Boolean UPDATED_IS_ACTIF = true;

    private static final String ENTITY_API_URL = "/api/teletravails";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private TeletravailRepository teletravailRepository;

    @Autowired
    private MockMvc restTeletravailMockMvc;

    private Teletravail teletravail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Teletravail createEntity() {
        Teletravail teletravail = new Teletravail()
            .ipDGFIPFixe(DEFAULT_IP_DGFIP_FIXE)
            .ipTeletravail(DEFAULT_IP_TELETRAVAIL)
            .ipIPSEC(DEFAULT_IP_IPSEC)
            .isActif(DEFAULT_IS_ACTIF);
        return teletravail;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Teletravail createUpdatedEntity() {
        Teletravail teletravail = new Teletravail()
            .ipDGFIPFixe(UPDATED_IP_DGFIP_FIXE)
            .ipTeletravail(UPDATED_IP_TELETRAVAIL)
            .ipIPSEC(UPDATED_IP_IPSEC)
            .isActif(UPDATED_IS_ACTIF);
        return teletravail;
    }

    @BeforeEach
    public void initTest() {
        teletravailRepository.deleteAll();
        teletravail = createEntity();
    }

    @Test
    void createTeletravail() throws Exception {
        int databaseSizeBeforeCreate = teletravailRepository.findAll().size();
        // Create the Teletravail
        restTeletravailMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teletravail))
            )
            .andExpect(status().isCreated());

        // Validate the Teletravail in the database
        List<Teletravail> teletravailList = teletravailRepository.findAll();
        assertThat(teletravailList).hasSize(databaseSizeBeforeCreate + 1);
        Teletravail testTeletravail = teletravailList.get(teletravailList.size() - 1);
        assertThat(testTeletravail.getIpDGFIPFixe()).isEqualTo(DEFAULT_IP_DGFIP_FIXE);
        assertThat(testTeletravail.getIpTeletravail()).isEqualTo(DEFAULT_IP_TELETRAVAIL);
        assertThat(testTeletravail.getIpIPSEC()).isEqualTo(DEFAULT_IP_IPSEC);
        assertThat(testTeletravail.getIsActif()).isEqualTo(DEFAULT_IS_ACTIF);
    }

    @Test
    void createTeletravailWithExistingId() throws Exception {
        // Create the Teletravail with an existing ID
        teletravail.setId("existing_id");

        int databaseSizeBeforeCreate = teletravailRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeletravailMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teletravail))
            )
            .andExpect(status().isBadRequest());

        // Validate the Teletravail in the database
        List<Teletravail> teletravailList = teletravailRepository.findAll();
        assertThat(teletravailList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllTeletravails() throws Exception {
        // Initialize the database
        teletravailRepository.save(teletravail);

        // Get all the teletravailList
        restTeletravailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teletravail.getId())))
            .andExpect(jsonPath("$.[*].ipDGFIPFixe").value(hasItem(DEFAULT_IP_DGFIP_FIXE)))
            .andExpect(jsonPath("$.[*].ipTeletravail").value(hasItem(DEFAULT_IP_TELETRAVAIL)))
            .andExpect(jsonPath("$.[*].ipIPSEC").value(hasItem(DEFAULT_IP_IPSEC)))
            .andExpect(jsonPath("$.[*].isActif").value(hasItem(DEFAULT_IS_ACTIF.booleanValue())));
    }

    @Test
    void getTeletravail() throws Exception {
        // Initialize the database
        teletravailRepository.save(teletravail);

        // Get the teletravail
        restTeletravailMockMvc
            .perform(get(ENTITY_API_URL_ID, teletravail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(teletravail.getId()))
            .andExpect(jsonPath("$.ipDGFIPFixe").value(DEFAULT_IP_DGFIP_FIXE))
            .andExpect(jsonPath("$.ipTeletravail").value(DEFAULT_IP_TELETRAVAIL))
            .andExpect(jsonPath("$.ipIPSEC").value(DEFAULT_IP_IPSEC))
            .andExpect(jsonPath("$.isActif").value(DEFAULT_IS_ACTIF.booleanValue()));
    }

    @Test
    void getNonExistingTeletravail() throws Exception {
        // Get the teletravail
        restTeletravailMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingTeletravail() throws Exception {
        // Initialize the database
        teletravailRepository.save(teletravail);

        int databaseSizeBeforeUpdate = teletravailRepository.findAll().size();

        // Update the teletravail
        Teletravail updatedTeletravail = teletravailRepository.findById(teletravail.getId()).orElseThrow();
        updatedTeletravail
            .ipDGFIPFixe(UPDATED_IP_DGFIP_FIXE)
            .ipTeletravail(UPDATED_IP_TELETRAVAIL)
            .ipIPSEC(UPDATED_IP_IPSEC)
            .isActif(UPDATED_IS_ACTIF);

        restTeletravailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTeletravail.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTeletravail))
            )
            .andExpect(status().isOk());

        // Validate the Teletravail in the database
        List<Teletravail> teletravailList = teletravailRepository.findAll();
        assertThat(teletravailList).hasSize(databaseSizeBeforeUpdate);
        Teletravail testTeletravail = teletravailList.get(teletravailList.size() - 1);
        assertThat(testTeletravail.getIpDGFIPFixe()).isEqualTo(UPDATED_IP_DGFIP_FIXE);
        assertThat(testTeletravail.getIpTeletravail()).isEqualTo(UPDATED_IP_TELETRAVAIL);
        assertThat(testTeletravail.getIpIPSEC()).isEqualTo(UPDATED_IP_IPSEC);
        assertThat(testTeletravail.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
    }

    @Test
    void putNonExistingTeletravail() throws Exception {
        int databaseSizeBeforeUpdate = teletravailRepository.findAll().size();
        teletravail.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeletravailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, teletravail.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teletravail))
            )
            .andExpect(status().isBadRequest());

        // Validate the Teletravail in the database
        List<Teletravail> teletravailList = teletravailRepository.findAll();
        assertThat(teletravailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchTeletravail() throws Exception {
        int databaseSizeBeforeUpdate = teletravailRepository.findAll().size();
        teletravail.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeletravailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teletravail))
            )
            .andExpect(status().isBadRequest());

        // Validate the Teletravail in the database
        List<Teletravail> teletravailList = teletravailRepository.findAll();
        assertThat(teletravailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamTeletravail() throws Exception {
        int databaseSizeBeforeUpdate = teletravailRepository.findAll().size();
        teletravail.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeletravailMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teletravail))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Teletravail in the database
        List<Teletravail> teletravailList = teletravailRepository.findAll();
        assertThat(teletravailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateTeletravailWithPatch() throws Exception {
        // Initialize the database
        teletravailRepository.save(teletravail);

        int databaseSizeBeforeUpdate = teletravailRepository.findAll().size();

        // Update the teletravail using partial update
        Teletravail partialUpdatedTeletravail = new Teletravail();
        partialUpdatedTeletravail.setId(teletravail.getId());

        partialUpdatedTeletravail
            .ipDGFIPFixe(UPDATED_IP_DGFIP_FIXE)
            .ipTeletravail(UPDATED_IP_TELETRAVAIL)
            .ipIPSEC(UPDATED_IP_IPSEC)
            .isActif(UPDATED_IS_ACTIF);

        restTeletravailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeletravail.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTeletravail))
            )
            .andExpect(status().isOk());

        // Validate the Teletravail in the database
        List<Teletravail> teletravailList = teletravailRepository.findAll();
        assertThat(teletravailList).hasSize(databaseSizeBeforeUpdate);
        Teletravail testTeletravail = teletravailList.get(teletravailList.size() - 1);
        assertThat(testTeletravail.getIpDGFIPFixe()).isEqualTo(UPDATED_IP_DGFIP_FIXE);
        assertThat(testTeletravail.getIpTeletravail()).isEqualTo(UPDATED_IP_TELETRAVAIL);
        assertThat(testTeletravail.getIpIPSEC()).isEqualTo(UPDATED_IP_IPSEC);
        assertThat(testTeletravail.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
    }

    @Test
    void fullUpdateTeletravailWithPatch() throws Exception {
        // Initialize the database
        teletravailRepository.save(teletravail);

        int databaseSizeBeforeUpdate = teletravailRepository.findAll().size();

        // Update the teletravail using partial update
        Teletravail partialUpdatedTeletravail = new Teletravail();
        partialUpdatedTeletravail.setId(teletravail.getId());

        partialUpdatedTeletravail
            .ipDGFIPFixe(UPDATED_IP_DGFIP_FIXE)
            .ipTeletravail(UPDATED_IP_TELETRAVAIL)
            .ipIPSEC(UPDATED_IP_IPSEC)
            .isActif(UPDATED_IS_ACTIF);

        restTeletravailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeletravail.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTeletravail))
            )
            .andExpect(status().isOk());

        // Validate the Teletravail in the database
        List<Teletravail> teletravailList = teletravailRepository.findAll();
        assertThat(teletravailList).hasSize(databaseSizeBeforeUpdate);
        Teletravail testTeletravail = teletravailList.get(teletravailList.size() - 1);
        assertThat(testTeletravail.getIpDGFIPFixe()).isEqualTo(UPDATED_IP_DGFIP_FIXE);
        assertThat(testTeletravail.getIpTeletravail()).isEqualTo(UPDATED_IP_TELETRAVAIL);
        assertThat(testTeletravail.getIpIPSEC()).isEqualTo(UPDATED_IP_IPSEC);
        assertThat(testTeletravail.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
    }

    @Test
    void patchNonExistingTeletravail() throws Exception {
        int databaseSizeBeforeUpdate = teletravailRepository.findAll().size();
        teletravail.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeletravailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, teletravail.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(teletravail))
            )
            .andExpect(status().isBadRequest());

        // Validate the Teletravail in the database
        List<Teletravail> teletravailList = teletravailRepository.findAll();
        assertThat(teletravailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchTeletravail() throws Exception {
        int databaseSizeBeforeUpdate = teletravailRepository.findAll().size();
        teletravail.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeletravailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(teletravail))
            )
            .andExpect(status().isBadRequest());

        // Validate the Teletravail in the database
        List<Teletravail> teletravailList = teletravailRepository.findAll();
        assertThat(teletravailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamTeletravail() throws Exception {
        int databaseSizeBeforeUpdate = teletravailRepository.findAll().size();
        teletravail.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeletravailMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(teletravail))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Teletravail in the database
        List<Teletravail> teletravailList = teletravailRepository.findAll();
        assertThat(teletravailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteTeletravail() throws Exception {
        // Initialize the database
        teletravailRepository.save(teletravail);

        int databaseSizeBeforeDelete = teletravailRepository.findAll().size();

        // Delete the teletravail
        restTeletravailMockMvc
            .perform(delete(ENTITY_API_URL_ID, teletravail.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Teletravail> teletravailList = teletravailRepository.findAll();
        assertThat(teletravailList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
