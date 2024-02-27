package inheritance.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "cars")
public class Car extends Vehicle {
    private static final String CAR_TYPE = "CAR";
    public Car() {
        super(CAR_TYPE);
    }
}
