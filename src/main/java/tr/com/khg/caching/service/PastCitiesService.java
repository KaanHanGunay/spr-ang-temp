package tr.com.khg.caching.service;

import tr.com.khg.caching.domain.PastCities;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PastCities}.
 */
public interface PastCitiesService {

    /**
     * Save a pastCities.
     *
     * @param pastCities the entity to save.
     * @return the persisted entity.
     */
    PastCities save(PastCities pastCities);

    /**
     * Get all the pastCities.
     *
     * @return the list of entities.
     */
    List<PastCities> findAll();


    /**
     * Get the "id" pastCities.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PastCities> findOne(Long id);

    /**
     * Delete the "id" pastCities.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
