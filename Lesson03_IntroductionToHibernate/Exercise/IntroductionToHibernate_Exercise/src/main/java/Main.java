import entities.*;

import javax.persistence.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("softuni_jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        // ex01_ChangeCasing(entityManager);
        // ex02_ContainsEmployee(entityManager);
        // ex03_EmployeesWithASalaryOver50000(entityManager);
        // ex04_EmployeesFromDepartment(entityManager);
        // ex05_AddingANewAddressAndUpdatingTheEmployee(entityManager);
        // ex06_AddressesWithEmployeeCount(entityManager);
        // ex07_GetEmployeesWithProject(entityManager);
        // ex08_FindTheLatest10Projects(entityManager);
        // ex09_IncreaseSalaries(entityManager);
        // ex10_FindEmployeesByFirstName(entityManager);
        // ex11_EmployeesMaximumSalaries(entityManager);
        // ex12_RemoveTowns(entityManager);

        entityManager.getTransaction().commit();
    }

    private static void ex12_RemoveTowns(EntityManager entityManager) throws IOException {
        List<Town> resultList = entityManager.createQuery("FROM Town WHERE name = :name", Town.class)
                .setParameter("name", READER.readLine())
                .getResultList();

        if (!resultList.isEmpty()) {
            Town town = resultList.get(0);
            List<Address> addresses = entityManager.createQuery("SELECT a FROM Address a JOIN a.town t WHERE t.name = :name", Address.class)
                    .setParameter("name", town.getName())
                    .getResultList();
            addresses.forEach(a -> {
                a.getEmployees().forEach(e -> {
                    e.setAddress(null);
                    entityManager.persist(e);
                });
                entityManager.remove(a);
            });

            System.out.printf("%d addresses in %s deleted",
                    addresses.size(),
                    town.getName());
            entityManager.remove(town);
        }
    }

    private static void ex11_EmployeesMaximumSalaries(EntityManager entityManager) {
        List<Department> departments = entityManager.createQuery("FROM Department", Department.class).getResultList();
        departments.forEach(d -> {
            double departmentMaxSalary = d.getEmployees()
                    .stream()
                    .mapToDouble(e -> e.getSalary().doubleValue())
                    .max().orElse(0);
            if (departmentMaxSalary < 30000 || departmentMaxSalary > 70000) {
                System.out.printf("%s %.2f%n", d.getName(), departmentMaxSalary);
            }
        });
    }

    private static void ex10_FindEmployeesByFirstName(EntityManager entityManager) throws IOException {
        entityManager.createQuery("FROM Employee  WHERE firstName LIKE CONCAT (:letters, '%')", Employee.class)
                .setParameter("letters", READER.readLine())
                .getResultStream()
                .forEach(e -> System.out.printf("%s %s - %s - ($%.2f)%n",
                        e.getFirstName(),
                        e.getLastName(),
                        e.getJobTitle(),
                        e.getSalary()));
    }

    private static void ex09_IncreaseSalaries(EntityManager entityManager) {
        List<Employee> employees = entityManager.createQuery("SELECT e FROM Employee  e JOIN e.department d " +
                        "WHERE d.name IN ('Engineering', 'Tool Design', 'Marketing', 'Information Services')", Employee.class)
                .getResultList();

        for (Employee employee : employees) {
            employee.setSalary(employee.getSalary().multiply(BigDecimal.valueOf(1.12)));
            entityManager.persist(employee);
            System.out.printf("%s %s ($%.2f)%n",
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getSalary());
        }
    }

    private static void ex08_FindTheLatest10Projects(EntityManager entityManager) {
        entityManager.createQuery("FROM Project ORDER BY startDate DESC, name", Project.class)
                .setMaxResults(10)
                .getResultStream()
                .forEach(p -> System.out.printf("Project name: %s%n" +
                                "   Project Description: %s%n" +
                                "   Project Start Date: %s%n" +
                                "   Project End Date: %s%n",
                        p.getName(),
                        p.getDescription(),
                        p.getStartDate(),
                        p.getEndDate()));
    }

    private static void ex07_GetEmployeesWithProject(EntityManager entityManager) throws IOException {
        Employee employee = entityManager
                .createQuery("FROM Employee e WHERE e.id = ?1", Employee.class)
                .setParameter(1, Integer.parseInt(READER.readLine()))
                .getSingleResult();
        System.out.printf("%s %s - %s%n",
                employee.getFirstName(),
                employee.getLastName(),
                employee.getJobTitle());
        employee.getProjects()
                .stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(p -> System.out.printf("   %s%n", p.getName()));
    }

    private static void ex06_AddressesWithEmployeeCount(EntityManager entityManager) {
        entityManager.createQuery("FROM Address ORDER BY employees.size DESC", Address.class)
                .setMaxResults(10)
                .getResultStream()
                .forEach(a -> System.out.printf("%s, %s - %d employees%n",
                        a.getText(),
                        a.getTown().getId(),
                        a.getEmployees().size()));
    }

    private static void ex05_AddingANewAddressAndUpdatingTheEmployee(EntityManager entityManager) throws IOException {
        Town town = entityManager.find(Town.class, 32);
        Address address = new Address();
        address.setText("Vitoshka 15");
        address.setTown(town);
        entityManager.persist(address);

        String lastName = READER.readLine();
        List<Employee> resultList = entityManager.createQuery("FROM Employee WHERE lastName = :lastName", Employee.class)
                .setParameter("lastName", lastName)
                .getResultList();

        if (!resultList.isEmpty()) {
            Employee employee = resultList.get(0);
            employee.setAddress(address);
            entityManager.persist(employee);
        }
    }

    private static void ex04_EmployeesFromDepartment(EntityManager entityManager) {
        entityManager.createQuery("SELECT e FROM Employee e JOIN e.department d WHERE d.id=6 ORDER BY e.salary, e.id", Employee.class)
                .getResultStream()
                .forEach(e -> System.out.printf("%s %s from Research and Development - $%.2f%n",
                        e.getFirstName(),
                        e.getLastName(),
                        e.getSalary()));
    }

    private static void ex03_EmployeesWithASalaryOver50000(EntityManager entityManager) {
        entityManager.createQuery("FROM Employee WHERE salary > 50000", Employee.class)
                .getResultStream()
                .map(Employee::getFirstName)
                .forEach(System.out::println);
    }

    private static void ex02_ContainsEmployee(EntityManager entityManager) throws IOException {
        String[] input = READER.readLine().split("\\s+");

        List<Employee> resultList = entityManager
                .createQuery("FROM Employee  WHERE firstName = :first_name AND lastName = :last_name", Employee.class)
                .setParameter("first_name", input[0])
                .setParameter("last_name", input[1])
                .getResultList();

        System.out.println(resultList.size() > 0 ? "Yes" : "No");
    }

    private static void ex01_ChangeCasing(EntityManager entityManager) {
        List<Town> resultList = entityManager.createQuery("FROM Town WHERE LENGTH(name) > 5", Town.class).getResultList();

        resultList.forEach(town -> {
            town.setName(town.getName().toUpperCase());
            entityManager.persist(town);
        });

        System.out.println();
    }
}

