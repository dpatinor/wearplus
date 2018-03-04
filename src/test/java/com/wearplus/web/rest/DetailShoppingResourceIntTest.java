package com.wearplus.web.rest;

import com.wearplus.WearplusApp;

import com.wearplus.domain.DetailShopping;
import com.wearplus.repository.DetailShoppingRepository;
import com.wearplus.service.DetailShoppingService;
import com.wearplus.service.dto.DetailShoppingDTO;
import com.wearplus.service.mapper.DetailShoppingMapper;
import com.wearplus.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.wearplus.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DetailShoppingResource REST controller.
 *
 * @see DetailShoppingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WearplusApp.class)
public class DetailShoppingResourceIntTest {

    private static final Long DEFAULT_QUANTITY = 1L;
    private static final Long UPDATED_QUANTITY = 2L;

    private static final Float DEFAULT_VALOR = 1F;
    private static final Float UPDATED_VALOR = 2F;

    @Autowired
    private DetailShoppingRepository detailShoppingRepository;

    @Autowired
    private DetailShoppingMapper detailShoppingMapper;

    @Autowired
    private DetailShoppingService detailShoppingService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDetailShoppingMockMvc;

    private DetailShopping detailShopping;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DetailShoppingResource detailShoppingResource = new DetailShoppingResource(detailShoppingService);
        this.restDetailShoppingMockMvc = MockMvcBuilders.standaloneSetup(detailShoppingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetailShopping createEntity(EntityManager em) {
        DetailShopping detailShopping = new DetailShopping()
            .quantity(DEFAULT_QUANTITY)
            .valor(DEFAULT_VALOR);
        return detailShopping;
    }

    @Before
    public void initTest() {
        detailShopping = createEntity(em);
    }

    @Test
    @Transactional
    public void createDetailShopping() throws Exception {
        int databaseSizeBeforeCreate = detailShoppingRepository.findAll().size();

        // Create the DetailShopping
        DetailShoppingDTO detailShoppingDTO = detailShoppingMapper.toDto(detailShopping);
        restDetailShoppingMockMvc.perform(post("/api/detail-shoppings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailShoppingDTO)))
            .andExpect(status().isCreated());

        // Validate the DetailShopping in the database
        List<DetailShopping> detailShoppingList = detailShoppingRepository.findAll();
        assertThat(detailShoppingList).hasSize(databaseSizeBeforeCreate + 1);
        DetailShopping testDetailShopping = detailShoppingList.get(detailShoppingList.size() - 1);
        assertThat(testDetailShopping.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testDetailShopping.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createDetailShoppingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = detailShoppingRepository.findAll().size();

        // Create the DetailShopping with an existing ID
        detailShopping.setId(1L);
        DetailShoppingDTO detailShoppingDTO = detailShoppingMapper.toDto(detailShopping);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetailShoppingMockMvc.perform(post("/api/detail-shoppings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailShoppingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DetailShopping in the database
        List<DetailShopping> detailShoppingList = detailShoppingRepository.findAll();
        assertThat(detailShoppingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDetailShoppings() throws Exception {
        // Initialize the database
        detailShoppingRepository.saveAndFlush(detailShopping);

        // Get all the detailShoppingList
        restDetailShoppingMockMvc.perform(get("/api/detail-shoppings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detailShopping.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())));
    }

    @Test
    @Transactional
    public void getDetailShopping() throws Exception {
        // Initialize the database
        detailShoppingRepository.saveAndFlush(detailShopping);

        // Get the detailShopping
        restDetailShoppingMockMvc.perform(get("/api/detail-shoppings/{id}", detailShopping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(detailShopping.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDetailShopping() throws Exception {
        // Get the detailShopping
        restDetailShoppingMockMvc.perform(get("/api/detail-shoppings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDetailShopping() throws Exception {
        // Initialize the database
        detailShoppingRepository.saveAndFlush(detailShopping);
        int databaseSizeBeforeUpdate = detailShoppingRepository.findAll().size();

        // Update the detailShopping
        DetailShopping updatedDetailShopping = detailShoppingRepository.findOne(detailShopping.getId());
        // Disconnect from session so that the updates on updatedDetailShopping are not directly saved in db
        em.detach(updatedDetailShopping);
        updatedDetailShopping
            .quantity(UPDATED_QUANTITY)
            .valor(UPDATED_VALOR);
        DetailShoppingDTO detailShoppingDTO = detailShoppingMapper.toDto(updatedDetailShopping);

        restDetailShoppingMockMvc.perform(put("/api/detail-shoppings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailShoppingDTO)))
            .andExpect(status().isOk());

        // Validate the DetailShopping in the database
        List<DetailShopping> detailShoppingList = detailShoppingRepository.findAll();
        assertThat(detailShoppingList).hasSize(databaseSizeBeforeUpdate);
        DetailShopping testDetailShopping = detailShoppingList.get(detailShoppingList.size() - 1);
        assertThat(testDetailShopping.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testDetailShopping.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingDetailShopping() throws Exception {
        int databaseSizeBeforeUpdate = detailShoppingRepository.findAll().size();

        // Create the DetailShopping
        DetailShoppingDTO detailShoppingDTO = detailShoppingMapper.toDto(detailShopping);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDetailShoppingMockMvc.perform(put("/api/detail-shoppings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailShoppingDTO)))
            .andExpect(status().isCreated());

        // Validate the DetailShopping in the database
        List<DetailShopping> detailShoppingList = detailShoppingRepository.findAll();
        assertThat(detailShoppingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDetailShopping() throws Exception {
        // Initialize the database
        detailShoppingRepository.saveAndFlush(detailShopping);
        int databaseSizeBeforeDelete = detailShoppingRepository.findAll().size();

        // Get the detailShopping
        restDetailShoppingMockMvc.perform(delete("/api/detail-shoppings/{id}", detailShopping.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DetailShopping> detailShoppingList = detailShoppingRepository.findAll();
        assertThat(detailShoppingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetailShopping.class);
        DetailShopping detailShopping1 = new DetailShopping();
        detailShopping1.setId(1L);
        DetailShopping detailShopping2 = new DetailShopping();
        detailShopping2.setId(detailShopping1.getId());
        assertThat(detailShopping1).isEqualTo(detailShopping2);
        detailShopping2.setId(2L);
        assertThat(detailShopping1).isNotEqualTo(detailShopping2);
        detailShopping1.setId(null);
        assertThat(detailShopping1).isNotEqualTo(detailShopping2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetailShoppingDTO.class);
        DetailShoppingDTO detailShoppingDTO1 = new DetailShoppingDTO();
        detailShoppingDTO1.setId(1L);
        DetailShoppingDTO detailShoppingDTO2 = new DetailShoppingDTO();
        assertThat(detailShoppingDTO1).isNotEqualTo(detailShoppingDTO2);
        detailShoppingDTO2.setId(detailShoppingDTO1.getId());
        assertThat(detailShoppingDTO1).isEqualTo(detailShoppingDTO2);
        detailShoppingDTO2.setId(2L);
        assertThat(detailShoppingDTO1).isNotEqualTo(detailShoppingDTO2);
        detailShoppingDTO1.setId(null);
        assertThat(detailShoppingDTO1).isNotEqualTo(detailShoppingDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(detailShoppingMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(detailShoppingMapper.fromId(null)).isNull();
    }
}
