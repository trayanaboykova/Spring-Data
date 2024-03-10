package bg.softuni.models;

import jakarta.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String country;
    private String city;
    @Column(name = "street_name")
    private String streetName;

    public Address() {
    }

    public Address(int id, String country, String city, String streetName) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.streetName = streetName;
    }
}
