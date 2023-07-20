package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.StatutCollaborateur;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Collaborateur.
 */
@Document(collection = "collaborateur")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Collaborateur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("identifiant")
    private String identifiant;

    @Field("matricule")
    private String matricule;

    @Field("societe")
    private String societe;

    @Field("email")
    private String email;

    // @Pattern(regexp = "^((\\+)33|0|0033)[1-9](\\d{2}){4}$")
    @Field("tel")
    private String tel;

    @Field("profil")
    private String profil;

    @Field("date_entree")
    private LocalDate dateEntree;

    @Field("date_sortie")
    private LocalDate dateSortie;

    @Field("statut")
    private StatutCollaborateur statut;

    @Field("commentaire")
    private String commentaire;

    @DBRef
    @Field("projets")
    @JsonIgnoreProperties(value = { "cps", "dps", "collaborateurs" }, allowSetters = true)
    private Set<Projet> projets = new HashSet<>();

    @DBRef
    @Field("equipements")
    @JsonIgnoreProperties(value = { "numeroStation", "teletravail", "localisation", "collaborateurs" }, allowSetters = true)
    private Set<Equipement> equipements = new HashSet<>();

    @DBRef
    @Field("localisation")
    @JsonIgnoreProperties(value = { "collaborateurs", "equipements" }, allowSetters = true)
    private Localisation localisation;

    @DBRef
    @Field("cps")
    @JsonIgnoreProperties(value = { "cps", "dps", "collaborateurs" }, allowSetters = true)
    private Set<Projet> cps = new HashSet<>();

    @DBRef
    @Field("dps")
    @JsonIgnoreProperties(value = { "cps", "dps", "collaborateurs" }, allowSetters = true)
    private Set<Projet> dps = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Collaborateur id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdentifiant() {
        return this.identifiant;
    }

    public Collaborateur identifiant(String identifiant) {
        this.setIdentifiant(identifiant);
        return this;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getMatricule() {
        return this.matricule;
    }

    public Collaborateur matricule(String matricule) {
        this.setMatricule(matricule);
        return this;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getSociete() {
        return this.societe;
    }

    public Collaborateur societe(String societe) {
        this.setSociete(societe);
        return this;
    }

    public void setSociete(String societe) {
        this.societe = societe;
    }

    public String getEmail() {
        return this.email;
    }

    public Collaborateur email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return this.tel;
    }

    public Collaborateur tel(String tel) {
        this.setTel(tel);
        return this;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getProfil() {
        return this.profil;
    }

    public Collaborateur profil(String profil) {
        this.setProfil(profil);
        return this;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }

    public LocalDate getDateEntree() {
        return this.dateEntree;
    }

    public Collaborateur dateEntree(LocalDate dateEntree) {
        this.setDateEntree(dateEntree);
        return this;
    }

    public void setDateEntree(LocalDate dateEntree) {
        this.dateEntree = dateEntree;
    }

    public LocalDate getDateSortie() {
        return this.dateSortie;
    }

    public Collaborateur dateSortie(LocalDate dateSortie) {
        this.setDateSortie(dateSortie);
        return this;
    }

    public void setDateSortie(LocalDate dateSortie) {
        this.dateSortie = dateSortie;
    }

    public StatutCollaborateur getStatut() {
        return this.statut;
    }

    public Collaborateur statut(StatutCollaborateur statut) {
        this.setStatut(statut);
        return this;
    }

    public void setStatut(StatutCollaborateur statut) {
        this.statut = statut;
    }

    public String getCommentaire() {
        return this.commentaire;
    }

    public Collaborateur commentaire(String commentaire) {
        this.setCommentaire(commentaire);
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Set<Projet> getProjets() {
        return this.projets;
    }

    public void setProjets(Set<Projet> projets) {
        this.projets = projets;
    }

    public Collaborateur projets(Set<Projet> projets) {
        this.setProjets(projets);
        return this;
    }

    public Collaborateur addProjet(Projet projet) {
        this.projets.add(projet);
        projet.getCollaborateurs().add(this);
        return this;
    }

    public Collaborateur removeProjet(Projet projet) {
        this.projets.remove(projet);
        projet.getCollaborateurs().remove(this);
        return this;
    }

    public Set<Equipement> getEquipements() {
        return this.equipements;
    }

    public void setEquipements(Set<Equipement> equipements) {
        this.equipements = equipements;
    }

    public Collaborateur equipements(Set<Equipement> equipements) {
        this.setEquipements(equipements);
        return this;
    }

    public Collaborateur addEquipements(Equipement equipement) {
        this.equipements.add(equipement);
        equipement.getCollaborateurs().add(this);
        return this;
    }

    public Collaborateur removeEquipements(Equipement equipement) {
        this.equipements.remove(equipement);
        equipement.getCollaborateurs().remove(this);
        return this;
    }

    public Localisation getLocalisation() {
        return this.localisation;
    }

    public void setLocalisation(Localisation localisation) {
        this.localisation = localisation;
    }

    public Collaborateur localisation(Localisation localisation) {
        this.setLocalisation(localisation);
        return this;
    }

    public Set<Projet> getCps() {
        return this.cps;
    }

    public void setCps(Set<Projet> projets) {
        if (this.cps != null) {
            this.cps.forEach(i -> i.removeCp(this));
        }
        if (projets != null) {
            projets.forEach(i -> i.addCp(this));
        }
        this.cps = projets;
    }

    public Collaborateur cps(Set<Projet> projets) {
        this.setCps(projets);
        return this;
    }

    public Collaborateur addCp(Projet projet) {
        this.cps.add(projet);
        projet.getCps().add(this);
        return this;
    }

    public Collaborateur removeCp(Projet projet) {
        this.cps.remove(projet);
        projet.getCps().remove(this);
        return this;
    }

    public Set<Projet> getDps() {
        return this.dps;
    }

    public void setDps(Set<Projet> projets) {
        if (this.dps != null) {
            this.dps.forEach(i -> i.removeDp(this));
        }
        if (projets != null) {
            projets.forEach(i -> i.addDp(this));
        }
        this.dps = projets;
    }

    public Collaborateur dps(Set<Projet> projets) {
        this.setDps(projets);
        return this;
    }

    public Collaborateur addDp(Projet projet) {
        this.dps.add(projet);
        projet.getDps().add(this);
        return this;
    }

    public Collaborateur removeDp(Projet projet) {
        this.dps.remove(projet);
        projet.getDps().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Collaborateur)) {
            return false;
        }
        return id != null && id.equals(((Collaborateur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Collaborateur{" +
            "id=" + getId() +
            ", identifiant='" + getIdentifiant() + "'" +
            ", matricule='" + getMatricule() + "'" +
            ", societe='" + getSociete() + "'" +
            ", email='" + getEmail() + "'" +
            ", tel='" + getTel() + "'" +
            ", profil='" + getProfil() + "'" +
            ", dateEntree='" + getDateEntree() + "'" +
            ", dateSortie='" + getDateSortie() + "'" +
            ", statut='" + getStatut() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            "}";
    }
}
