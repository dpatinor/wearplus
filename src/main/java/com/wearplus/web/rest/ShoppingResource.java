package com.wearplus.web.rest;

import com.wearplus.service.ShoppingService;
import com.wearplus.web.rest.errors.BadRequestAlertException;
import com.wearplus.web.rest.util.PaginationUtil;
import com.wearplus.service.dto.ShoppingDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing Shopping.
 */
@Component
@Path("/shoppings")
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
     * @return the Response with status 201 (Created) and with body the new shoppingDTO, or with status 400 (Bad Request) if the shopping has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @POST
    @Produces("application/json")
    public Response createShopping(@RequestBody ShoppingDTO shoppingDTO) throws URISyntaxException {
        log.debug("REST request to save Shopping : {}", shoppingDTO);
        if (shoppingDTO.getId() != null) {
            throw new BadRequestAlertException("A new shopping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShoppingDTO result = shoppingService.save(shoppingDTO);
        return Response.created(new URI("/api/shoppings/" + result.getId()))
            .header("X-wearplusApp-alert", "A new " + ENTITY_NAME + " is created with identifier " + result.getId().toString())
            .header("X-wearplusApp-params", result.getId().toString())
            .entity(result)
            .build();
    }

    /**
     * PUT  /shoppings : Updates an existing shopping.
     *
     * @param shoppingDTO the shoppingDTO to update
     * @return the Response with status 200 (OK) and with body the updated shoppingDTO,
     * or with status 400 (Bad Request) if the shoppingDTO is not valid,
     * or with status 500 (Internal Server Error) if the shoppingDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PUT
    @Produces("application/json")
    public Response updateShopping(@RequestBody ShoppingDTO shoppingDTO) throws URISyntaxException {
        log.debug("REST request to update Shopping : {}", shoppingDTO);
        if (shoppingDTO.getId() == null) {
            return createShopping(shoppingDTO);
        }
        ShoppingDTO result = shoppingService.save(shoppingDTO);
        return Response.ok(result)
            .header("X-wearplusApp-alert","A " + ENTITY_NAME + " is updated with identifier" + result.getId().toString())
            .header("X-wearplusApp-params", shoppingDTO.getId().toString())
            .build();
    }

    /**
     * GET  /shoppings : get all the shoppings.
     *
     * @param pageable the pagination information
     * @return the Response with status 200 (OK) and the list of shoppings in body
     */
    @GET
    @Produces("application/json")
    public Response getAllShoppings(Pageable pageable) {
        log.debug("REST request to get a page of Shoppings");
        Page<ShoppingDTO> page = shoppingService.findAll(pageable);
        String link = PaginationUtil.generatePaginationLink(page, "/api/shoppings");
        return Response.ok(page.getContent())
            .header("X-Total-Count", Long.toString(page.getTotalElements()))
            .header(HttpHeaders.LINK, link)
            .build();
    }

    /**
     * GET  /shoppings/:id : get the "id" shopping.
     *
     * @param id the id of the shoppingDTO to retrieve
     * @return the Response with status 200 (OK) and with body the shoppingDTO, or with status 404 (Not Found)
     */
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getShopping(@PathParam("id") Long id) {
        log.debug("REST request to get Shopping : {}", id);
        ShoppingDTO shoppingDTO = shoppingService.findOne(id);
        return Response.ok(shoppingDTO).build();
    }

    /**
     * DELETE  /shoppings/:id : delete the "id" shopping.
     *
     * @param id the id of the shoppingDTO to delete
     * @return the Response with status 200 (OK)
     */
    @DELETE
    @Path("/{id}")
    @Produces("application/json")
    public Response deleteShopping(@PathParam("id") Long id) {
        log.debug("REST request to delete Shopping : {}", id);
        shoppingService.delete(id);
        return Response.ok()
            .header("X-wearplusApp-alert", "A " + ENTITY_NAME + " is deleted with identifier " + id.toString())
            .header("X-wearplusApp-params", id.toString())
            .build();
    }
}
