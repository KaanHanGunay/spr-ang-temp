package tr.com.khg.caching.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A PastCities.
 */
@Entity
@Table(name = "past_cities")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PastCities extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city")
    private String city;

    @Column(name = "start_year")
    private Integer startYear;

    @Column(name = "end_year")
    private Integer endYear;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Person person;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public PastCities city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public PastCities startYear(Integer startYear) {
        this.startYear = startYear;
        return this;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public PastCities endYear(Integer endYear) {
        this.endYear = endYear;
        return this;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    public Person getPerson() {
        return person;
    }

    public PastCities person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PastCities)) {
            return false;
        }
        return id != null && id.equals(((PastCities) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PastCities{" +
            "id=" + getId() +
            ", city='" + getCity() + "'" +
            ", startYear=" + getStartYear() +
            ", endYear=" + getEndYear() +
            "}";
    }
}
