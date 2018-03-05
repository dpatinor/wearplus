package com.wearplus.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wearplus.domain.Clothes;
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
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Clothes.
 */
@Component
@Path("/clothes")
public class ClothesResource {

    private final Logger log = LoggerFactory.getLogger(ClothesResource.class);

    private static final String ENTITY_NAME = "clothes";

    private final ClothesService clothesService;

    public ClothesResource(ClothesService clothesService) {
        this.clothesService = clothesService;
    }

    /**
     * GET  /clothes : get all the clothes.
     *
     * @param typeClothesId
     * @return the ResponseEntity with status 200 (OK) and the list of clothes in body
     */
    @GET
    @Produces("application/json")
    public Response findAllByTypeClothesId(@QueryParam("typeClothesId") Long typeClothesId) {
        log.debug("REST request to get a page of Clothes");
        List<Clothes> list = clothesService.findAllByTypeClothesId(typeClothesId);
        return Response.ok(list)
            .build();
    }

 }
