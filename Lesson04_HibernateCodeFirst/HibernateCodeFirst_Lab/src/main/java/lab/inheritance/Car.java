package lab.inheritance;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class Car extends Vehicle {
    private static final String CAR_TYPE = "CAR";
    @Basic
    private int seats;

    public Car() {}

    public Car(String model, BigDecimal price, String fuelType, int seats) {
        super(CAR_TYPE, model, price, fuelType);
        this.seats = seats;
    }
}
