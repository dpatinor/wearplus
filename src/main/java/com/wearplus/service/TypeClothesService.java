package com.wearplus.service;

import com.wearplus.service.dto.TypeClothesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing TypeClothes.
 */
public interface TypeClothesService {

    /**
     * Save a typeClothes.
     *
     * @param typeClothesDTO the entity to save
     * @return the persisted entity
     */
    TypeClothesDTO save(TypeClothesDTO typeClothesDTO);

    /**
     * Get all the typeClothes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TypeClothesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" typeClothes.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TypeClothesDTO findOne(Long id);

    /**
     * Delete the "id" typeClothes.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
