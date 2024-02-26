import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class P01_ChangeCasing {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("softuni_jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        // Fetch all towns from the database
        TypedQuery<Town> query = entityManager.createQuery("SELECT t FROM Town t", Town.class);
        List<Town> towns = query.getResultList();

        // Detach towns with a name length more than 5 symbols
        for (Town town : towns) {
            if (town.getName().length() > 5) {
                entityManager.detach(town);
            } else {
                // Transform the names of attached towns to uppercase
                town.setName(town.getName().toUpperCase());
            }
        }

        // Persist the changes
        for (Town town : towns) {
            if (!entityManager.contains(town)) {
                entityManager.merge(town);
            }
        }

        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();
    }
}

