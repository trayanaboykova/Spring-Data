package inheritance;

import inheritance.entities.BikeDemo;
import inheritance.entities.CarDemo;
import inheritance.entities.VehicleDemo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class MainDemo {
    public static void main(String[] args) {
        EntityManagerFactory mainFactory =
                Persistence.createEntityManagerFactory("main");
        EntityManager entityManager = mainFactory.createEntityManager();

        entityManager.getTransaction().begin();

        VehicleDemo car = new CarDemo();
        VehicleDemo bike = new BikeDemo();

        entityManager.persist(car);
        entityManager.persist(bike);

        entityManager.getTransaction().commit();
    }
}
