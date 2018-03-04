package com.wearplus.service.impl;

import com.wearplus.service.DetailShoppingService;
import com.wearplus.domain.DetailShopping;
import com.wearplus.repository.DetailShoppingRepository;
import com.wearplus.service.dto.DetailShoppingDTO;
import com.wearplus.service.mapper.DetailShoppingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing DetailShopping.
 */
@Service
@Transactional
public class DetailShoppingServiceImpl implements DetailShoppingService {

    private final Logger log = LoggerFactory.getLogger(DetailShoppingServiceImpl.class);

    private final DetailShoppingRepository detailShoppingRepository;

    private final DetailShoppingMapper detailShoppingMapper;

    public DetailShoppingServiceImpl(DetailShoppingRepository detailShoppingRepository, DetailShoppingMapper detailShoppingMapper) {
        this.detailShoppingRepository = detailShoppingRepository;
        this.detailShoppingMapper = detailShoppingMapper;
    }

    /**
     * Save a detailShopping.
     *
     * @param detailShoppingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DetailShoppingDTO save(DetailShoppingDTO detailShoppingDTO) {
        log.debug("Request to save DetailShopping : {}", detailShoppingDTO);
        DetailShopping detailShopping = detailShoppingMapper.toEntity(detailShoppingDTO);
        detailShopping = detailShoppingRepository.save(detailShopping);
        return detailShoppingMapper.toDto(detailShopping);
    }

    /**
     * Get all the detailShoppings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DetailShoppingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DetailShoppings");
        return detailShoppingRepository.findAll(pageable)
            .map(detailShoppingMapper::toDto);
    }

    /**
     * Get one detailShopping by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DetailShoppingDTO findOne(Long id) {
        log.debug("Request to get DetailShopping : {}", id);
        DetailShopping detailShopping = detailShoppingRepository.findOne(id);
        return detailShoppingMapper.toDto(detailShopping);
    }

    /**
     * Delete the detailShopping by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DetailShopping : {}", id);
        detailShoppingRepository.delete(id);
    }
}
