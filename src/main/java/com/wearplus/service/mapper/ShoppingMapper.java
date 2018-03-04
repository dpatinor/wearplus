package com.wearplus.service.mapper;

import com.wearplus.domain.*;
import com.wearplus.service.dto.ShoppingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Shopping and its DTO ShoppingDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ShoppingMapper extends EntityMapper<ShoppingDTO, Shopping> {



    default Shopping fromId(Long id) {
        if (id == null) {
            return null;
        }
        Shopping shopping = new Shopping();
        shopping.setId(id);
        return shopping;
    }
}
