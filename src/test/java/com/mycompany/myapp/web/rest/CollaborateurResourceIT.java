package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Collaborateur;
import com.mycompany.myapp.domain.enumeration.StatutCollaborateur;
import com.mycompany.myapp.repository.CollaborateurRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link CollaborateurResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CollaborateurResourceIT {

    private static final String DEFAULT_IDENTIFIANT = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIANT = "BBBBBBBBBB";

    private static final String DEFAULT_MATRICULE = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULE = "BBBBBBBBBB";

    private static final String DEFAULT_SOCIETE = "AAAAAAAAAA";
    private static final String UPDATED_SOCIETE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TEL = "0544136352";
    private static final String UPDATED_TEL = "+33986390639";

    private static final String DEFAULT_PROFIL = "AAAAAAAAAA";
    private static final String UPDATED_PROFIL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_ENTREE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ENTREE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_SORTIE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_SORTIE = LocalDate.now(ZoneId.systemDefault());

    private static final StatutCollaborateur DEFAULT_STATUT = StatutCollaborateur.Arrivee;
    private static final StatutCollaborateur UPDATED_STATUT = StatutCollaborateur.Sortie;

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/collaborateurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private CollaborateurRepository collaborateurRepository;

    @Mock
    private CollaborateurRepository collaborateurRepositoryMock;

    @Autowired
    private MockMvc restCollaborateurMockMvc;

    private Collaborateur collaborateur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Collaborateur createEntity() {
        Collaborateur collaborateur = new Collaborateur()
            .identifiant(DEFAULT_IDENTIFIANT)
            .matricule(DEFAULT_MATRICULE)
            .societe(DEFAULT_SOCIETE)
            .email(DEFAULT_EMAIL)
            .tel(DEFAULT_TEL)
            .profil(DEFAULT_PROFIL)
            .dateEntree(DEFAULT_DATE_ENTREE)
            .dateSortie(DEFAULT_DATE_SORTIE)
            .statut(DEFAULT_STATUT)
            .commentaire(DEFAULT_COMMENTAIRE);
        return collaborateur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Collaborateur createUpdatedEntity() {
        Collaborateur collaborateur = new Collaborateur()
            .identifiant(UPDATED_IDENTIFIANT)
            .matricule(UPDATED_MATRICULE)
            .societe(UPDATED_SOCIETE)
            .email(UPDATED_EMAIL)
            .tel(UPDATED_TEL)
            .profil(UPDATED_PROFIL)
            .dateEntree(UPDATED_DATE_ENTREE)
            .dateSortie(UPDATED_DATE_SORTIE)
            .statut(UPDATED_STATUT)
            .commentaire(UPDATED_COMMENTAIRE);
        return collaborateur;
    }

    @BeforeEach
    public void initTest() {
        collaborateurRepository.deleteAll();
        collaborateur = createEntity();
    }

    @Test
    void createCollaborateur() throws Exception {
        int databaseSizeBeforeCreate = collaborateurRepository.findAll().size();
        // Create the Collaborateur
        restCollaborateurMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(collaborateur))
            )
            .andExpect(status().isCreated());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeCreate + 1);
        Collaborateur testCollaborateur = collaborateurList.get(collaborateurList.size() - 1);
        assertThat(testCollaborateur.getIdentifiant()).isEqualTo(DEFAULT_IDENTIFIANT);
        assertThat(testCollaborateur.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
        assertThat(testCollaborateur.getSociete()).isEqualTo(DEFAULT_SOCIETE);
        assertThat(testCollaborateur.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCollaborateur.getTel()).isEqualTo(DEFAULT_TEL);
        assertThat(testCollaborateur.getProfil()).isEqualTo(DEFAULT_PROFIL);
        assertThat(testCollaborateur.getDateEntree()).isEqualTo(DEFAULT_DATE_ENTREE);
        assertThat(testCollaborateur.getDateSortie()).isEqualTo(DEFAULT_DATE_SORTIE);
        assertThat(testCollaborateur.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testCollaborateur.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
    }

    @Test
    void createCollaborateurWithExistingId() throws Exception {
        // Create the Collaborateur with an existing ID
        collaborateur.setId("existing_id");

        int databaseSizeBeforeCreate = collaborateurRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollaborateurMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(collaborateur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkIdentifiantIsRequired() throws Exception {
        int databaseSizeBeforeTest = collaborateurRepository.findAll().size();
        // set the field null
        collaborateur.setIdentifiant(null);

        // Create the Collaborateur, which fails.

        restCollaborateurMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(collaborateur))
            )
            .andExpect(status().isBadRequest());

        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllCollaborateurs() throws Exception {
        // Initialize the database
        collaborateurRepository.save(collaborateur);

        // Get all the collaborateurList
        restCollaborateurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collaborateur.getId())))
            .andExpect(jsonPath("$.[*].identifiant").value(hasItem(DEFAULT_IDENTIFIANT)))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE)))
            .andExpect(jsonPath("$.[*].societe").value(hasItem(DEFAULT_SOCIETE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL)))
            .andExpect(jsonPath("$.[*].profil").value(hasItem(DEFAULT_PROFIL)))
            .andExpect(jsonPath("$.[*].dateEntree").value(hasItem(DEFAULT_DATE_ENTREE.toString())))
            .andExpect(jsonPath("$.[*].dateSortie").value(hasItem(DEFAULT_DATE_SORTIE.toString())))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCollaborateursWithEagerRelationshipsIsEnabled() throws Exception {
        when(collaborateurRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCollaborateurMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(collaborateurRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCollaborateursWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(collaborateurRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCollaborateurMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(collaborateurRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getCollaborateur() throws Exception {
        // Initialize the database
        collaborateurRepository.save(collaborateur);

        // Get the collaborateur
        restCollaborateurMockMvc
            .perform(get(ENTITY_API_URL_ID, collaborateur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(collaborateur.getId()))
            .andExpect(jsonPath("$.identifiant").value(DEFAULT_IDENTIFIANT))
            .andExpect(jsonPath("$.matricule").value(DEFAULT_MATRICULE))
            .andExpect(jsonPath("$.societe").value(DEFAULT_SOCIETE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.tel").value(DEFAULT_TEL))
            .andExpect(jsonPath("$.profil").value(DEFAULT_PROFIL))
            .andExpect(jsonPath("$.dateEntree").value(DEFAULT_DATE_ENTREE.toString()))
            .andExpect(jsonPath("$.dateSortie").value(DEFAULT_DATE_SORTIE.toString()))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT.toString()))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE));
    }

    @Test
    void getNonExistingCollaborateur() throws Exception {
        // Get the collaborateur
        restCollaborateurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingCollaborateur() throws Exception {
        // Initialize the database
        collaborateurRepository.save(collaborateur);

        int databaseSizeBeforeUpdate = collaborateurRepository.findAll().size();

        // Update the collaborateur
        Collaborateur updatedCollaborateur = collaborateurRepository.findById(collaborateur.getId()).orElseThrow();
        updatedCollaborateur
            .identifiant(UPDATED_IDENTIFIANT)
            .matricule(UPDATED_MATRICULE)
            .societe(UPDATED_SOCIETE)
            .email(UPDATED_EMAIL)
            .tel(UPDATED_TEL)
            .profil(UPDATED_PROFIL)
            .dateEntree(UPDATED_DATE_ENTREE)
            .dateSortie(UPDATED_DATE_SORTIE)
            .statut(UPDATED_STATUT)
            .commentaire(UPDATED_COMMENTAIRE);

        restCollaborateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCollaborateur.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCollaborateur))
            )
            .andExpect(status().isOk());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeUpdate);
        Collaborateur testCollaborateur = collaborateurList.get(collaborateurList.size() - 1);
        assertThat(testCollaborateur.getIdentifiant()).isEqualTo(UPDATED_IDENTIFIANT);
        assertThat(testCollaborateur.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testCollaborateur.getSociete()).isEqualTo(UPDATED_SOCIETE);
        assertThat(testCollaborateur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCollaborateur.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testCollaborateur.getProfil()).isEqualTo(UPDATED_PROFIL);
        assertThat(testCollaborateur.getDateEntree()).isEqualTo(UPDATED_DATE_ENTREE);
        assertThat(testCollaborateur.getDateSortie()).isEqualTo(UPDATED_DATE_SORTIE);
        assertThat(testCollaborateur.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testCollaborateur.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
    }

    @Test
    void putNonExistingCollaborateur() throws Exception {
        int databaseSizeBeforeUpdate = collaborateurRepository.findAll().size();
        collaborateur.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollaborateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, collaborateur.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(collaborateur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCollaborateur() throws Exception {
        int databaseSizeBeforeUpdate = collaborateurRepository.findAll().size();
        collaborateur.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollaborateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(collaborateur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCollaborateur() throws Exception {
        int databaseSizeBeforeUpdate = collaborateurRepository.findAll().size();
        collaborateur.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollaborateurMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(collaborateur))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCollaborateurWithPatch() throws Exception {
        // Initialize the database
        collaborateurRepository.save(collaborateur);

        int databaseSizeBeforeUpdate = collaborateurRepository.findAll().size();

        // Update the collaborateur using partial update
        Collaborateur partialUpdatedCollaborateur = new Collaborateur();
        partialUpdatedCollaborateur.setId(collaborateur.getId());

        partialUpdatedCollaborateur
            .email(UPDATED_EMAIL)
            .tel(UPDATED_TEL)
            .profil(UPDATED_PROFIL)
            .dateEntree(UPDATED_DATE_ENTREE)
            .dateSortie(UPDATED_DATE_SORTIE)
            .statut(UPDATED_STATUT)
            .commentaire(UPDATED_COMMENTAIRE);

        restCollaborateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCollaborateur.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCollaborateur))
            )
            .andExpect(status().isOk());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeUpdate);
        Collaborateur testCollaborateur = collaborateurList.get(collaborateurList.size() - 1);
        assertThat(testCollaborateur.getIdentifiant()).isEqualTo(DEFAULT_IDENTIFIANT);
        assertThat(testCollaborateur.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
        assertThat(testCollaborateur.getSociete()).isEqualTo(DEFAULT_SOCIETE);
        assertThat(testCollaborateur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCollaborateur.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testCollaborateur.getProfil()).isEqualTo(UPDATED_PROFIL);
        assertThat(testCollaborateur.getDateEntree()).isEqualTo(UPDATED_DATE_ENTREE);
        assertThat(testCollaborateur.getDateSortie()).isEqualTo(UPDATED_DATE_SORTIE);
        assertThat(testCollaborateur.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testCollaborateur.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
    }

    @Test
    void fullUpdateCollaborateurWithPatch() throws Exception {
        // Initialize the database
        collaborateurRepository.save(collaborateur);

        int databaseSizeBeforeUpdate = collaborateurRepository.findAll().size();

        // Update the collaborateur using partial update
        Collaborateur partialUpdatedCollaborateur = new Collaborateur();
        partialUpdatedCollaborateur.setId(collaborateur.getId());

        partialUpdatedCollaborateur
            .identifiant(UPDATED_IDENTIFIANT)
            .matricule(UPDATED_MATRICULE)
            .societe(UPDATED_SOCIETE)
            .email(UPDATED_EMAIL)
            .tel(UPDATED_TEL)
            .profil(UPDATED_PROFIL)
            .dateEntree(UPDATED_DATE_ENTREE)
            .dateSortie(UPDATED_DATE_SORTIE)
            .statut(UPDATED_STATUT)
            .commentaire(UPDATED_COMMENTAIRE);

        restCollaborateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCollaborateur.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCollaborateur))
            )
            .andExpect(status().isOk());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeUpdate);
        Collaborateur testCollaborateur = collaborateurList.get(collaborateurList.size() - 1);
        assertThat(testCollaborateur.getIdentifiant()).isEqualTo(UPDATED_IDENTIFIANT);
        assertThat(testCollaborateur.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testCollaborateur.getSociete()).isEqualTo(UPDATED_SOCIETE);
        assertThat(testCollaborateur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCollaborateur.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testCollaborateur.getProfil()).isEqualTo(UPDATED_PROFIL);
        assertThat(testCollaborateur.getDateEntree()).isEqualTo(UPDATED_DATE_ENTREE);
        assertThat(testCollaborateur.getDateSortie()).isEqualTo(UPDATED_DATE_SORTIE);
        assertThat(testCollaborateur.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testCollaborateur.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
    }

    @Test
    void patchNonExistingCollaborateur() throws Exception {
        int databaseSizeBeforeUpdate = collaborateurRepository.findAll().size();
        collaborateur.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollaborateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, collaborateur.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(collaborateur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCollaborateur() throws Exception {
        int databaseSizeBeforeUpdate = collaborateurRepository.findAll().size();
        collaborateur.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollaborateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(collaborateur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCollaborateur() throws Exception {
        int databaseSizeBeforeUpdate = collaborateurRepository.findAll().size();
        collaborateur.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollaborateurMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(collaborateur))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCollaborateur() throws Exception {
        // Initialize the database
        collaborateurRepository.save(collaborateur);

        int databaseSizeBeforeDelete = collaborateurRepository.findAll().size();

        // Delete the collaborateur
        restCollaborateurMockMvc
            .perform(delete(ENTITY_API_URL_ID, collaborateur.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
