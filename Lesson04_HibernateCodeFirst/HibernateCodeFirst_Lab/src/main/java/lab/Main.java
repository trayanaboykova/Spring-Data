package lab;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lab.composition.Company;
import lab.composition.PlateNumber;
import lab.inheritance.*;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory main =
                Persistence.createEntityManagerFactory("main");
        EntityManager entityManager = main.createEntityManager();

        entityManager.getTransaction().begin();

         persistAll(entityManager);

        // find(entityManager);

        entityManager.getTransaction().commit();

        entityManager.close();

    }

    private static void find(EntityManager entityManager) {
        Company company = entityManager.find(Company.class, 2);

        List<Plane> planes = company.getPlanes();

        for (Plane plane : planes) {
            System.out.println(plane);
        }
    }

    private static void persistAll(EntityManager entityManager) {
        PlateNumber plate = new PlateNumber("3030");

        Company company = new Company("Bulgaria Air");

        Vehicle car = new Car("BMW X1", BigDecimal.TEN, "Petrol", 5, plate);
        Vehicle bike = new Bike("BMX", BigDecimal.ONE, "None");
        Plane plane = new Plane("Boeing", BigDecimal.TEN, "PlaneFuel", 100, company);
        Vehicle truck = new Truck("John Deere", BigDecimal.TEN, "Diesel", 40);

        entityManager.persist(company);
        entityManager.persist(car);
        entityManager.persist(bike);
        entityManager.persist(plane);
        entityManager.persist(truck);
        
    }
}
