package tr.com.khg.caching.service.impl;

import tr.com.khg.caching.service.IdentityService;
import tr.com.khg.caching.domain.Identity;
import tr.com.khg.caching.repository.IdentityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Identity}.
 */
@Service
@Transactional
public class IdentityServiceImpl implements IdentityService {

    private final Logger log = LoggerFactory.getLogger(IdentityServiceImpl.class);

    private final IdentityRepository identityRepository;

    public IdentityServiceImpl(IdentityRepository identityRepository) {
        this.identityRepository = identityRepository;
    }

    @Override
    public Identity save(Identity identity) {
        log.debug("Request to save Identity : {}", identity);
        return identityRepository.save(identity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Identity> findAll() {
        log.debug("Request to get all Identities");
        return identityRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Identity> findOne(Long id) {
        log.debug("Request to get Identity : {}", id);
        return identityRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Identity : {}", id);
        identityRepository.deleteById(id);
    }
}
