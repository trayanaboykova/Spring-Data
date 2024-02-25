import entities.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf =
           Persistence.createEntityManagerFactory("general");

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

    //    Teacher teacher = new Teacher();
    //    teacher.setName("Taylor");
    //    entityManager.persist(teacher);

        Teacher fromDB = entityManager.find(Teacher.class, 3);
        System.out.println(fromDB);

        entityManager.getTransaction().commit();
    }
}
