package com.wearplus.service.mapper;

import com.wearplus.domain.*;
import com.wearplus.service.dto.ClothesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Clothes and its DTO ClothesDTO.
 */
@Mapper(componentModel = "spring", uses = {TypeClothesMapper.class})
public interface ClothesMapper extends EntityMapper<ClothesDTO, Clothes> {

    @Mapping(source = "typeClothes.id", target = "typeClothesId")
    ClothesDTO toDto(Clothes clothes);

    @Mapping(source = "typeClothesId", target = "typeClothes")
    Clothes toEntity(ClothesDTO clothesDTO);

    default Clothes fromId(Long id) {
        if (id == null) {
            return null;
        }
        Clothes clothes = new Clothes();
        clothes.setId(id);
        return clothes;
    }
}
