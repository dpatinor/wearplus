package com.wearplus.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wearplus.service.ClothesService;
import com.wearplus.web.rest.errors.BadRequestAlertException;
import com.wearplus.web.rest.util.HeaderUtil;
import com.wearplus.web.rest.util.PaginationUtil;
import com.wearplus.service.dto.ClothesDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Clothes.
 */
@RestController
@RequestMapping("/api")
public class ClothesResource {

    private final Logger log = LoggerFactory.getLogger(ClothesResource.class);

    private static final String ENTITY_NAME = "clothes";

    private final ClothesService clothesService;

    public ClothesResource(ClothesService clothesService) {
        this.clothesService = clothesService;
    }

    /**
     * POST  /clothes : Create a new clothes.
     *
     * @param clothesDTO the clothesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clothesDTO, or with status 400 (Bad Request) if the clothes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/clothes")
    @Timed
    public ResponseEntity<ClothesDTO> createClothes(@RequestBody ClothesDTO clothesDTO) throws URISyntaxException {
        log.debug("REST request to save Clothes : {}", clothesDTO);
        if (clothesDTO.getId() != null) {
            throw new BadRequestAlertException("A new clothes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClothesDTO result = clothesService.save(clothesDTO);
        return ResponseEntity.created(new URI("/api/clothes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clothes : Updates an existing clothes.
     *
     * @param clothesDTO the clothesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clothesDTO,
     * or with status 400 (Bad Request) if the clothesDTO is not valid,
     * or with status 500 (Internal Server Error) if the clothesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/clothes")
    @Timed
    public ResponseEntity<ClothesDTO> updateClothes(@RequestBody ClothesDTO clothesDTO) throws URISyntaxException {
        log.debug("REST request to update Clothes : {}", clothesDTO);
        if (clothesDTO.getId() == null) {
            return createClothes(clothesDTO);
        }
        ClothesDTO result = clothesService.save(clothesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clothesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clothes : get all the clothes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of clothes in body
     */
    @GetMapping("/clothes")
    @Timed
    public ResponseEntity<List<ClothesDTO>> getAllClothes(Pageable pageable) {
        log.debug("REST request to get a page of Clothes");
        Page<ClothesDTO> page = clothesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/clothes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /clothes/:id : get the "id" clothes.
     *
     * @param id the id of the clothesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clothesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/clothes/{id}")
    @Timed
    public ResponseEntity<ClothesDTO> getClothes(@PathVariable Long id) {
        log.debug("REST request to get Clothes : {}", id);
        ClothesDTO clothesDTO = clothesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(clothesDTO));
    }

    /**
     * DELETE  /clothes/:id : delete the "id" clothes.
     *
     * @param id the id of the clothesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clothes/{id}")
    @Timed
    public ResponseEntity<Void> deleteClothes(@PathVariable Long id) {
        log.debug("REST request to delete Clothes : {}", id);
        clothesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
