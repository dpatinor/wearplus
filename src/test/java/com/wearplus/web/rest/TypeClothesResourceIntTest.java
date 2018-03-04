package com.wearplus.web.rest;

import com.wearplus.WearplusApp;

import com.wearplus.domain.TypeClothes;
import com.wearplus.repository.TypeClothesRepository;
import com.wearplus.service.TypeClothesService;
import com.wearplus.service.dto.TypeClothesDTO;
import com.wearplus.service.mapper.TypeClothesMapper;
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
 * Test class for the TypeClothesResource REST controller.
 *
 * @see TypeClothesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WearplusApp.class)
public class TypeClothesResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TypeClothesRepository typeClothesRepository;

    @Autowired
    private TypeClothesMapper typeClothesMapper;

    @Autowired
    private TypeClothesService typeClothesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTypeClothesMockMvc;

    private TypeClothes typeClothes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeClothesResource typeClothesResource = new TypeClothesResource(typeClothesService);
        this.restTypeClothesMockMvc = MockMvcBuilders.standaloneSetup(typeClothesResource)
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
    public static TypeClothes createEntity(EntityManager em) {
        TypeClothes typeClothes = new TypeClothes()
            .name(DEFAULT_NAME);
        return typeClothes;
    }

    @Before
    public void initTest() {
        typeClothes = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeClothes() throws Exception {
        int databaseSizeBeforeCreate = typeClothesRepository.findAll().size();

        // Create the TypeClothes
        TypeClothesDTO typeClothesDTO = typeClothesMapper.toDto(typeClothes);
        restTypeClothesMockMvc.perform(post("/api/type-clothes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeClothesDTO)))
            .andExpect(status().isCreated());

        // Validate the TypeClothes in the database
        List<TypeClothes> typeClothesList = typeClothesRepository.findAll();
        assertThat(typeClothesList).hasSize(databaseSizeBeforeCreate + 1);
        TypeClothes testTypeClothes = typeClothesList.get(typeClothesList.size() - 1);
        assertThat(testTypeClothes.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTypeClothesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeClothesRepository.findAll().size();

        // Create the TypeClothes with an existing ID
        typeClothes.setId(1L);
        TypeClothesDTO typeClothesDTO = typeClothesMapper.toDto(typeClothes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeClothesMockMvc.perform(post("/api/type-clothes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeClothesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeClothes in the database
        List<TypeClothes> typeClothesList = typeClothesRepository.findAll();
        assertThat(typeClothesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTypeClothes() throws Exception {
        // Initialize the database
        typeClothesRepository.saveAndFlush(typeClothes);

        // Get all the typeClothesList
        restTypeClothesMockMvc.perform(get("/api/type-clothes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeClothes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getTypeClothes() throws Exception {
        // Initialize the database
        typeClothesRepository.saveAndFlush(typeClothes);

        // Get the typeClothes
        restTypeClothesMockMvc.perform(get("/api/type-clothes/{id}", typeClothes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeClothes.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeClothes() throws Exception {
        // Get the typeClothes
        restTypeClothesMockMvc.perform(get("/api/type-clothes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeClothes() throws Exception {
        // Initialize the database
        typeClothesRepository.saveAndFlush(typeClothes);
        int databaseSizeBeforeUpdate = typeClothesRepository.findAll().size();

        // Update the typeClothes
        TypeClothes updatedTypeClothes = typeClothesRepository.findOne(typeClothes.getId());
        // Disconnect from session so that the updates on updatedTypeClothes are not directly saved in db
        em.detach(updatedTypeClothes);
        updatedTypeClothes
            .name(UPDATED_NAME);
        TypeClothesDTO typeClothesDTO = typeClothesMapper.toDto(updatedTypeClothes);

        restTypeClothesMockMvc.perform(put("/api/type-clothes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeClothesDTO)))
            .andExpect(status().isOk());

        // Validate the TypeClothes in the database
        List<TypeClothes> typeClothesList = typeClothesRepository.findAll();
        assertThat(typeClothesList).hasSize(databaseSizeBeforeUpdate);
        TypeClothes testTypeClothes = typeClothesList.get(typeClothesList.size() - 1);
        assertThat(testTypeClothes.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeClothes() throws Exception {
        int databaseSizeBeforeUpdate = typeClothesRepository.findAll().size();

        // Create the TypeClothes
        TypeClothesDTO typeClothesDTO = typeClothesMapper.toDto(typeClothes);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTypeClothesMockMvc.perform(put("/api/type-clothes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeClothesDTO)))
            .andExpect(status().isCreated());

        // Validate the TypeClothes in the database
        List<TypeClothes> typeClothesList = typeClothesRepository.findAll();
        assertThat(typeClothesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTypeClothes() throws Exception {
        // Initialize the database
        typeClothesRepository.saveAndFlush(typeClothes);
        int databaseSizeBeforeDelete = typeClothesRepository.findAll().size();

        // Get the typeClothes
        restTypeClothesMockMvc.perform(delete("/api/type-clothes/{id}", typeClothes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeClothes> typeClothesList = typeClothesRepository.findAll();
        assertThat(typeClothesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeClothes.class);
        TypeClothes typeClothes1 = new TypeClothes();
        typeClothes1.setId(1L);
        TypeClothes typeClothes2 = new TypeClothes();
        typeClothes2.setId(typeClothes1.getId());
        assertThat(typeClothes1).isEqualTo(typeClothes2);
        typeClothes2.setId(2L);
        assertThat(typeClothes1).isNotEqualTo(typeClothes2);
        typeClothes1.setId(null);
        assertThat(typeClothes1).isNotEqualTo(typeClothes2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeClothesDTO.class);
        TypeClothesDTO typeClothesDTO1 = new TypeClothesDTO();
        typeClothesDTO1.setId(1L);
        TypeClothesDTO typeClothesDTO2 = new TypeClothesDTO();
        assertThat(typeClothesDTO1).isNotEqualTo(typeClothesDTO2);
        typeClothesDTO2.setId(typeClothesDTO1.getId());
        assertThat(typeClothesDTO1).isEqualTo(typeClothesDTO2);
        typeClothesDTO2.setId(2L);
        assertThat(typeClothesDTO1).isNotEqualTo(typeClothesDTO2);
        typeClothesDTO1.setId(null);
        assertThat(typeClothesDTO1).isNotEqualTo(typeClothesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(typeClothesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(typeClothesMapper.fromId(null)).isNull();
    }
}
