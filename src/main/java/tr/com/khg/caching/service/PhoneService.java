package tr.com.khg.caching.service;

import tr.com.khg.caching.domain.Phone;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Phone}.
 */
public interface PhoneService {

    /**
     * Save a phone.
     *
     * @param phone the entity to save.
     * @return the persisted entity.
     */
    Phone save(Phone phone);

    /**
     * Get all the phones.
     *
     * @return the list of entities.
     */
    List<Phone> findAll();


    /**
     * Get the "id" phone.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Phone> findOne(Long id);

    /**
     * Delete the "id" phone.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
