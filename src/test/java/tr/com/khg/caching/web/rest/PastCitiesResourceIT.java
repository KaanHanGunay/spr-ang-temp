package tr.com.khg.caching.web.rest;

import tr.com.khg.caching.ModernProjectApp;
import tr.com.khg.caching.domain.PastCities;
import tr.com.khg.caching.repository.PastCitiesRepository;
import tr.com.khg.caching.service.PastCitiesService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PastCitiesResource} REST controller.
 */
@SpringBootTest(classes = ModernProjectApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PastCitiesResourceIT {

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final Integer DEFAULT_START_YEAR = 1;
    private static final Integer UPDATED_START_YEAR = 2;

    private static final Integer DEFAULT_END_YEAR = 1;
    private static final Integer UPDATED_END_YEAR = 2;

    @Autowired
    private PastCitiesRepository pastCitiesRepository;

    @Autowired
    private PastCitiesService pastCitiesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPastCitiesMockMvc;

    private PastCities pastCities;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PastCities createEntity(EntityManager em) {
        PastCities pastCities = new PastCities()
            .city(DEFAULT_CITY)
            .startYear(DEFAULT_START_YEAR)
            .endYear(DEFAULT_END_YEAR);
        return pastCities;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PastCities createUpdatedEntity(EntityManager em) {
        PastCities pastCities = new PastCities()
            .city(UPDATED_CITY)
            .startYear(UPDATED_START_YEAR)
            .endYear(UPDATED_END_YEAR);
        return pastCities;
    }

    @BeforeEach
    public void initTest() {
        pastCities = createEntity(em);
    }

    @Test
    @Transactional
    public void createPastCities() throws Exception {
        int databaseSizeBeforeCreate = pastCitiesRepository.findAll().size();
        // Create the PastCities
        restPastCitiesMockMvc.perform(post("/api/past-cities").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pastCities)))
            .andExpect(status().isCreated());

        // Validate the PastCities in the database
        List<PastCities> pastCitiesList = pastCitiesRepository.findAll();
        assertThat(pastCitiesList).hasSize(databaseSizeBeforeCreate + 1);
        PastCities testPastCities = pastCitiesList.get(pastCitiesList.size() - 1);
        assertThat(testPastCities.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testPastCities.getStartYear()).isEqualTo(DEFAULT_START_YEAR);
        assertThat(testPastCities.getEndYear()).isEqualTo(DEFAULT_END_YEAR);
    }

    @Test
    @Transactional
    public void createPastCitiesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pastCitiesRepository.findAll().size();

        // Create the PastCities with an existing ID
        pastCities.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPastCitiesMockMvc.perform(post("/api/past-cities").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pastCities)))
            .andExpect(status().isBadRequest());

        // Validate the PastCities in the database
        List<PastCities> pastCitiesList = pastCitiesRepository.findAll();
        assertThat(pastCitiesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPastCities() throws Exception {
        // Initialize the database
        pastCitiesRepository.saveAndFlush(pastCities);

        // Get all the pastCitiesList
        restPastCitiesMockMvc.perform(get("/api/past-cities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pastCities.getId().intValue())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].startYear").value(hasItem(DEFAULT_START_YEAR)))
            .andExpect(jsonPath("$.[*].endYear").value(hasItem(DEFAULT_END_YEAR)));
    }
    
    @Test
    @Transactional
    public void getPastCities() throws Exception {
        // Initialize the database
        pastCitiesRepository.saveAndFlush(pastCities);

        // Get the pastCities
        restPastCitiesMockMvc.perform(get("/api/past-cities/{id}", pastCities.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pastCities.getId().intValue()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.startYear").value(DEFAULT_START_YEAR))
            .andExpect(jsonPath("$.endYear").value(DEFAULT_END_YEAR));
    }
    @Test
    @Transactional
    public void getNonExistingPastCities() throws Exception {
        // Get the pastCities
        restPastCitiesMockMvc.perform(get("/api/past-cities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePastCities() throws Exception {
        // Initialize the database
        pastCitiesService.save(pastCities);

        int databaseSizeBeforeUpdate = pastCitiesRepository.findAll().size();

        // Update the pastCities
        PastCities updatedPastCities = pastCitiesRepository.findById(pastCities.getId()).get();
        // Disconnect from session so that the updates on updatedPastCities are not directly saved in db
        em.detach(updatedPastCities);
        updatedPastCities
            .city(UPDATED_CITY)
            .startYear(UPDATED_START_YEAR)
            .endYear(UPDATED_END_YEAR);

        restPastCitiesMockMvc.perform(put("/api/past-cities").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPastCities)))
            .andExpect(status().isOk());

        // Validate the PastCities in the database
        List<PastCities> pastCitiesList = pastCitiesRepository.findAll();
        assertThat(pastCitiesList).hasSize(databaseSizeBeforeUpdate);
        PastCities testPastCities = pastCitiesList.get(pastCitiesList.size() - 1);
        assertThat(testPastCities.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testPastCities.getStartYear()).isEqualTo(UPDATED_START_YEAR);
        assertThat(testPastCities.getEndYear()).isEqualTo(UPDATED_END_YEAR);
    }

    @Test
    @Transactional
    public void updateNonExistingPastCities() throws Exception {
        int databaseSizeBeforeUpdate = pastCitiesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPastCitiesMockMvc.perform(put("/api/past-cities").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pastCities)))
            .andExpect(status().isBadRequest());

        // Validate the PastCities in the database
        List<PastCities> pastCitiesList = pastCitiesRepository.findAll();
        assertThat(pastCitiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePastCities() throws Exception {
        // Initialize the database
        pastCitiesService.save(pastCities);

        int databaseSizeBeforeDelete = pastCitiesRepository.findAll().size();

        // Delete the pastCities
        restPastCitiesMockMvc.perform(delete("/api/past-cities/{id}", pastCities.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PastCities> pastCitiesList = pastCitiesRepository.findAll();
        assertThat(pastCitiesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
