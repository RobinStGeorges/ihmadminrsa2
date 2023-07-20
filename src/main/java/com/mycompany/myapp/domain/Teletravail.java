package com.mycompany.myapp.domain;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Teletravail.
 */
@Document(collection = "teletravail")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Teletravail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    // @Pattern(regexp = "[0-9]{1,3}[.]{1}[0-9]{1,3}[.]{1}[0-9]{1,3}[.]{1}[0-9]{1,3}")
    @Field("ip_dgfip_fixe")
    private String ipDGFIPFixe;

    // @Pattern(regexp = "[0-9]{1,3}[.]{1}[0-9]{1,3}[.]{1}[0-9]{1,3}[.]{1}[0-9]{1,3}")
    @Field("ip_teletravail")
    private String ipTeletravail;

    // @Pattern(regexp = "[0-9]{1,3}[.]{1}[0-9]{1,3}[.]{1}[0-9]{1,3}[.]{1}[0-9]{1,3}")
    @Field("ip_ipsec")
    private String ipIPSEC;

    @Field("is_actif")
    private Boolean isActif;

    @DBRef
    @Field("equipement")
    private Equipement equipement;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Teletravail id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIpDGFIPFixe() {
        return this.ipDGFIPFixe;
    }

    public Teletravail ipDGFIPFixe(String ipDGFIPFixe) {
        this.setIpDGFIPFixe(ipDGFIPFixe);
        return this;
    }

    public void setIpDGFIPFixe(String ipDGFIPFixe) {
        this.ipDGFIPFixe = ipDGFIPFixe;
    }

    public String getIpTeletravail() {
        return this.ipTeletravail;
    }

    public Teletravail ipTeletravail(String ipTeletravail) {
        this.setIpTeletravail(ipTeletravail);
        return this;
    }

    public void setIpTeletravail(String ipTeletravail) {
        this.ipTeletravail = ipTeletravail;
    }

    public String getIpIPSEC() {
        return this.ipIPSEC;
    }

    public Teletravail ipIPSEC(String ipIPSEC) {
        this.setIpIPSEC(ipIPSEC);
        return this;
    }

    public void setIpIPSEC(String ipIPSEC) {
        this.ipIPSEC = ipIPSEC;
    }

    public Boolean getIsActif() {
        return this.isActif;
    }

    public Teletravail isActif(Boolean isActif) {
        this.setIsActif(isActif);
        return this;
    }

    public void setIsActif(Boolean isActif) {
        this.isActif = isActif;
    }

    public Equipement getEquipement() {
        return this.equipement;
    }

    public void setEquipement(Equipement equipement) {
        this.equipement = equipement;
    }

    public Teletravail equipement(Equipement equipement) {
        this.setEquipement(equipement);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Teletravail)) {
            return false;
        }
        return id != null && id.equals(((Teletravail) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Teletravail{" +
            "id=" + getId() +
            ", ipDGFIPFixe='" + getIpDGFIPFixe() + "'" +
            ", ipTeletravail='" + getIpTeletravail() + "'" +
            ", ipIPSEC='" + getIpIPSEC() + "'" +
            ", isActif='" + getIsActif() + "'" +
            "}";
    }
}
