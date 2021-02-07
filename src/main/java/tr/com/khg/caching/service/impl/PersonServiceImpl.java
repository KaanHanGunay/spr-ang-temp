package tr.com.khg.caching.service.impl;

import tr.com.khg.caching.service.PersonService;
import tr.com.khg.caching.domain.Person;
import tr.com.khg.caching.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Person}.
 */
@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    private final Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person save(Person person) {
        log.debug("Request to save Person : {}", person);
        return personRepository.save(person);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Person> findAll() {
        log.debug("Request to get all People");
        return personRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Person> findOne(Long id) {
        log.debug("Request to get Person : {}", id);
        return personRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Person : {}", id);
        personRepository.deleteById(id);
    }
}
