package tr.com.khg.caching.web.rest;

import tr.com.khg.caching.ModernProjectApp;
import tr.com.khg.caching.domain.Identity;
import tr.com.khg.caching.repository.IdentityRepository;
import tr.com.khg.caching.service.IdentityService;

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
 * Integration tests for the {@link IdentityResource} REST controller.
 */
@SpringBootTest(classes = ModernProjectApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class IdentityResourceIT {

    private static final String DEFAULT_IDENTITY_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTITY_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_MOTHER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MOTHER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FATHER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FATHER_NAME = "BBBBBBBBBB";

    @Autowired
    private IdentityRepository identityRepository;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIdentityMockMvc;

    private Identity identity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Identity createEntity(EntityManager em) {
        Identity identity = new Identity()
            .identityNumber(DEFAULT_IDENTITY_NUMBER)
            .motherName(DEFAULT_MOTHER_NAME)
            .fatherName(DEFAULT_FATHER_NAME);
        return identity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Identity createUpdatedEntity(EntityManager em) {
        Identity identity = new Identity()
            .identityNumber(UPDATED_IDENTITY_NUMBER)
            .motherName(UPDATED_MOTHER_NAME)
            .fatherName(UPDATED_FATHER_NAME);
        return identity;
    }

    @BeforeEach
    public void initTest() {
        identity = createEntity(em);
    }

    @Test
    @Transactional
    public void createIdentity() throws Exception {
        int databaseSizeBeforeCreate = identityRepository.findAll().size();
        // Create the Identity
        restIdentityMockMvc.perform(post("/api/identities").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(identity)))
            .andExpect(status().isCreated());

        // Validate the Identity in the database
        List<Identity> identityList = identityRepository.findAll();
        assertThat(identityList).hasSize(databaseSizeBeforeCreate + 1);
        Identity testIdentity = identityList.get(identityList.size() - 1);
        assertThat(testIdentity.getIdentityNumber()).isEqualTo(DEFAULT_IDENTITY_NUMBER);
        assertThat(testIdentity.getMotherName()).isEqualTo(DEFAULT_MOTHER_NAME);
        assertThat(testIdentity.getFatherName()).isEqualTo(DEFAULT_FATHER_NAME);
    }

    @Test
    @Transactional
    public void createIdentityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = identityRepository.findAll().size();

        // Create the Identity with an existing ID
        identity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIdentityMockMvc.perform(post("/api/identities").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(identity)))
            .andExpect(status().isBadRequest());

        // Validate the Identity in the database
        List<Identity> identityList = identityRepository.findAll();
        assertThat(identityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllIdentities() throws Exception {
        // Initialize the database
        identityRepository.saveAndFlush(identity);

        // Get all the identityList
        restIdentityMockMvc.perform(get("/api/identities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(identity.getId().intValue())))
            .andExpect(jsonPath("$.[*].identityNumber").value(hasItem(DEFAULT_IDENTITY_NUMBER)))
            .andExpect(jsonPath("$.[*].motherName").value(hasItem(DEFAULT_MOTHER_NAME)))
            .andExpect(jsonPath("$.[*].fatherName").value(hasItem(DEFAULT_FATHER_NAME)));
    }
    
    @Test
    @Transactional
    public void getIdentity() throws Exception {
        // Initialize the database
        identityRepository.saveAndFlush(identity);

        // Get the identity
        restIdentityMockMvc.perform(get("/api/identities/{id}", identity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(identity.getId().intValue()))
            .andExpect(jsonPath("$.identityNumber").value(DEFAULT_IDENTITY_NUMBER))
            .andExpect(jsonPath("$.motherName").value(DEFAULT_MOTHER_NAME))
            .andExpect(jsonPath("$.fatherName").value(DEFAULT_FATHER_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingIdentity() throws Exception {
        // Get the identity
        restIdentityMockMvc.perform(get("/api/identities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIdentity() throws Exception {
        // Initialize the database
        identityService.save(identity);

        int databaseSizeBeforeUpdate = identityRepository.findAll().size();

        // Update the identity
        Identity updatedIdentity = identityRepository.findById(identity.getId()).get();
        // Disconnect from session so that the updates on updatedIdentity are not directly saved in db
        em.detach(updatedIdentity);
        updatedIdentity
            .identityNumber(UPDATED_IDENTITY_NUMBER)
            .motherName(UPDATED_MOTHER_NAME)
            .fatherName(UPDATED_FATHER_NAME);

        restIdentityMockMvc.perform(put("/api/identities").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedIdentity)))
            .andExpect(status().isOk());

        // Validate the Identity in the database
        List<Identity> identityList = identityRepository.findAll();
        assertThat(identityList).hasSize(databaseSizeBeforeUpdate);
        Identity testIdentity = identityList.get(identityList.size() - 1);
        assertThat(testIdentity.getIdentityNumber()).isEqualTo(UPDATED_IDENTITY_NUMBER);
        assertThat(testIdentity.getMotherName()).isEqualTo(UPDATED_MOTHER_NAME);
        assertThat(testIdentity.getFatherName()).isEqualTo(UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingIdentity() throws Exception {
        int databaseSizeBeforeUpdate = identityRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIdentityMockMvc.perform(put("/api/identities").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(identity)))
            .andExpect(status().isBadRequest());

        // Validate the Identity in the database
        List<Identity> identityList = identityRepository.findAll();
        assertThat(identityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIdentity() throws Exception {
        // Initialize the database
        identityService.save(identity);

        int databaseSizeBeforeDelete = identityRepository.findAll().size();

        // Delete the identity
        restIdentityMockMvc.perform(delete("/api/identities/{id}", identity.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Identity> identityList = identityRepository.findAll();
        assertThat(identityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
