package tr.com.khg.caching.service;

import tr.com.khg.caching.domain.Identity;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Identity}.
 */
public interface IdentityService {

    /**
     * Save a identity.
     *
     * @param identity the entity to save.
     * @return the persisted entity.
     */
    Identity save(Identity identity);

    /**
     * Get all the identities.
     *
     * @return the list of entities.
     */
    List<Identity> findAll();


    /**
     * Get the "id" identity.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Identity> findOne(Long id);

    /**
     * Delete the "id" identity.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
