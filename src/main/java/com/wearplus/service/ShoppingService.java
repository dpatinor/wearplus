package com.wearplus.service;

import com.wearplus.service.dto.ShoppingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Shopping.
 */
public interface ShoppingService {

    /**
     * Save a shopping.
     *
     * @param shoppingDTO the entity to save
     * @return the persisted entity
     */
    ShoppingDTO save(ShoppingDTO shoppingDTO);

    /**
     * Get all the shoppings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ShoppingDTO> findAll(Pageable pageable);

    /**
     * Get the "id" shopping.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ShoppingDTO findOne(Long id);

    /**
     * Delete the "id" shopping.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
