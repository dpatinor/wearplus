package com.wearplus.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wearplus.service.ShoppingService;
import com.wearplus.web.rest.errors.BadRequestAlertException;
import com.wearplus.web.rest.util.HeaderUtil;
import com.wearplus.web.rest.util.PaginationUtil;
import com.wearplus.service.dto.ShoppingDTO;
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
 * REST controller for managing Shopping.
 */
@RestController
@RequestMapping("/api")
public class ShoppingResource {

    private final Logger log = LoggerFactory.getLogger(ShoppingResource.class);

    private static final String ENTITY_NAME = "shopping";

    private final ShoppingService shoppingService;

    public ShoppingResource(ShoppingService shoppingService) {
        this.shoppingService = shoppingService;
    }

    /**
     * POST  /shoppings : Create a new shopping.
     *
     * @param shoppingDTO the shoppingDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shoppingDTO, or with status 400 (Bad Request) if the shopping has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shoppings")
    @Timed
    public ResponseEntity<ShoppingDTO> createShopping(@RequestBody ShoppingDTO shoppingDTO) throws URISyntaxException {
        log.debug("REST request to save Shopping : {}", shoppingDTO);
        if (shoppingDTO.getId() != null) {
            throw new BadRequestAlertException("A new shopping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShoppingDTO result = shoppingService.save(shoppingDTO);
        return ResponseEntity.created(new URI("/api/shoppings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shoppings : Updates an existing shopping.
     *
     * @param shoppingDTO the shoppingDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shoppingDTO,
     * or with status 400 (Bad Request) if the shoppingDTO is not valid,
     * or with status 500 (Internal Server Error) if the shoppingDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shoppings")
    @Timed
    public ResponseEntity<ShoppingDTO> updateShopping(@RequestBody ShoppingDTO shoppingDTO) throws URISyntaxException {
        log.debug("REST request to update Shopping : {}", shoppingDTO);
        if (shoppingDTO.getId() == null) {
            return createShopping(shoppingDTO);
        }
        ShoppingDTO result = shoppingService.save(shoppingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shoppingDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shoppings : get all the shoppings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of shoppings in body
     */
    @GetMapping("/shoppings")
    @Timed
    public ResponseEntity<List<ShoppingDTO>> getAllShoppings(Pageable pageable) {
        log.debug("REST request to get a page of Shoppings");
        Page<ShoppingDTO> page = shoppingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/shoppings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /shoppings/:id : get the "id" shopping.
     *
     * @param id the id of the shoppingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shoppingDTO, or with status 404 (Not Found)
     */
    @GetMapping("/shoppings/{id}")
    @Timed
    public ResponseEntity<ShoppingDTO> getShopping(@PathVariable Long id) {
        log.debug("REST request to get Shopping : {}", id);
        ShoppingDTO shoppingDTO = shoppingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(shoppingDTO));
    }

    /**
     * DELETE  /shoppings/:id : delete the "id" shopping.
     *
     * @param id the id of the shoppingDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shoppings/{id}")
    @Timed
    public ResponseEntity<Void> deleteShopping(@PathVariable Long id) {
        log.debug("REST request to delete Shopping : {}", id);
        shoppingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
