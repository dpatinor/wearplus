package com.wearplus.service.mapper;

import com.wearplus.domain.*;
import com.wearplus.service.dto.TypeClothesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TypeClothes and its DTO TypeClothesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypeClothesMapper extends EntityMapper<TypeClothesDTO, TypeClothes> {



    default TypeClothes fromId(Long id) {
        if (id == null) {
            return null;
        }
        TypeClothes typeClothes = new TypeClothes();
        typeClothes.setId(id);
        return typeClothes;
    }
}
