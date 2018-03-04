package com.wearplus.service;

import com.wearplus.service.dto.DetailShoppingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing DetailShopping.
 */
public interface DetailShoppingService {

    /**
     * Save a detailShopping.
     *
     * @param detailShoppingDTO the entity to save
     * @return the persisted entity
     */
    DetailShoppingDTO save(DetailShoppingDTO detailShoppingDTO);

    /**
     * Get all the detailShoppings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DetailShoppingDTO> findAll(Pageable pageable);

    /**
     * Get the "id" detailShopping.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DetailShoppingDTO findOne(Long id);

    /**
     * Delete the "id" detailShopping.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
