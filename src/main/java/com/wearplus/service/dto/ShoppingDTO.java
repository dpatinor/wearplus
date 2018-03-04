package com.wearplus.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Shopping entity.
 */
public class ShoppingDTO implements Serializable {

    private Long id;

    private String name;

    private String idType;

    private Long idNumber;

    private Instant dateOrder;

    private Instant dateDelivery;

    private String address;

    private Float total;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public Long getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(Long idNumber) {
        this.idNumber = idNumber;
    }

    public Instant getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(Instant dateOrder) {
        this.dateOrder = dateOrder;
    }

    public Instant getDateDelivery() {
        return dateDelivery;
    }

    public void setDateDelivery(Instant dateDelivery) {
        this.dateDelivery = dateDelivery;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShoppingDTO shoppingDTO = (ShoppingDTO) o;
        if(shoppingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shoppingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShoppingDTO{" +
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
