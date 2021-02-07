package tr.com.khg.caching.service.impl;

import tr.com.khg.caching.domain.PastCities;
import tr.com.khg.caching.domain.Phone;
import tr.com.khg.caching.service.PastCitiesService;
import tr.com.khg.caching.service.PersonService;
import tr.com.khg.caching.domain.Person;
import tr.com.khg.caching.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.khg.caching.service.PhoneService;

import java.util.*;

/**
 * Service Implementation for managing {@link Person}.
 */
@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    private final Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);

    private final PersonRepository personRepository;

    private final PastCitiesService pastCitiesService;

    private final PhoneService phoneService;

    public PersonServiceImpl(PersonRepository personRepository, PastCitiesService pastCitiesService,
                             PhoneService phoneService) {
        this.personRepository = personRepository;
        this.pastCitiesService = pastCitiesService;
        this.phoneService = phoneService;
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

    @Override
    public Person saveWithRelations(Person person) {
        Person persistedPerson = personRepository.save(person);

        List<Phone> phones = new ArrayList<>(person.getPhones());
        phones.forEach(phone -> phone.setPerson(persistedPerson));
        phoneService.saveAll(phones);

        List<PastCities> cities = new ArrayList<>(person.getCities());
        cities.forEach(city -> city.setPerson(persistedPerson));
        pastCitiesService.saveAll(cities);

        return persistedPerson;
    }
}
