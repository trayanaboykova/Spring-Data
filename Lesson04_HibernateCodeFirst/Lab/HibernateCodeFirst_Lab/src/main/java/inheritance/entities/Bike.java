package inheritance.entities;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "bikes")
@DiscriminatorValue("OurBike")
public class Bike extends Vehicle {
    private static final String BIKE_TYPE = "BIKE";
    public Bike() {
        super(BIKE_TYPE);
    }
}
