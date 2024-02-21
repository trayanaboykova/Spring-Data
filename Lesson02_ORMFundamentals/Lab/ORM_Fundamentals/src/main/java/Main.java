import entities.Product;
import entities.User;
import orm.EntityManager;
import orm.MyConnector;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, IllegalAccessException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        MyConnector.createConnection(username, password, "mini_orm");
        Connection connection = MyConnector.getConnection();

        EntityManager<User> userEntityManager = new EntityManager<>(connection);
        User taylor = new User("taylor", 13, LocalDate.now());
        userEntityManager.persist(taylor);

    //    EntityManager<Product> productEntityManager = new EntityManager<>(connection);
    //    Product song = new Product("willow", 1);
    //    productEntityManager.persist(song);
    }
}
