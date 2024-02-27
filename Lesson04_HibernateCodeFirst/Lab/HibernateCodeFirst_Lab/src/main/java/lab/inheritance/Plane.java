package lab.inheritance;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class Plane extends Vehicle {
    private static final String PLANE_TYPE = "PLANE";
    @Column(name = "passenger_capacity")
    private int passengerCapacity;

    public Plane() {}
    public Plane(String model, BigDecimal price, String fuelType, int passengerCapacity) {
        super(PLANE_TYPE, model, price, fuelType);
        this.passengerCapacity = passengerCapacity;
    }
}
