import entities.Order;
import entities.Product;
import entities.User;
import orm.EntityManager;
import orm.MyConnector;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        MyConnector.createConnection(username, password, "mini_orm");
        Connection connection = MyConnector.getConnection();

        //  EntityManager<User> userEntityManager = new EntityManager<>(connection);
        //  User taylor = userEntityManager.findFirst(User.class);
        //  userEntityManager.delete(taylor);

        //  EntityManager<User> userEntityManager = new EntityManager<>(connection);
        //  User taylor = new User("taylor", 33, LocalDate.now());
        //  taylor.setEmail("taylornation@taylor.com");
        //  userEntityManager.doAlter(taylor);
        //  userEntityManager.persist(taylor);

        // EntityManager<User> userEntityManager = new EntityManager<>(connection);
        // userEntityManager.doCreate(User.class);
        // EntityManager<User> userEntityManager = new EntityManager<>(connection);
        // User taylor = new User("taylor", 33, LocalDate.now());
        // userEntityManager.persist(taylor);
        // User taylor = new User("taylor", 33, LocalDate.now());
        // taylor.setId(1);
        // userEntityManager.persist(taylor);
        // Iterable<User> users = userEntityManager.find(User.class);
        // System.out.println(users.iterator().next());


        //  EntityManager<Order> orderEntityManager = new EntityManager<>(connection);
        //  orderEntityManager.doCreate(Order.class);
        // EntityManager<Order> orderEntityManager = new EntityManager<>(connection);
        // Order tay13 = new Order("tay13", LocalDate.now());
        // orderEntityManager.persist(tay13);


        //    EntityManager<Product> productEntityManager = new EntityManager<>(connection);
        //    Product song = new Product("willow", 1);
        //    productEntityManager.persist(song);


    }
}
