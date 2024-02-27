package inheritanceDemo.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "bikes")
@DiscriminatorValue("OurBike")
public class BikeDemo extends VehicleDemo {
    private static final String BIKE_TYPE = "BIKE";
    public BikeDemo() {
        super(BIKE_TYPE);
    }
}
