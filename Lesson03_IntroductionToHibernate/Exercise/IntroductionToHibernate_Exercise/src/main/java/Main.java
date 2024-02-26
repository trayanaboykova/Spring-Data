import entities.Employee;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("softuni_jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        ex01_ChangeCasing(entityManager, entityManagerFactory);

        ex02_ContainsEmployee(entityManager);
    }

    private static void ex02_ContainsEmployee(EntityManager entityManager) throws IOException {
        String[] input = READER.readLine().split("\\s+");

        List<Employee>resultList = entityManager
                .createQuery("FROM Employee  WHERE firstName = :first_name AND lastName = :last_name", Employee.class)
                .setParameter("first_name", input[0])
                .setParameter("last_name", input[1])
                .getResultList();

        System.out.println(resultList.size() > 0 ? "Yes" : "No");
    }

    private static void ex01_ChangeCasing(EntityManager entityManager, EntityManagerFactory entityManagerFactory) {
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

