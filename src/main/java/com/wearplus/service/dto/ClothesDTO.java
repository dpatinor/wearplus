package com.wearplus.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Clothes entity.
 */
public class ClothesDTO implements Serializable {

    private Long id;

    private String name;

    private Long quantity;

    private Float value;

    private String image;

    private Long typeClothesId;

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

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getTypeClothesId() {
        return typeClothesId;
    }

    public void setTypeClothesId(Long typeClothesId) {
        this.typeClothesId = typeClothesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClothesDTO clothesDTO = (ClothesDTO) o;
        if(clothesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clothesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClothesDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", quantity=" + getQuantity() +
            ", value=" + getValue() +
            ", image='" + getImage() + "'" +
            "}";
    }
}
