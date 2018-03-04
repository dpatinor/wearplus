package com.wearplus.web.rest;

import com.wearplus.WearplusApp;

import com.wearplus.domain.Shopping;
import com.wearplus.repository.ShoppingRepository;
import com.wearplus.service.ShoppingService;
import com.wearplus.service.dto.ShoppingDTO;
import com.wearplus.service.mapper.ShoppingMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.wearplus.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ShoppingResource REST controller.
 *
 * @see ShoppingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WearplusApp.class)
public class ShoppingResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ID_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ID_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_ID_NUMBER = 1L;
    private static final Long UPDATED_ID_NUMBER = 2L;

    private static final Instant DEFAULT_DATE_ORDER = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_ORDER = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_DELIVERY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_DELIVERY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Float DEFAULT_TOTAL = 1F;
    private static final Float UPDATED_TOTAL = 2F;

    @Autowired
    private ShoppingRepository shoppingRepository;

    @Autowired
    private ShoppingMapper shoppingMapper;

    @Autowired
    private ShoppingService shoppingService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restShoppingMockMvc;

    private Shopping shopping;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShoppingResource shoppingResource = new ShoppingResource(shoppingService);
        this.restShoppingMockMvc = MockMvcBuilders.standaloneSetup(shoppingResource)
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
    public static Shopping createEntity(EntityManager em) {
        Shopping shopping = new Shopping()
            .name(DEFAULT_NAME)
            .idType(DEFAULT_ID_TYPE)
            .idNumber(DEFAULT_ID_NUMBER)
            .dateOrder(DEFAULT_DATE_ORDER)
            .dateDelivery(DEFAULT_DATE_DELIVERY)
            .address(DEFAULT_ADDRESS)
            .total(DEFAULT_TOTAL);
        return shopping;
    }

    @Before
    public void initTest() {
        shopping = createEntity(em);
    }

    @Test
    @Transactional
    public void createShopping() throws Exception {
        int databaseSizeBeforeCreate = shoppingRepository.findAll().size();

        // Create the Shopping
        ShoppingDTO shoppingDTO = shoppingMapper.toDto(shopping);
        restShoppingMockMvc.perform(post("/api/shoppings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingDTO)))
            .andExpect(status().isCreated());

        // Validate the Shopping in the database
        List<Shopping> shoppingList = shoppingRepository.findAll();
        assertThat(shoppingList).hasSize(databaseSizeBeforeCreate + 1);
        Shopping testShopping = shoppingList.get(shoppingList.size() - 1);
        assertThat(testShopping.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testShopping.getIdType()).isEqualTo(DEFAULT_ID_TYPE);
        assertThat(testShopping.getIdNumber()).isEqualTo(DEFAULT_ID_NUMBER);
        assertThat(testShopping.getDateOrder()).isEqualTo(DEFAULT_DATE_ORDER);
        assertThat(testShopping.getDateDelivery()).isEqualTo(DEFAULT_DATE_DELIVERY);
        assertThat(testShopping.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testShopping.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void createShoppingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shoppingRepository.findAll().size();

        // Create the Shopping with an existing ID
        shopping.setId(1L);
        ShoppingDTO shoppingDTO = shoppingMapper.toDto(shopping);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShoppingMockMvc.perform(post("/api/shoppings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Shopping in the database
        List<Shopping> shoppingList = shoppingRepository.findAll();
        assertThat(shoppingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllShoppings() throws Exception {
        // Initialize the database
        shoppingRepository.saveAndFlush(shopping);

        // Get all the shoppingList
        restShoppingMockMvc.perform(get("/api/shoppings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shopping.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].idType").value(hasItem(DEFAULT_ID_TYPE.toString())))
            .andExpect(jsonPath("$.[*].idNumber").value(hasItem(DEFAULT_ID_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].dateOrder").value(hasItem(DEFAULT_DATE_ORDER.toString())))
            .andExpect(jsonPath("$.[*].dateDelivery").value(hasItem(DEFAULT_DATE_DELIVERY.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())));
    }

    @Test
    @Transactional
    public void getShopping() throws Exception {
        // Initialize the database
        shoppingRepository.saveAndFlush(shopping);

        // Get the shopping
        restShoppingMockMvc.perform(get("/api/shoppings/{id}", shopping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shopping.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.idType").value(DEFAULT_ID_TYPE.toString()))
            .andExpect(jsonPath("$.idNumber").value(DEFAULT_ID_NUMBER.intValue()))
            .andExpect(jsonPath("$.dateOrder").value(DEFAULT_DATE_ORDER.toString()))
            .andExpect(jsonPath("$.dateDelivery").value(DEFAULT_DATE_DELIVERY.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingShopping() throws Exception {
        // Get the shopping
        restShoppingMockMvc.perform(get("/api/shoppings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShopping() throws Exception {
        // Initialize the database
        shoppingRepository.saveAndFlush(shopping);
        int databaseSizeBeforeUpdate = shoppingRepository.findAll().size();

        // Update the shopping
        Shopping updatedShopping = shoppingRepository.findOne(shopping.getId());
        // Disconnect from session so that the updates on updatedShopping are not directly saved in db
        em.detach(updatedShopping);
        updatedShopping
            .name(UPDATED_NAME)
            .idType(UPDATED_ID_TYPE)
            .idNumber(UPDATED_ID_NUMBER)
            .dateOrder(UPDATED_DATE_ORDER)
            .dateDelivery(UPDATED_DATE_DELIVERY)
            .address(UPDATED_ADDRESS)
            .total(UPDATED_TOTAL);
        ShoppingDTO shoppingDTO = shoppingMapper.toDto(updatedShopping);

        restShoppingMockMvc.perform(put("/api/shoppings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingDTO)))
            .andExpect(status().isOk());

        // Validate the Shopping in the database
        List<Shopping> shoppingList = shoppingRepository.findAll();
        assertThat(shoppingList).hasSize(databaseSizeBeforeUpdate);
        Shopping testShopping = shoppingList.get(shoppingList.size() - 1);
        assertThat(testShopping.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testShopping.getIdType()).isEqualTo(UPDATED_ID_TYPE);
        assertThat(testShopping.getIdNumber()).isEqualTo(UPDATED_ID_NUMBER);
        assertThat(testShopping.getDateOrder()).isEqualTo(UPDATED_DATE_ORDER);
        assertThat(testShopping.getDateDelivery()).isEqualTo(UPDATED_DATE_DELIVERY);
        assertThat(testShopping.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testShopping.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingShopping() throws Exception {
        int databaseSizeBeforeUpdate = shoppingRepository.findAll().size();

        // Create the Shopping
        ShoppingDTO shoppingDTO = shoppingMapper.toDto(shopping);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restShoppingMockMvc.perform(put("/api/shoppings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingDTO)))
            .andExpect(status().isCreated());

        // Validate the Shopping in the database
        List<Shopping> shoppingList = shoppingRepository.findAll();
        assertThat(shoppingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteShopping() throws Exception {
        // Initialize the database
        shoppingRepository.saveAndFlush(shopping);
        int databaseSizeBeforeDelete = shoppingRepository.findAll().size();

        // Get the shopping
        restShoppingMockMvc.perform(delete("/api/shoppings/{id}", shopping.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Shopping> shoppingList = shoppingRepository.findAll();
        assertThat(shoppingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Shopping.class);
        Shopping shopping1 = new Shopping();
        shopping1.setId(1L);
        Shopping shopping2 = new Shopping();
        shopping2.setId(shopping1.getId());
        assertThat(shopping1).isEqualTo(shopping2);
        shopping2.setId(2L);
        assertThat(shopping1).isNotEqualTo(shopping2);
        shopping1.setId(null);
        assertThat(shopping1).isNotEqualTo(shopping2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShoppingDTO.class);
        ShoppingDTO shoppingDTO1 = new ShoppingDTO();
        shoppingDTO1.setId(1L);
        ShoppingDTO shoppingDTO2 = new ShoppingDTO();
        assertThat(shoppingDTO1).isNotEqualTo(shoppingDTO2);
        shoppingDTO2.setId(shoppingDTO1.getId());
        assertThat(shoppingDTO1).isEqualTo(shoppingDTO2);
        shoppingDTO2.setId(2L);
        assertThat(shoppingDTO1).isNotEqualTo(shoppingDTO2);
        shoppingDTO1.setId(null);
        assertThat(shoppingDTO1).isNotEqualTo(shoppingDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(shoppingMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(shoppingMapper.fromId(null)).isNull();
    }
}
