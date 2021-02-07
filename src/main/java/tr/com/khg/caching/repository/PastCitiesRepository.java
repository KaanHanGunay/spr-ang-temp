package tr.com.khg.caching.repository;

import tr.com.khg.caching.domain.PastCities;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PastCities entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PastCitiesRepository extends JpaRepository<PastCities, Long> {
}
