package tr.com.khg.caching.service.impl;

import tr.com.khg.caching.service.PastCitiesService;
import tr.com.khg.caching.domain.PastCities;
import tr.com.khg.caching.repository.PastCitiesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link PastCities}.
 */
@Service
@Transactional
public class PastCitiesServiceImpl implements PastCitiesService {

    private final Logger log = LoggerFactory.getLogger(PastCitiesServiceImpl.class);

    private final PastCitiesRepository pastCitiesRepository;

    public PastCitiesServiceImpl(PastCitiesRepository pastCitiesRepository) {
        this.pastCitiesRepository = pastCitiesRepository;
    }

    @Override
    public PastCities save(PastCities pastCities) {
        log.debug("Request to save PastCities : {}", pastCities);
        return pastCitiesRepository.save(pastCities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PastCities> findAll() {
        log.debug("Request to get all PastCities");
        return pastCitiesRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PastCities> findOne(Long id) {
        log.debug("Request to get PastCities : {}", id);
        return pastCitiesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PastCities : {}", id);
        pastCitiesRepository.deleteById(id);
    }
}
