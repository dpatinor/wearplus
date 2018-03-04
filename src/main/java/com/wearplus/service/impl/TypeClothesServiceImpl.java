package com.wearplus.service.impl;

import com.wearplus.service.TypeClothesService;
import com.wearplus.domain.TypeClothes;
import com.wearplus.repository.TypeClothesRepository;
import com.wearplus.service.dto.TypeClothesDTO;
import com.wearplus.service.mapper.TypeClothesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TypeClothes.
 */
@Service
@Transactional
public class TypeClothesServiceImpl implements TypeClothesService {

    private final Logger log = LoggerFactory.getLogger(TypeClothesServiceImpl.class);

    private final TypeClothesRepository typeClothesRepository;

    private final TypeClothesMapper typeClothesMapper;

    public TypeClothesServiceImpl(TypeClothesRepository typeClothesRepository, TypeClothesMapper typeClothesMapper) {
        this.typeClothesRepository = typeClothesRepository;
        this.typeClothesMapper = typeClothesMapper;
    }

    /**
     * Save a typeClothes.
     *
     * @param typeClothesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TypeClothesDTO save(TypeClothesDTO typeClothesDTO) {
        log.debug("Request to save TypeClothes : {}", typeClothesDTO);
        TypeClothes typeClothes = typeClothesMapper.toEntity(typeClothesDTO);
        typeClothes = typeClothesRepository.save(typeClothes);
        return typeClothesMapper.toDto(typeClothes);
    }

    /**
     * Get all the typeClothes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeClothesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeClothes");
        return typeClothesRepository.findAll(pageable)
            .map(typeClothesMapper::toDto);
    }

    /**
     * Get one typeClothes by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TypeClothesDTO findOne(Long id) {
        log.debug("Request to get TypeClothes : {}", id);
        TypeClothes typeClothes = typeClothesRepository.findOne(id);
        return typeClothesMapper.toDto(typeClothes);
    }

    /**
     * Delete the typeClothes by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeClothes : {}", id);
        typeClothesRepository.delete(id);
    }
}
