package inheritanceDemo;

import inheritanceDemo.entities.BikeDemo;
import inheritanceDemo.entities.CarDemo;
import inheritanceDemo.entities.VehicleDemo;
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
