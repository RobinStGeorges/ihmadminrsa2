package com.mycompany.myapp.domain;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A NumeroStation.
 */
@Document(collection = "numero_station")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NumeroStation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    // @Pattern(regexp = "[Z]{1}[0-9]{3}[-]{1}[0-9]{7}")
    @Field("numero_station")
    private String numeroStation;

    @DBRef
    @Field("equipement")
    private Equipement equipement;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public NumeroStation id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumeroStation() {
        return this.numeroStation;
    }

    public NumeroStation numeroStation(String numeroStation) {
        this.setNumeroStation(numeroStation);
        return this;
    }

    public void setNumeroStation(String numeroStation) {
        this.numeroStation = numeroStation;
    }

    public Equipement getEquipement() {
        return this.equipement;
    }

    public void setEquipement(Equipement equipement) {
        this.equipement = equipement;
    }

    public NumeroStation equipement(Equipement equipement) {
        this.setEquipement(equipement);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NumeroStation)) {
            return false;
        }
        return id != null && id.equals(((NumeroStation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NumeroStation{" +
            "id=" + getId() +
            ", numeroStation='" + getNumeroStation() + "'" +
            "}";
    }
}
