package inheritance.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "cars")
@DiscriminatorValue("OurCar")
public class CarDemo extends VehicleDemo {
    private static final String CAR_TYPE = "CAR";
    public CarDemo() {
        super(CAR_TYPE);
    }
}
