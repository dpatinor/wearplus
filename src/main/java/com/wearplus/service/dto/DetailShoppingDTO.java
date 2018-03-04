package com.wearplus.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the DetailShopping entity.
 */
public class DetailShoppingDTO implements Serializable {

    private Long id;

    private Long quantity;

    private Float valor;

    private Long shoppingId;

    private Long clothesId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Long getShoppingId() {
        return shoppingId;
    }

    public void setShoppingId(Long shoppingId) {
        this.shoppingId = shoppingId;
    }

    public Long getClothesId() {
        return clothesId;
    }

    public void setClothesId(Long clothesId) {
        this.clothesId = clothesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DetailShoppingDTO detailShoppingDTO = (DetailShoppingDTO) o;
        if(detailShoppingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), detailShoppingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DetailShoppingDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", valor=" + getValor() +
            "}";
    }
}
