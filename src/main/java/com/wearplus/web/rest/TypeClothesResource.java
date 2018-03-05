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
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TypeClothes.
 */
@Component
@Path("/type-clothes")
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
    @GET
    @Produces("application/json")
    public Response getAllTypeClothes(Pageable pageable) {
        log.debug("REST request to get a page of TypeClothes");
        Page<TypeClothesDTO> page = typeClothesService.findAll(pageable);
        String link = PaginationUtil.generatePaginationLink(page, "/api/type-clothes");
        return Response.ok(page.getContent())
            .header("X-Total-Count", Long.toString(page.getTotalElements()))
            .header(HttpHeaders.LINK, link)
            .build();
    }

}
