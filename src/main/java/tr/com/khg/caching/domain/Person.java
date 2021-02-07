package tr.com.khg.caching.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Person.
 */
@Entity
@Table(name = "person")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "birthday")
    private LocalDate birthday;

    @OneToOne
    @JoinColumn(unique = true)
    private Identity identity;

    @OneToMany(mappedBy = "person")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Phone> phones = new HashSet<>();

    @OneToMany(mappedBy = "person")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PastCities> cities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Person name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public Person surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public Person birthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Identity getIdentity() {
        return identity;
    }

    public Person identity(Identity identity) {
        this.identity = identity;
        return this;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    public Person phones(Set<Phone> phones) {
        this.phones = phones;
        return this;
    }

    public Person addPhones(Phone phone) {
        this.phones.add(phone);
        phone.setPerson(this);
        return this;
    }

    public Person removePhones(Phone phone) {
        this.phones.remove(phone);
        phone.setPerson(null);
        return this;
    }

    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }

    public Set<PastCities> getCities() {
        return cities;
    }

    public Person cities(Set<PastCities> pastCities) {
        this.cities = pastCities;
        return this;
    }

    public Person addCities(PastCities pastCities) {
        this.cities.add(pastCities);
        pastCities.setPerson(this);
        return this;
    }

    public Person removeCities(PastCities pastCities) {
        this.cities.remove(pastCities);
        pastCities.setPerson(null);
        return this;
    }

    public void setCities(Set<PastCities> pastCities) {
        this.cities = pastCities;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        return id != null && id.equals(((Person) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            ", birthday='" + getBirthday() + "'" +
            "}";
    }
}
