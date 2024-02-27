package lab;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lab.composition.PlateNumber;
import lab.inheritance.*;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory main =
                Persistence.createEntityManagerFactory("main");
        EntityManager entityManager = main.createEntityManager();

        entityManager.getTransaction().begin();

        PlateNumber plate = new PlateNumber("3030");

        Vehicle car = new Car("BMW X1", BigDecimal.TEN, "Petrol", 5, plate);
        Vehicle bike = new Bike("BMX", BigDecimal.ONE, "None");
        Vehicle plane = new Plane("Boeing", BigDecimal.TEN, "PlaneFuel", 100);
        Vehicle truck = new Truck("John Deere", BigDecimal.TEN, "Diesel", 40);

        entityManager.persist(car);
        entityManager.persist(bike);
        entityManager.persist(plane);
        entityManager.persist(truck);
        entityManager.persist(plate);

        entityManager.getTransaction().commit();

        entityManager.close();

    }
}
