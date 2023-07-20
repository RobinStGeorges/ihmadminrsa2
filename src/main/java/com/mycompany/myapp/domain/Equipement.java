package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.EquipementType;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Equipement.
 */
@Document(collection = "equipement")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Equipement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    // @Pattern(
    //     regexp = "[A-Za-z-0-9]{1}[A-Za-z-0-9]{1}[:|-]{1}[A-Za-z-0-9]{1}[A-Za-z-0-9]{1}[:|-]{1}[A-Za-z-0-9]{1}[A-Za-z-0-9]{1}[:|-]{1}[A-Za-z-0-9]{1}[A-Za-z-0-9]{1}[:|-]{1}[A-Za-z-0-9]{1}[A-Za-z-0-9]{1}[:|-]{0,1}[A-Za-z-0-9]{0,1}[A-Za-z-0-9]{0,1}"
    // )
    @Field("adresse_physique")
    private String adressePhysique;

    @Field("equipement_type")
    private EquipementType equipementType;

    @Field("ip")
    private String ip;

    @Field("bios_version")
    private String biosVersion;

    @Field("commentaire")
    private String commentaire;

    @Pattern(regexp = "[S]{1}[0-9]{5,8}")
    @Field("asset")
    private String asset;

    @Field("numero_serie")
    private String numeroSerie;

    @Field("modele")
    private String modele;

    @Field("generation")
    private String generation;

    @Field("marque")
    private String marque;

    @Field("fonctionnel")
    private Boolean fonctionnel;

    @Field("cle_anti_vol_admin")
    private String cleAntiVolAdmin;

    @Field("cle_anti_vol_collaborateur")
    private String cleAntiVolCollaborateur;

    @DBRef
    private NumeroStation numeroStation;

    @DBRef
    private Teletravail teletravail;

    @DBRef
    @Field("localisation")
    @JsonIgnoreProperties(value = { "collaborateurs", "equipements" }, allowSetters = true)
    private Localisation localisation;

    @DBRef
    @Field("collaborateurs")
    @JsonIgnoreProperties(value = { "projets", "equipements", "localisation", "cps", "dps" }, allowSetters = true)
    private Set<Collaborateur> collaborateurs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Equipement id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdressePhysique() {
        return this.adressePhysique;
    }

    public Equipement adressePhysique(String adressePhysique) {
        this.setAdressePhysique(adressePhysique);
        return this;
    }

    public void setAdressePhysique(String adressePhysique) {
        this.adressePhysique = adressePhysique;
    }

    public EquipementType getEquipementType() {
        return this.equipementType;
    }

    public Equipement equipementType(EquipementType equipementType) {
        this.setEquipementType(equipementType);
        return this;
    }

    public void setEquipementType(EquipementType equipementType) {
        this.equipementType = equipementType;
    }

    public String getIp() {
        return this.ip;
    }

    public Equipement ip(String ip) {
        this.setIp(ip);
        return this;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getBiosVersion() {
        return this.biosVersion;
    }

    public Equipement biosVersion(String biosVersion) {
        this.setBiosVersion(biosVersion);
        return this;
    }

    public void setBiosVersion(String biosVersion) {
        this.biosVersion = biosVersion;
    }

    public String getCommentaire() {
        return this.commentaire;
    }

    public Equipement commentaire(String commentaire) {
        this.setCommentaire(commentaire);
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getAsset() {
        return this.asset;
    }

    public Equipement asset(String asset) {
        this.setAsset(asset);
        return this;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getNumeroSerie() {
        return this.numeroSerie;
    }

    public Equipement numeroSerie(String numeroSerie) {
        this.setNumeroSerie(numeroSerie);
        return this;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getModele() {
        return this.modele;
    }

    public Equipement modele(String modele) {
        this.setModele(modele);
        return this;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getGeneration() {
        return this.generation;
    }

    public Equipement generation(String generation) {
        this.setGeneration(generation);
        return this;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    public String getMarque() {
        return this.marque;
    }

    public Equipement marque(String marque) {
        this.setMarque(marque);
        return this;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public Boolean getFonctionnel() {
        return this.fonctionnel;
    }

    public Equipement fonctionnel(Boolean fonctionnel) {
        this.setFonctionnel(fonctionnel);
        return this;
    }

    public void setFonctionnel(Boolean fonctionnel) {
        this.fonctionnel = fonctionnel;
    }

    public String getCleAntiVolAdmin() {
        return this.cleAntiVolAdmin;
    }

    public Equipement cleAntiVolAdmin(String cleAntiVolAdmin) {
        this.setCleAntiVolAdmin(cleAntiVolAdmin);
        return this;
    }

    public void setCleAntiVolAdmin(String cleAntiVolAdmin) {
        this.cleAntiVolAdmin = cleAntiVolAdmin;
    }

    public String getCleAntiVolCollaborateur() {
        return this.cleAntiVolCollaborateur;
    }

    public Equipement cleAntiVolCollaborateur(String cleAntiVolCollaborateur) {
        this.setCleAntiVolCollaborateur(cleAntiVolCollaborateur);
        return this;
    }

    public void setCleAntiVolCollaborateur(String cleAntiVolCollaborateur) {
        this.cleAntiVolCollaborateur = cleAntiVolCollaborateur;
    }

    public NumeroStation getNumeroStation() {
        return this.numeroStation;
    }

    public void setNumeroStation(NumeroStation numeroStation) {
        if (this.numeroStation != null) {
            this.numeroStation.setEquipement(null);
        }
        if (numeroStation != null) {
            numeroStation.setEquipement(this);
        }
        this.numeroStation = numeroStation;
    }

    public Equipement numeroStation(NumeroStation numeroStation) {
        this.setNumeroStation(numeroStation);
        return this;
    }

    public Teletravail getTeletravail() {
        return this.teletravail;
    }

    public void setTeletravail(Teletravail teletravail) {
        if (this.teletravail != null) {
            this.teletravail.setEquipement(null);
        }
        if (teletravail != null) {
            teletravail.setEquipement(this);
        }
        this.teletravail = teletravail;
    }

    public Equipement teletravail(Teletravail teletravail) {
        this.setTeletravail(teletravail);
        return this;
    }

    public Localisation getLocalisation() {
        return this.localisation;
    }

    public void setLocalisation(Localisation localisation) {
        this.localisation = localisation;
    }

    public Equipement localisation(Localisation localisation) {
        this.setLocalisation(localisation);
        return this;
    }

    public Set<Collaborateur> getCollaborateurs() {
        return this.collaborateurs;
    }

    public void setCollaborateurs(Set<Collaborateur> collaborateurs) {
        if (this.collaborateurs != null) {
            this.collaborateurs.forEach(i -> i.removeEquipements(this));
        }
        if (collaborateurs != null) {
            collaborateurs.forEach(i -> i.addEquipements(this));
        }
        this.collaborateurs = collaborateurs;
    }

    public Equipement collaborateurs(Set<Collaborateur> collaborateurs) {
        this.setCollaborateurs(collaborateurs);
        return this;
    }

    public Equipement addCollaborateur(Collaborateur collaborateur) {
        this.collaborateurs.add(collaborateur);
        collaborateur.getEquipements().add(this);
        return this;
    }

    public Equipement removeCollaborateur(Collaborateur collaborateur) {
        this.collaborateurs.remove(collaborateur);
        collaborateur.getEquipements().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Equipement)) {
            return false;
        }
        return id != null && id.equals(((Equipement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Equipement{" +
            "id=" + getId() +
            ", adressePhysique='" + getAdressePhysique() + "'" +
            ", equipementType='" + getEquipementType() + "'" +
            ", ip='" + getIp() + "'" +
            ", biosVersion='" + getBiosVersion() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            ", asset='" + getAsset() + "'" +
            ", numeroSerie='" + getNumeroSerie() + "'" +
            ", modele='" + getModele() + "'" +
            ", generation='" + getGeneration() + "'" +
            ", marque='" + getMarque() + "'" +
            ", fonctionnel='" + getFonctionnel() + "'" +
            ", cleAntiVolAdmin='" + getCleAntiVolAdmin() + "'" +
            ", cleAntiVolCollaborateur='" + getCleAntiVolCollaborateur() + "'" +
            "}";
    }
}
