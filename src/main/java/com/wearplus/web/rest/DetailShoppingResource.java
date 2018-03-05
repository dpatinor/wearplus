package com.wearplus.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wearplus.service.DetailShoppingService;
import com.wearplus.web.rest.errors.BadRequestAlertException;
import com.wearplus.web.rest.util.HeaderUtil;
import com.wearplus.web.rest.util.PaginationUtil;
import com.wearplus.service.dto.DetailShoppingDTO;
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
 * REST controller for managing DetailShopping.
 */
@RestController
@RequestMapping("/api")
public class DetailShoppingResource {

    private final Logger log = LoggerFactory.getLogger(DetailShoppingResource.class);

    private static final String ENTITY_NAME = "detailShopping";

    private final DetailShoppingService detailShoppingService;

    public DetailShoppingResource(DetailShoppingService detailShoppingService) {
        this.detailShoppingService = detailShoppingService;
    }

    /**
     * GET  /detail-shoppings : get all the detailShoppings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of detailShoppings in body
     */
    @GetMapping("/detail-shoppings")
    @Timed
    public ResponseEntity<List<DetailShoppingDTO>> getAllDetailShoppings(Pageable pageable) {
        log.debug("REST request to get a page of DetailShoppings");
        Page<DetailShoppingDTO> page = detailShoppingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/detail-shoppings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /detail-shoppings/:id : get the "id" detailShopping.
     *
     * @param id the id of the detailShoppingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the detailShoppingDTO, or with status 404 (Not Found)
     */
    @GetMapping("/detail-shoppings/{id}")
    @Timed
    public ResponseEntity<DetailShoppingDTO> getDetailShopping(@PathVariable Long id) {
        log.debug("REST request to get DetailShopping : {}", id);
        DetailShoppingDTO detailShoppingDTO = detailShoppingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(detailShoppingDTO));
    }

}
