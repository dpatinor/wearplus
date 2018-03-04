package com.wearplus.web.rest;

import com.wearplus.WearplusApp;

import com.wearplus.domain.Clothes;
import com.wearplus.repository.ClothesRepository;
import com.wearplus.service.ClothesService;
import com.wearplus.service.dto.ClothesDTO;
import com.wearplus.service.mapper.ClothesMapper;
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
 * Test class for the ClothesResource REST controller.
 *
 * @see ClothesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WearplusApp.class)
public class ClothesResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_QUANTITY = 1L;
    private static final Long UPDATED_QUANTITY = 2L;

    private static final Float DEFAULT_VALUE = 1F;
    private static final Float UPDATED_VALUE = 2F;

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    @Autowired
    private ClothesRepository clothesRepository;

    @Autowired
    private ClothesMapper clothesMapper;

    @Autowired
    private ClothesService clothesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClothesMockMvc;

    private Clothes clothes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClothesResource clothesResource = new ClothesResource(clothesService);
        this.restClothesMockMvc = MockMvcBuilders.standaloneSetup(clothesResource)
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
    public static Clothes createEntity(EntityManager em) {
        Clothes clothes = new Clothes()
            .name(DEFAULT_NAME)
            .quantity(DEFAULT_QUANTITY)
            .value(DEFAULT_VALUE)
            .image(DEFAULT_IMAGE);
        return clothes;
    }

    @Before
    public void initTest() {
        clothes = createEntity(em);
    }

    @Test
    @Transactional
    public void createClothes() throws Exception {
        int databaseSizeBeforeCreate = clothesRepository.findAll().size();

        // Create the Clothes
        ClothesDTO clothesDTO = clothesMapper.toDto(clothes);
        restClothesMockMvc.perform(post("/api/clothes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clothesDTO)))
            .andExpect(status().isCreated());

        // Validate the Clothes in the database
        List<Clothes> clothesList = clothesRepository.findAll();
        assertThat(clothesList).hasSize(databaseSizeBeforeCreate + 1);
        Clothes testClothes = clothesList.get(clothesList.size() - 1);
        assertThat(testClothes.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClothes.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testClothes.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testClothes.getImage()).isEqualTo(DEFAULT_IMAGE);
    }

    @Test
    @Transactional
    public void createClothesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clothesRepository.findAll().size();

        // Create the Clothes with an existing ID
        clothes.setId(1L);
        ClothesDTO clothesDTO = clothesMapper.toDto(clothes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClothesMockMvc.perform(post("/api/clothes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clothesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Clothes in the database
        List<Clothes> clothesList = clothesRepository.findAll();
        assertThat(clothesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClothes() throws Exception {
        // Initialize the database
        clothesRepository.saveAndFlush(clothes);

        // Get all the clothesList
        restClothesMockMvc.perform(get("/api/clothes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clothes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())));
    }

    @Test
    @Transactional
    public void getClothes() throws Exception {
        // Initialize the database
        clothesRepository.saveAndFlush(clothes);

        // Get the clothes
        restClothesMockMvc.perform(get("/api/clothes/{id}", clothes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clothes.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClothes() throws Exception {
        // Get the clothes
        restClothesMockMvc.perform(get("/api/clothes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClothes() throws Exception {
        // Initialize the database
        clothesRepository.saveAndFlush(clothes);
        int databaseSizeBeforeUpdate = clothesRepository.findAll().size();

        // Update the clothes
        Clothes updatedClothes = clothesRepository.findOne(clothes.getId());
        // Disconnect from session so that the updates on updatedClothes are not directly saved in db
        em.detach(updatedClothes);
        updatedClothes
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .value(UPDATED_VALUE)
            .image(UPDATED_IMAGE);
        ClothesDTO clothesDTO = clothesMapper.toDto(updatedClothes);

        restClothesMockMvc.perform(put("/api/clothes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clothesDTO)))
            .andExpect(status().isOk());

        // Validate the Clothes in the database
        List<Clothes> clothesList = clothesRepository.findAll();
        assertThat(clothesList).hasSize(databaseSizeBeforeUpdate);
        Clothes testClothes = clothesList.get(clothesList.size() - 1);
        assertThat(testClothes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClothes.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testClothes.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testClothes.getImage()).isEqualTo(UPDATED_IMAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingClothes() throws Exception {
        int databaseSizeBeforeUpdate = clothesRepository.findAll().size();

        // Create the Clothes
        ClothesDTO clothesDTO = clothesMapper.toDto(clothes);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClothesMockMvc.perform(put("/api/clothes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clothesDTO)))
            .andExpect(status().isCreated());

        // Validate the Clothes in the database
        List<Clothes> clothesList = clothesRepository.findAll();
        assertThat(clothesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClothes() throws Exception {
        // Initialize the database
        clothesRepository.saveAndFlush(clothes);
        int databaseSizeBeforeDelete = clothesRepository.findAll().size();

        // Get the clothes
        restClothesMockMvc.perform(delete("/api/clothes/{id}", clothes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Clothes> clothesList = clothesRepository.findAll();
        assertThat(clothesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Clothes.class);
        Clothes clothes1 = new Clothes();
        clothes1.setId(1L);
        Clothes clothes2 = new Clothes();
        clothes2.setId(clothes1.getId());
        assertThat(clothes1).isEqualTo(clothes2);
        clothes2.setId(2L);
        assertThat(clothes1).isNotEqualTo(clothes2);
        clothes1.setId(null);
        assertThat(clothes1).isNotEqualTo(clothes2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClothesDTO.class);
        ClothesDTO clothesDTO1 = new ClothesDTO();
        clothesDTO1.setId(1L);
        ClothesDTO clothesDTO2 = new ClothesDTO();
        assertThat(clothesDTO1).isNotEqualTo(clothesDTO2);
        clothesDTO2.setId(clothesDTO1.getId());
        assertThat(clothesDTO1).isEqualTo(clothesDTO2);
        clothesDTO2.setId(2L);
        assertThat(clothesDTO1).isNotEqualTo(clothesDTO2);
        clothesDTO1.setId(null);
        assertThat(clothesDTO1).isNotEqualTo(clothesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(clothesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(clothesMapper.fromId(null)).isNull();
    }
}
