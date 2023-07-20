package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Projet;
import com.mycompany.myapp.domain.enumeration.SocieteCPDP;
import com.mycompany.myapp.repository.ProjetRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link ProjetResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProjetResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_STRUCTURE = "AAAAAAAAAA";
    private static final String UPDATED_STRUCTURE = "BBBBBBBBBB";

    private static final SocieteCPDP DEFAULT_SOCIETE_CPDP = SocieteCPDP.CGI;
    private static final SocieteCPDP UPDATED_SOCIETE_CPDP = SocieteCPDP.SSG;

    private static final String ENTITY_API_URL = "/api/projets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ProjetRepository projetRepository;

    @Mock
    private ProjetRepository projetRepositoryMock;

    @Autowired
    private MockMvc restProjetMockMvc;

    private Projet projet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projet createEntity() {
        Projet projet = new Projet().nom(DEFAULT_NOM).structure(DEFAULT_STRUCTURE).societeCPDP(DEFAULT_SOCIETE_CPDP);
        return projet;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projet createUpdatedEntity() {
        Projet projet = new Projet().nom(UPDATED_NOM).structure(UPDATED_STRUCTURE).societeCPDP(UPDATED_SOCIETE_CPDP);
        return projet;
    }

    @BeforeEach
    public void initTest() {
        projetRepository.deleteAll();
        projet = createEntity();
    }

    @Test
    void createProjet() throws Exception {
        int databaseSizeBeforeCreate = projetRepository.findAll().size();
        // Create the Projet
        restProjetMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projet))
            )
            .andExpect(status().isCreated());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeCreate + 1);
        Projet testProjet = projetList.get(projetList.size() - 1);
        assertThat(testProjet.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testProjet.getStructure()).isEqualTo(DEFAULT_STRUCTURE);
        assertThat(testProjet.getSocieteCPDP()).isEqualTo(DEFAULT_SOCIETE_CPDP);
    }

    @Test
    void createProjetWithExistingId() throws Exception {
        // Create the Projet with an existing ID
        projet.setId("existing_id");

        int databaseSizeBeforeCreate = projetRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjetMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projet))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = projetRepository.findAll().size();
        // set the field null
        projet.setNom(null);

        // Create the Projet, which fails.

        restProjetMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projet))
            )
            .andExpect(status().isBadRequest());

        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllProjets() throws Exception {
        // Initialize the database
        projetRepository.save(projet);

        // Get all the projetList
        restProjetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projet.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].structure").value(hasItem(DEFAULT_STRUCTURE)))
            .andExpect(jsonPath("$.[*].societeCPDP").value(hasItem(DEFAULT_SOCIETE_CPDP.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProjetsWithEagerRelationshipsIsEnabled() throws Exception {
        when(projetRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProjetMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(projetRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProjetsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(projetRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProjetMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(projetRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getProjet() throws Exception {
        // Initialize the database
        projetRepository.save(projet);

        // Get the projet
        restProjetMockMvc
            .perform(get(ENTITY_API_URL_ID, projet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projet.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.structure").value(DEFAULT_STRUCTURE))
            .andExpect(jsonPath("$.societeCPDP").value(DEFAULT_SOCIETE_CPDP.toString()));
    }

    @Test
    void getNonExistingProjet() throws Exception {
        // Get the projet
        restProjetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingProjet() throws Exception {
        // Initialize the database
        projetRepository.save(projet);

        int databaseSizeBeforeUpdate = projetRepository.findAll().size();

        // Update the projet
        Projet updatedProjet = projetRepository.findById(projet.getId()).orElseThrow();
        updatedProjet.nom(UPDATED_NOM).structure(UPDATED_STRUCTURE).societeCPDP(UPDATED_SOCIETE_CPDP);

        restProjetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProjet.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProjet))
            )
            .andExpect(status().isOk());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
        Projet testProjet = projetList.get(projetList.size() - 1);
        assertThat(testProjet.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testProjet.getStructure()).isEqualTo(UPDATED_STRUCTURE);
        assertThat(testProjet.getSocieteCPDP()).isEqualTo(UPDATED_SOCIETE_CPDP);
    }

    @Test
    void putNonExistingProjet() throws Exception {
        int databaseSizeBeforeUpdate = projetRepository.findAll().size();
        projet.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projet.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projet))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchProjet() throws Exception {
        int databaseSizeBeforeUpdate = projetRepository.findAll().size();
        projet.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projet))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamProjet() throws Exception {
        int databaseSizeBeforeUpdate = projetRepository.findAll().size();
        projet.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjetMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projet))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateProjetWithPatch() throws Exception {
        // Initialize the database
        projetRepository.save(projet);

        int databaseSizeBeforeUpdate = projetRepository.findAll().size();

        // Update the projet using partial update
        Projet partialUpdatedProjet = new Projet();
        partialUpdatedProjet.setId(projet.getId());

        partialUpdatedProjet.nom(UPDATED_NOM);

        restProjetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjet.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjet))
            )
            .andExpect(status().isOk());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
        Projet testProjet = projetList.get(projetList.size() - 1);
        assertThat(testProjet.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testProjet.getStructure()).isEqualTo(DEFAULT_STRUCTURE);
        assertThat(testProjet.getSocieteCPDP()).isEqualTo(DEFAULT_SOCIETE_CPDP);
    }

    @Test
    void fullUpdateProjetWithPatch() throws Exception {
        // Initialize the database
        projetRepository.save(projet);

        int databaseSizeBeforeUpdate = projetRepository.findAll().size();

        // Update the projet using partial update
        Projet partialUpdatedProjet = new Projet();
        partialUpdatedProjet.setId(projet.getId());

        partialUpdatedProjet.nom(UPDATED_NOM).structure(UPDATED_STRUCTURE).societeCPDP(UPDATED_SOCIETE_CPDP);

        restProjetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjet.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjet))
            )
            .andExpect(status().isOk());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
        Projet testProjet = projetList.get(projetList.size() - 1);
        assertThat(testProjet.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testProjet.getStructure()).isEqualTo(UPDATED_STRUCTURE);
        assertThat(testProjet.getSocieteCPDP()).isEqualTo(UPDATED_SOCIETE_CPDP);
    }

    @Test
    void patchNonExistingProjet() throws Exception {
        int databaseSizeBeforeUpdate = projetRepository.findAll().size();
        projet.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projet.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projet))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchProjet() throws Exception {
        int databaseSizeBeforeUpdate = projetRepository.findAll().size();
        projet.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projet))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamProjet() throws Exception {
        int databaseSizeBeforeUpdate = projetRepository.findAll().size();
        projet.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjetMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projet))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteProjet() throws Exception {
        // Initialize the database
        projetRepository.save(projet);

        int databaseSizeBeforeDelete = projetRepository.findAll().size();

        // Delete the projet
        restProjetMockMvc
            .perform(delete(ENTITY_API_URL_ID, projet.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
