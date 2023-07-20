package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.SocieteCPDP;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Projet.
 */
@Document(collection = "projet")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Projet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("nom")
    private String nom;

    @Field("structure")
    private String structure;

    @Field("societe_cpdp")
    private SocieteCPDP societeCPDP;

    @DBRef
    @Field("cps")
    @JsonIgnoreProperties(value = { "projets", "equipements", "localisation", "cps", "dps" }, allowSetters = true)
    private Set<Collaborateur> cps = new HashSet<>();

    @DBRef
    @Field("dps")
    @JsonIgnoreProperties(value = { "projets", "equipements", "localisation", "cps", "dps" }, allowSetters = true)
    private Set<Collaborateur> dps = new HashSet<>();

    @DBRef
    @Field("collaborateurs")
    @JsonIgnoreProperties(value = { "projets", "equipements", "localisation", "cps", "dps" }, allowSetters = true)
    private Set<Collaborateur> collaborateurs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Projet id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Projet nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getStructure() {
        return this.structure;
    }

    public Projet structure(String structure) {
        this.setStructure(structure);
        return this;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public SocieteCPDP getSocieteCPDP() {
        return this.societeCPDP;
    }

    public Projet societeCPDP(SocieteCPDP societeCPDP) {
        this.setSocieteCPDP(societeCPDP);
        return this;
    }

    public void setSocieteCPDP(SocieteCPDP societeCPDP) {
        this.societeCPDP = societeCPDP;
    }

    public Set<Collaborateur> getCps() {
        return this.cps;
    }

    public void setCps(Set<Collaborateur> collaborateurs) {
        this.cps = collaborateurs;
    }

    public Projet cps(Set<Collaborateur> collaborateurs) {
        this.setCps(collaborateurs);
        return this;
    }

    public Projet addCp(Collaborateur collaborateur) {
        this.cps.add(collaborateur);
        collaborateur.getCps().add(this);
        return this;
    }

    public Projet removeCp(Collaborateur collaborateur) {
        this.cps.remove(collaborateur);
        collaborateur.getCps().remove(this);
        return this;
    }

    public Set<Collaborateur> getDps() {
        return this.dps;
    }

    public void setDps(Set<Collaborateur> collaborateurs) {
        this.dps = collaborateurs;
    }

    public Projet dps(Set<Collaborateur> collaborateurs) {
        this.setDps(collaborateurs);
        return this;
    }

    public Projet addDp(Collaborateur collaborateur) {
        this.dps.add(collaborateur);
        collaborateur.getDps().add(this);
        return this;
    }

    public Projet removeDp(Collaborateur collaborateur) {
        this.dps.remove(collaborateur);
        collaborateur.getDps().remove(this);
        return this;
    }

    public Set<Collaborateur> getCollaborateurs() {
        return this.collaborateurs;
    }

    public void setCollaborateurs(Set<Collaborateur> collaborateurs) {
        if (this.collaborateurs != null) {
            this.collaborateurs.forEach(i -> i.removeProjet(this));
        }
        if (collaborateurs != null) {
            collaborateurs.forEach(i -> i.addProjet(this));
        }
        this.collaborateurs = collaborateurs;
    }

    public Projet collaborateurs(Set<Collaborateur> collaborateurs) {
        this.setCollaborateurs(collaborateurs);
        return this;
    }

    public Projet addCollaborateur(Collaborateur collaborateur) {
        this.collaborateurs.add(collaborateur);
        collaborateur.getProjets().add(this);
        return this;
    }

    public Projet removeCollaborateur(Collaborateur collaborateur) {
        this.collaborateurs.remove(collaborateur);
        collaborateur.getProjets().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Projet)) {
            return false;
        }
        return id != null && id.equals(((Projet) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Projet{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", structure='" + getStructure() + "'" +
            ", societeCPDP='" + getSocieteCPDP() + "'" +
            "}";
    }
}
