package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Site;
import com.mycompany.myapp.domain.enumeration.Ville;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Localisation.
 */
@Document(collection = "localisation")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Localisation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("ville")
    private Ville ville;

    @Field("site")
    private Site site;

    @Field("batiment")
    private String batiment;

    @Field("etage")
    private Integer etage;

    @Field("bureau")
    private String bureau;

    @Field("place")
    private String place;

    @DBRef
    @Field("collaborateur")
    @JsonIgnoreProperties(value = { "projets", "equipements", "localisation", "cps", "dps" }, allowSetters = true)
    private Set<Collaborateur> collaborateurs = new HashSet<>();

    @DBRef
    @Field("equipement")
    @JsonIgnoreProperties(value = { "numeroStation", "teletravail", "localisation", "collaborateurs" }, allowSetters = true)
    private Set<Equipement> equipements = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Localisation id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Ville getVille() {
        return this.ville;
    }

    public Localisation ville(Ville ville) {
        this.setVille(ville);
        return this;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }

    public Site getSite() {
        return this.site;
    }

    public Localisation site(Site site) {
        this.setSite(site);
        return this;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public String getBatiment() {
        return this.batiment;
    }

    public Localisation batiment(String batiment) {
        this.setBatiment(batiment);
        return this;
    }

    public void setBatiment(String batiment) {
        this.batiment = batiment;
    }

    public Integer getEtage() {
        return this.etage;
    }

    public Localisation etage(Integer etage) {
        this.setEtage(etage);
        return this;
    }

    public void setEtage(Integer etage) {
        this.etage = etage;
    }

    public String getBureau() {
        return this.bureau;
    }

    public Localisation bureau(String bureau) {
        this.setBureau(bureau);
        return this;
    }

    public void setBureau(String bureau) {
        this.bureau = bureau;
    }

    public String getPlace() {
        return this.place;
    }

    public Localisation place(String place) {
        this.setPlace(place);
        return this;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Set<Collaborateur> getCollaborateurs() {
        return this.collaborateurs;
    }

    public void setCollaborateurs(Set<Collaborateur> collaborateurs) {
        if (this.collaborateurs != null) {
            this.collaborateurs.forEach(i -> i.setLocalisation(null));
        }
        if (collaborateurs != null) {
            collaborateurs.forEach(i -> i.setLocalisation(this));
        }
        this.collaborateurs = collaborateurs;
    }

    public Localisation collaborateurs(Set<Collaborateur> collaborateurs) {
        this.setCollaborateurs(collaborateurs);
        return this;
    }

    public Localisation addCollaborateur(Collaborateur collaborateur) {
        this.collaborateurs.add(collaborateur);
        collaborateur.setLocalisation(this);
        return this;
    }

    public Localisation removeCollaborateur(Collaborateur collaborateur) {
        this.collaborateurs.remove(collaborateur);
        collaborateur.setLocalisation(null);
        return this;
    }

    public Set<Equipement> getEquipements() {
        return this.equipements;
    }

    public void setEquipements(Set<Equipement> equipements) {
        if (this.equipements != null) {
            this.equipements.forEach(i -> i.setLocalisation(null));
        }
        if (equipements != null) {
            equipements.forEach(i -> i.setLocalisation(this));
        }
        this.equipements = equipements;
    }

    public Localisation equipements(Set<Equipement> equipements) {
        this.setEquipements(equipements);
        return this;
    }

    public Localisation addEquipement(Equipement equipement) {
        this.equipements.add(equipement);
        equipement.setLocalisation(this);
        return this;
    }

    public Localisation removeEquipement(Equipement equipement) {
        this.equipements.remove(equipement);
        equipement.setLocalisation(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Localisation)) {
            return false;
        }
        return id != null && id.equals(((Localisation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Localisation{" +
            "id=" + getId() +
            ", ville='" + getVille() + "'" +
            ", site='" + getSite() + "'" +
            ", batiment='" + getBatiment() + "'" +
            ", etage=" + getEtage() +
            ", bureau='" + getBureau() + "'" +
            ", place='" + getPlace() + "'" +
            "}";
    }
}
