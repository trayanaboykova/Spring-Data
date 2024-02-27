package lab.inheritance;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class Truck extends Vehicle {
    private static final String TRUCK_TYPE = "TRUCK";
    @Column(name = "load_capacity")
    private double loadCapacity;

    public Truck() {}

    public Truck(String model, BigDecimal price, String fuelType, double loadCapacity) {
        super(TRUCK_TYPE, model, price, fuelType);
        this.loadCapacity = loadCapacity;
    }
}
