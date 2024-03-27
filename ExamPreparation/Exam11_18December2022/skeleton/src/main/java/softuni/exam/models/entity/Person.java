package softuni.exam.models.entity;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "people")
public class Person extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(unique = true)
    private String phone;
    @Column(name = "status_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private StatusType statusType;
    @ManyToOne
    @JoinColumn(name = "country_id",
            referencedColumnName = "id",
            nullable = false)
    private Country country;

    public Person() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public StatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
