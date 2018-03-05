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

}
