import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("main");
    //   EntityManager entityManager = entityManagerFactory.createEntityManager();

    //   entityManager.getTransaction().begin();
    //
    //   entityManager.getTransaction().commit();
    }
}
