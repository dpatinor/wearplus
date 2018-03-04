package com.wearplus.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DetailShopping.
 */
@Entity
@Table(name = "detail_shopping")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DetailShopping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "valor")
    private Float valor;

    @OneToOne
    @JoinColumn(unique = true)
    private Shopping shopping;

    @OneToOne
    @JoinColumn(unique = true)
    private Clothes clothes;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public DetailShopping quantity(Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Float getValor() {
        return valor;
    }

    public DetailShopping valor(Float valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Shopping getShopping() {
        return shopping;
    }

    public DetailShopping shopping(Shopping shopping) {
        this.shopping = shopping;
        return this;
    }

    public void setShopping(Shopping shopping) {
        this.shopping = shopping;
    }

    public Clothes getClothes() {
        return clothes;
    }

    public DetailShopping clothes(Clothes clothes) {
        this.clothes = clothes;
        return this;
    }

    public void setClothes(Clothes clothes) {
        this.clothes = clothes;
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
        DetailShopping detailShopping = (DetailShopping) o;
        if (detailShopping.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), detailShopping.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DetailShopping{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", valor=" + getValor() +
            "}";
    }
}
