package inheritance.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "cars")
@DiscriminatorValue("OurCar")
public class Car extends Vehicle {
    private static final String CAR_TYPE = "CAR";
    public Car() {
        super(CAR_TYPE);
    }
}
