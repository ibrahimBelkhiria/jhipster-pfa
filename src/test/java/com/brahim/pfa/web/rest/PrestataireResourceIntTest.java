package com.brahim.pfa.web.rest;

import com.brahim.pfa.PfaprojectApp;

import com.brahim.pfa.domain.Prestataire;
import com.brahim.pfa.repository.PrestataireRepository;
import com.brahim.pfa.service.PrestataireService;
import com.brahim.pfa.repository.search.PrestataireSearchRepository;
import com.brahim.pfa.web.rest.errors.ExceptionTranslator;

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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.brahim.pfa.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PrestataireResource REST controller.
 *
 * @see PrestataireResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PfaprojectApp.class)
public class PrestataireResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private PrestataireRepository prestataireRepository;

    @Autowired
    private PrestataireService prestataireService;

    @Autowired
    private PrestataireSearchRepository prestataireSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPrestataireMockMvc;

    private Prestataire prestataire;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrestataireResource prestataireResource = new PrestataireResource(prestataireService);
        this.restPrestataireMockMvc = MockMvcBuilders.standaloneSetup(prestataireResource)
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
    public static Prestataire createEntity(EntityManager em) {
        Prestataire prestataire = new Prestataire()
            .name(DEFAULT_NAME)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .email(DEFAULT_EMAIL);
        return prestataire;
    }

    @Before
    public void initTest() {
        prestataireSearchRepository.deleteAll();
        prestataire = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrestataire() throws Exception {
        int databaseSizeBeforeCreate = prestataireRepository.findAll().size();

        // Create the Prestataire
        restPrestataireMockMvc.perform(post("/api/prestataires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestataire)))
            .andExpect(status().isCreated());

        // Validate the Prestataire in the database
        List<Prestataire> prestataireList = prestataireRepository.findAll();
        assertThat(prestataireList).hasSize(databaseSizeBeforeCreate + 1);
        Prestataire testPrestataire = prestataireList.get(prestataireList.size() - 1);
        assertThat(testPrestataire.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPrestataire.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testPrestataire.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testPrestataire.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPrestataire.getEmail()).isEqualTo(DEFAULT_EMAIL);

        // Validate the Prestataire in Elasticsearch
        Prestataire prestataireEs = prestataireSearchRepository.findOne(testPrestataire.getId());
        assertThat(prestataireEs).isEqualToIgnoringGivenFields(testPrestataire);
    }

    @Test
    @Transactional
    public void createPrestataireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prestataireRepository.findAll().size();

        // Create the Prestataire with an existing ID
        prestataire.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrestataireMockMvc.perform(post("/api/prestataires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestataire)))
            .andExpect(status().isBadRequest());

        // Validate the Prestataire in the database
        List<Prestataire> prestataireList = prestataireRepository.findAll();
        assertThat(prestataireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = prestataireRepository.findAll().size();
        // set the field null
        prestataire.setName(null);

        // Create the Prestataire, which fails.

        restPrestataireMockMvc.perform(post("/api/prestataires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestataire)))
            .andExpect(status().isBadRequest());

        List<Prestataire> prestataireList = prestataireRepository.findAll();
        assertThat(prestataireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = prestataireRepository.findAll().size();
        // set the field null
        prestataire.setDescription(null);

        // Create the Prestataire, which fails.

        restPrestataireMockMvc.perform(post("/api/prestataires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestataire)))
            .andExpect(status().isBadRequest());

        List<Prestataire> prestataireList = prestataireRepository.findAll();
        assertThat(prestataireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = prestataireRepository.findAll().size();
        // set the field null
        prestataire.setEmail(null);

        // Create the Prestataire, which fails.

        restPrestataireMockMvc.perform(post("/api/prestataires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestataire)))
            .andExpect(status().isBadRequest());

        List<Prestataire> prestataireList = prestataireRepository.findAll();
        assertThat(prestataireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPrestataires() throws Exception {
        // Initialize the database
        prestataireRepository.saveAndFlush(prestataire);

        // Get all the prestataireList
        restPrestataireMockMvc.perform(get("/api/prestataires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prestataire.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getPrestataire() throws Exception {
        // Initialize the database
        prestataireRepository.saveAndFlush(prestataire);

        // Get the prestataire
        restPrestataireMockMvc.perform(get("/api/prestataires/{id}", prestataire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prestataire.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPrestataire() throws Exception {
        // Get the prestataire
        restPrestataireMockMvc.perform(get("/api/prestataires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrestataire() throws Exception {
        // Initialize the database
        prestataireService.save(prestataire);

        int databaseSizeBeforeUpdate = prestataireRepository.findAll().size();

        // Update the prestataire
        Prestataire updatedPrestataire = prestataireRepository.findOne(prestataire.getId());
        // Disconnect from session so that the updates on updatedPrestataire are not directly saved in db
        em.detach(updatedPrestataire);
        updatedPrestataire
            .name(UPDATED_NAME)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION)
            .email(UPDATED_EMAIL);

        restPrestataireMockMvc.perform(put("/api/prestataires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrestataire)))
            .andExpect(status().isOk());

        // Validate the Prestataire in the database
        List<Prestataire> prestataireList = prestataireRepository.findAll();
        assertThat(prestataireList).hasSize(databaseSizeBeforeUpdate);
        Prestataire testPrestataire = prestataireList.get(prestataireList.size() - 1);
        assertThat(testPrestataire.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPrestataire.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testPrestataire.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testPrestataire.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPrestataire.getEmail()).isEqualTo(UPDATED_EMAIL);

        // Validate the Prestataire in Elasticsearch
        Prestataire prestataireEs = prestataireSearchRepository.findOne(testPrestataire.getId());
        assertThat(prestataireEs).isEqualToIgnoringGivenFields(testPrestataire);
    }

    @Test
    @Transactional
    public void updateNonExistingPrestataire() throws Exception {
        int databaseSizeBeforeUpdate = prestataireRepository.findAll().size();

        // Create the Prestataire

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPrestataireMockMvc.perform(put("/api/prestataires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestataire)))
            .andExpect(status().isCreated());

        // Validate the Prestataire in the database
        List<Prestataire> prestataireList = prestataireRepository.findAll();
        assertThat(prestataireList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePrestataire() throws Exception {
        // Initialize the database
        prestataireService.save(prestataire);

        int databaseSizeBeforeDelete = prestataireRepository.findAll().size();

        // Get the prestataire
        restPrestataireMockMvc.perform(delete("/api/prestataires/{id}", prestataire.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean prestataireExistsInEs = prestataireSearchRepository.exists(prestataire.getId());
        assertThat(prestataireExistsInEs).isFalse();

        // Validate the database is empty
        List<Prestataire> prestataireList = prestataireRepository.findAll();
        assertThat(prestataireList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPrestataire() throws Exception {
        // Initialize the database
        prestataireService.save(prestataire);

        // Search the prestataire
        restPrestataireMockMvc.perform(get("/api/_search/prestataires?query=id:" + prestataire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prestataire.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prestataire.class);
        Prestataire prestataire1 = new Prestataire();
        prestataire1.setId(1L);
        Prestataire prestataire2 = new Prestataire();
        prestataire2.setId(prestataire1.getId());
        assertThat(prestataire1).isEqualTo(prestataire2);
        prestataire2.setId(2L);
        assertThat(prestataire1).isNotEqualTo(prestataire2);
        prestataire1.setId(null);
        assertThat(prestataire1).isNotEqualTo(prestataire2);
    }
}
