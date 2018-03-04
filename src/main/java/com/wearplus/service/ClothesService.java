package com.wearplus.service;

import com.wearplus.service.dto.ClothesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Clothes.
 */
public interface ClothesService {

    /**
     * Save a clothes.
     *
     * @param clothesDTO the entity to save
     * @return the persisted entity
     */
    ClothesDTO save(ClothesDTO clothesDTO);

    /**
     * Get all the clothes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ClothesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" clothes.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ClothesDTO findOne(Long id);

    /**
     * Delete the "id" clothes.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
