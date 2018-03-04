package com.wearplus.service.mapper;

import com.wearplus.domain.*;
import com.wearplus.service.dto.DetailShoppingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DetailShopping and its DTO DetailShoppingDTO.
 */
@Mapper(componentModel = "spring", uses = {ShoppingMapper.class, ClothesMapper.class})
public interface DetailShoppingMapper extends EntityMapper<DetailShoppingDTO, DetailShopping> {

    @Mapping(source = "shopping.id", target = "shoppingId")
    @Mapping(source = "clothes.id", target = "clothesId")
    DetailShoppingDTO toDto(DetailShopping detailShopping);

    @Mapping(source = "shoppingId", target = "shopping")
    @Mapping(source = "clothesId", target = "clothes")
    DetailShopping toEntity(DetailShoppingDTO detailShoppingDTO);

    default DetailShopping fromId(Long id) {
        if (id == null) {
            return null;
        }
        DetailShopping detailShopping = new DetailShopping();
        detailShopping.setId(id);
        return detailShopping;
    }
}
