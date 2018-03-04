package com.wearplus.service.impl;

import com.wearplus.service.ClothesService;
import com.wearplus.domain.Clothes;
import com.wearplus.repository.ClothesRepository;
import com.wearplus.service.dto.ClothesDTO;
import com.wearplus.service.mapper.ClothesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Clothes.
 */
@Service
@Transactional
public class ClothesServiceImpl implements ClothesService {

    private final Logger log = LoggerFactory.getLogger(ClothesServiceImpl.class);

    private final ClothesRepository clothesRepository;

    private final ClothesMapper clothesMapper;

    public ClothesServiceImpl(ClothesRepository clothesRepository, ClothesMapper clothesMapper) {
        this.clothesRepository = clothesRepository;
        this.clothesMapper = clothesMapper;
    }

    /**
     * Save a clothes.
     *
     * @param clothesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ClothesDTO save(ClothesDTO clothesDTO) {
        log.debug("Request to save Clothes : {}", clothesDTO);
        Clothes clothes = clothesMapper.toEntity(clothesDTO);
        clothes = clothesRepository.save(clothes);
        return clothesMapper.toDto(clothes);
    }

    /**
     * Get all the clothes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClothesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Clothes");
        return clothesRepository.findAll(pageable)
            .map(clothesMapper::toDto);
    }

    /**
     * Get one clothes by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ClothesDTO findOne(Long id) {
        log.debug("Request to get Clothes : {}", id);
        Clothes clothes = clothesRepository.findOne(id);
        return clothesMapper.toDto(clothes);
    }

    /**
     * Delete the clothes by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Clothes : {}", id);
        clothesRepository.delete(id);
    }
}
