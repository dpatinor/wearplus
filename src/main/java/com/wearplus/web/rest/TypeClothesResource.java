package com.wearplus.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wearplus.service.TypeClothesService;
import com.wearplus.web.rest.errors.BadRequestAlertException;
import com.wearplus.web.rest.util.HeaderUtil;
import com.wearplus.web.rest.util.PaginationUtil;
import com.wearplus.service.dto.TypeClothesDTO;
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
 * REST controller for managing TypeClothes.
 */
@RestController
@RequestMapping("/api")
public class TypeClothesResource {

    private final Logger log = LoggerFactory.getLogger(TypeClothesResource.class);

    private static final String ENTITY_NAME = "typeClothes";

    private final TypeClothesService typeClothesService;

    public TypeClothesResource(TypeClothesService typeClothesService) {
        this.typeClothesService = typeClothesService;
    }

    /**
     * POST  /type-clothes : Create a new typeClothes.
     *
     * @param typeClothesDTO the typeClothesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeClothesDTO, or with status 400 (Bad Request) if the typeClothes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-clothes")
    @Timed
    public ResponseEntity<TypeClothesDTO> createTypeClothes(@RequestBody TypeClothesDTO typeClothesDTO) throws URISyntaxException {
        log.debug("REST request to save TypeClothes : {}", typeClothesDTO);
        if (typeClothesDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeClothes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeClothesDTO result = typeClothesService.save(typeClothesDTO);
        return ResponseEntity.created(new URI("/api/type-clothes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-clothes : Updates an existing typeClothes.
     *
     * @param typeClothesDTO the typeClothesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeClothesDTO,
     * or with status 400 (Bad Request) if the typeClothesDTO is not valid,
     * or with status 500 (Internal Server Error) if the typeClothesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-clothes")
    @Timed
    public ResponseEntity<TypeClothesDTO> updateTypeClothes(@RequestBody TypeClothesDTO typeClothesDTO) throws URISyntaxException {
        log.debug("REST request to update TypeClothes : {}", typeClothesDTO);
        if (typeClothesDTO.getId() == null) {
            return createTypeClothes(typeClothesDTO);
        }
        TypeClothesDTO result = typeClothesService.save(typeClothesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeClothesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-clothes : get all the typeClothes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of typeClothes in body
     */
    @GetMapping("/type-clothes")
    @Timed
    public ResponseEntity<List<TypeClothesDTO>> getAllTypeClothes(Pageable pageable) {
        log.debug("REST request to get a page of TypeClothes");
        Page<TypeClothesDTO> page = typeClothesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/type-clothes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /type-clothes/:id : get the "id" typeClothes.
     *
     * @param id the id of the typeClothesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeClothesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/type-clothes/{id}")
    @Timed
    public ResponseEntity<TypeClothesDTO> getTypeClothes(@PathVariable Long id) {
        log.debug("REST request to get TypeClothes : {}", id);
        TypeClothesDTO typeClothesDTO = typeClothesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(typeClothesDTO));
    }

    /**
     * DELETE  /type-clothes/:id : delete the "id" typeClothes.
     *
     * @param id the id of the typeClothesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-clothes/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeClothes(@PathVariable Long id) {
        log.debug("REST request to delete TypeClothes : {}", id);
        typeClothesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
