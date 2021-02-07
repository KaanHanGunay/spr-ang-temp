package tr.com.khg.caching.web.rest;

import tr.com.khg.caching.domain.PastCities;
import tr.com.khg.caching.service.PastCitiesService;
import tr.com.khg.caching.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link tr.com.khg.caching.domain.PastCities}.
 */
@RestController
@RequestMapping("/api")
public class PastCitiesResource {

    private final Logger log = LoggerFactory.getLogger(PastCitiesResource.class);

    private static final String ENTITY_NAME = "pastCities";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PastCitiesService pastCitiesService;

    public PastCitiesResource(PastCitiesService pastCitiesService) {
        this.pastCitiesService = pastCitiesService;
    }

    /**
     * {@code POST  /past-cities} : Create a new pastCities.
     *
     * @param pastCities the pastCities to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pastCities, or with status {@code 400 (Bad Request)} if the pastCities has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/past-cities")
    public ResponseEntity<PastCities> createPastCities(@RequestBody PastCities pastCities) throws URISyntaxException {
        log.debug("REST request to save PastCities : {}", pastCities);
        if (pastCities.getId() != null) {
            throw new BadRequestAlertException("A new pastCities cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PastCities result = pastCitiesService.save(pastCities);
        return ResponseEntity.created(new URI("/api/past-cities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /past-cities} : Updates an existing pastCities.
     *
     * @param pastCities the pastCities to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pastCities,
     * or with status {@code 400 (Bad Request)} if the pastCities is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pastCities couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/past-cities")
    public ResponseEntity<PastCities> updatePastCities(@RequestBody PastCities pastCities) throws URISyntaxException {
        log.debug("REST request to update PastCities : {}", pastCities);
        if (pastCities.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PastCities result = pastCitiesService.save(pastCities);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pastCities.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /past-cities} : get all the pastCities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pastCities in body.
     */
    @GetMapping("/past-cities")
    public List<PastCities> getAllPastCities() {
        log.debug("REST request to get all PastCities");
        return pastCitiesService.findAll();
    }

    /**
     * {@code GET  /past-cities/:id} : get the "id" pastCities.
     *
     * @param id the id of the pastCities to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pastCities, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/past-cities/{id}")
    public ResponseEntity<PastCities> getPastCities(@PathVariable Long id) {
        log.debug("REST request to get PastCities : {}", id);
        Optional<PastCities> pastCities = pastCitiesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pastCities);
    }

    /**
     * {@code DELETE  /past-cities/:id} : delete the "id" pastCities.
     *
     * @param id the id of the pastCities to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/past-cities/{id}")
    public ResponseEntity<Void> deletePastCities(@PathVariable Long id) {
        log.debug("REST request to delete PastCities : {}", id);
        pastCitiesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
