package tr.com.khg.caching.web.rest;

import tr.com.khg.caching.ModernProjectApp;
import tr.com.khg.caching.domain.Phone;
import tr.com.khg.caching.repository.PhoneRepository;
import tr.com.khg.caching.service.PhoneService;

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
 * Integration tests for the {@link PhoneResource} REST controller.
 */
@SpringBootTest(classes = ModernProjectApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PhoneResourceIT {

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_START_YEAR = 1;
    private static final Integer UPDATED_START_YEAR = 2;

    private static final Integer DEFAULT_END_YEAR = 1;
    private static final Integer UPDATED_END_YEAR = 2;

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private PhoneService phoneService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPhoneMockMvc;

    private Phone phone;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Phone createEntity(EntityManager em) {
        Phone phone = new Phone()
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .startYear(DEFAULT_START_YEAR)
            .endYear(DEFAULT_END_YEAR);
        return phone;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Phone createUpdatedEntity(EntityManager em) {
        Phone phone = new Phone()
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .startYear(UPDATED_START_YEAR)
            .endYear(UPDATED_END_YEAR);
        return phone;
    }

    @BeforeEach
    public void initTest() {
        phone = createEntity(em);
    }

    @Test
    @Transactional
    public void createPhone() throws Exception {
        int databaseSizeBeforeCreate = phoneRepository.findAll().size();
        // Create the Phone
        restPhoneMockMvc.perform(post("/api/phones").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phone)))
            .andExpect(status().isCreated());

        // Validate the Phone in the database
        List<Phone> phoneList = phoneRepository.findAll();
        assertThat(phoneList).hasSize(databaseSizeBeforeCreate + 1);
        Phone testPhone = phoneList.get(phoneList.size() - 1);
        assertThat(testPhone.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testPhone.getStartYear()).isEqualTo(DEFAULT_START_YEAR);
        assertThat(testPhone.getEndYear()).isEqualTo(DEFAULT_END_YEAR);
    }

    @Test
    @Transactional
    public void createPhoneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = phoneRepository.findAll().size();

        // Create the Phone with an existing ID
        phone.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhoneMockMvc.perform(post("/api/phones").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phone)))
            .andExpect(status().isBadRequest());

        // Validate the Phone in the database
        List<Phone> phoneList = phoneRepository.findAll();
        assertThat(phoneList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPhones() throws Exception {
        // Initialize the database
        phoneRepository.saveAndFlush(phone);

        // Get all the phoneList
        restPhoneMockMvc.perform(get("/api/phones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phone.getId().intValue())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].startYear").value(hasItem(DEFAULT_START_YEAR)))
            .andExpect(jsonPath("$.[*].endYear").value(hasItem(DEFAULT_END_YEAR)));
    }
    
    @Test
    @Transactional
    public void getPhone() throws Exception {
        // Initialize the database
        phoneRepository.saveAndFlush(phone);

        // Get the phone
        restPhoneMockMvc.perform(get("/api/phones/{id}", phone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(phone.getId().intValue()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.startYear").value(DEFAULT_START_YEAR))
            .andExpect(jsonPath("$.endYear").value(DEFAULT_END_YEAR));
    }
    @Test
    @Transactional
    public void getNonExistingPhone() throws Exception {
        // Get the phone
        restPhoneMockMvc.perform(get("/api/phones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePhone() throws Exception {
        // Initialize the database
        phoneService.save(phone);

        int databaseSizeBeforeUpdate = phoneRepository.findAll().size();

        // Update the phone
        Phone updatedPhone = phoneRepository.findById(phone.getId()).get();
        // Disconnect from session so that the updates on updatedPhone are not directly saved in db
        em.detach(updatedPhone);
        updatedPhone
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .startYear(UPDATED_START_YEAR)
            .endYear(UPDATED_END_YEAR);

        restPhoneMockMvc.perform(put("/api/phones").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPhone)))
            .andExpect(status().isOk());

        // Validate the Phone in the database
        List<Phone> phoneList = phoneRepository.findAll();
        assertThat(phoneList).hasSize(databaseSizeBeforeUpdate);
        Phone testPhone = phoneList.get(phoneList.size() - 1);
        assertThat(testPhone.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testPhone.getStartYear()).isEqualTo(UPDATED_START_YEAR);
        assertThat(testPhone.getEndYear()).isEqualTo(UPDATED_END_YEAR);
    }

    @Test
    @Transactional
    public void updateNonExistingPhone() throws Exception {
        int databaseSizeBeforeUpdate = phoneRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhoneMockMvc.perform(put("/api/phones").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phone)))
            .andExpect(status().isBadRequest());

        // Validate the Phone in the database
        List<Phone> phoneList = phoneRepository.findAll();
        assertThat(phoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePhone() throws Exception {
        // Initialize the database
        phoneService.save(phone);

        int databaseSizeBeforeDelete = phoneRepository.findAll().size();

        // Delete the phone
        restPhoneMockMvc.perform(delete("/api/phones/{id}", phone.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Phone> phoneList = phoneRepository.findAll();
        assertThat(phoneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
