package inheritance;

import inheritance.entities.Bike;
import inheritance.entities.Car;
import inheritance.entities.Vehicle;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory mainFactory =
                Persistence.createEntityManagerFactory("main");
        EntityManager entityManager = mainFactory.createEntityManager();

        entityManager.getTransaction().begin();

        Vehicle car = new Car();
        Vehicle bike = new Bike();

        entityManager.persist(car);
        entityManager.persist(bike);

        entityManager.getTransaction().commit();
    }
}
