package tr.com.khg.caching.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Identity.
 */
@Entity
@Table(name = "identity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Identity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "identity_number")
    private String identityNumber;

    @Column(name = "mother_name")
    private String motherName;

    @Column(name = "father_name")
    private String fatherName;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public Identity identityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
        return this;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getMotherName() {
        return motherName;
    }

    public Identity motherName(String motherName) {
        this.motherName = motherName;
        return this;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public Identity fatherName(String fatherName) {
        this.fatherName = fatherName;
        return this;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Identity)) {
            return false;
        }
        return id != null && id.equals(((Identity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Identity{" +
            "id=" + getId() +
            ", identityNumber='" + getIdentityNumber() + "'" +
            ", motherName='" + getMotherName() + "'" +
            ", fatherName='" + getFatherName() + "'" +
            "}";
    }
}
