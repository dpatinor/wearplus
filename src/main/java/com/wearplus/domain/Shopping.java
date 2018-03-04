package com.wearplus.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Shopping.
 */
@Entity
@Table(name = "shopping")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Shopping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "id_type")
    private String idType;

    @Column(name = "id_number")
    private Long idNumber;

    @Column(name = "date_order")
    private Instant dateOrder;

    @Column(name = "date_delivery")
    private Instant dateDelivery;

    @Column(name = "address")
    private String address;

    @Column(name = "total")
    private Float total;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Shopping name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdType() {
        return idType;
    }

    public Shopping idType(String idType) {
        this.idType = idType;
        return this;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public Long getIdNumber() {
        return idNumber;
    }

    public Shopping idNumber(Long idNumber) {
        this.idNumber = idNumber;
        return this;
    }

    public void setIdNumber(Long idNumber) {
        this.idNumber = idNumber;
    }

    public Instant getDateOrder() {
        return dateOrder;
    }

    public Shopping dateOrder(Instant dateOrder) {
        this.dateOrder = dateOrder;
        return this;
    }

    public void setDateOrder(Instant dateOrder) {
        this.dateOrder = dateOrder;
    }

    public Instant getDateDelivery() {
        return dateDelivery;
    }

    public Shopping dateDelivery(Instant dateDelivery) {
        this.dateDelivery = dateDelivery;
        return this;
    }

    public void setDateDelivery(Instant dateDelivery) {
        this.dateDelivery = dateDelivery;
    }

    public String getAddress() {
        return address;
    }

    public Shopping address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getTotal() {
        return total;
    }

    public Shopping total(Float total) {
        this.total = total;
        return this;
    }

    public void setTotal(Float total) {
        this.total = total;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Shopping shopping = (Shopping) o;
        if (shopping.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shopping.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Shopping{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", idType='" + getIdType() + "'" +
            ", idNumber=" + getIdNumber() +
            ", dateOrder='" + getDateOrder() + "'" +
            ", dateDelivery='" + getDateDelivery() + "'" +
            ", address='" + getAddress() + "'" +
            ", total=" + getTotal() +
            "}";
    }
}
