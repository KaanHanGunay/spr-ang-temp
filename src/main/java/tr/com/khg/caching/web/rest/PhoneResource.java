package tr.com.khg.caching.web.rest;

import tr.com.khg.caching.domain.Phone;
import tr.com.khg.caching.service.PhoneService;
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
 * REST controller for managing {@link tr.com.khg.caching.domain.Phone}.
 */
@RestController
@RequestMapping("/api")
public class PhoneResource {

    private final Logger log = LoggerFactory.getLogger(PhoneResource.class);

    private static final String ENTITY_NAME = "phone";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PhoneService phoneService;

    public PhoneResource(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    /**
     * {@code POST  /phones} : Create a new phone.
     *
     * @param phone the phone to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new phone, or with status {@code 400 (Bad Request)} if the phone has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/phones")
    public ResponseEntity<Phone> createPhone(@RequestBody Phone phone) throws URISyntaxException {
        log.debug("REST request to save Phone : {}", phone);
        if (phone.getId() != null) {
            throw new BadRequestAlertException("A new phone cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Phone result = phoneService.save(phone);
        return ResponseEntity.created(new URI("/api/phones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /phones} : Updates an existing phone.
     *
     * @param phone the phone to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phone,
     * or with status {@code 400 (Bad Request)} if the phone is not valid,
     * or with status {@code 500 (Internal Server Error)} if the phone couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/phones")
    public ResponseEntity<Phone> updatePhone(@RequestBody Phone phone) throws URISyntaxException {
        log.debug("REST request to update Phone : {}", phone);
        if (phone.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Phone result = phoneService.save(phone);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, phone.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /phones} : get all the phones.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of phones in body.
     */
    @GetMapping("/phones")
    public List<Phone> getAllPhones() {
        log.debug("REST request to get all Phones");
        return phoneService.findAll();
    }

    /**
     * {@code GET  /phones/:id} : get the "id" phone.
     *
     * @param id the id of the phone to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the phone, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/phones/{id}")
    public ResponseEntity<Phone> getPhone(@PathVariable Long id) {
        log.debug("REST request to get Phone : {}", id);
        Optional<Phone> phone = phoneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(phone);
    }

    /**
     * {@code DELETE  /phones/:id} : delete the "id" phone.
     *
     * @param id the id of the phone to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/phones/{id}")
    public ResponseEntity<Void> deletePhone(@PathVariable Long id) {
        log.debug("REST request to delete Phone : {}", id);
        phoneService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
