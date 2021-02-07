package tr.com.khg.caching.service.impl;

import tr.com.khg.caching.service.PhoneService;
import tr.com.khg.caching.domain.Phone;
import tr.com.khg.caching.repository.PhoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Phone}.
 */
@Service
@Transactional
public class PhoneServiceImpl implements PhoneService {

    private final Logger log = LoggerFactory.getLogger(PhoneServiceImpl.class);

    private final PhoneRepository phoneRepository;

    public PhoneServiceImpl(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    @Override
    public Phone save(Phone phone) {
        log.debug("Request to save Phone : {}", phone);
        return phoneRepository.save(phone);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Phone> findAll() {
        log.debug("Request to get all Phones");
        return phoneRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Phone> findOne(Long id) {
        log.debug("Request to get Phone : {}", id);
        return phoneRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Phone : {}", id);
        phoneRepository.deleteById(id);
    }
}
