import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class P02_GetMinionNames {
    public static void main(String[] args) throws SQLException {
        // Connect to DB
        Scanner scanner = new Scanner(System.in);
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        Properties properties = new Properties();
        properties.setProperty("user", username);
        properties.setProperty("password", password);
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", properties);

        // Statement logic -> create query
        String query = "";
        ResultSet resultSet = connection.createStatement().executeQuery(query);

        // Query output
        while (resultSet.next()) {
            System.out.printf("%s %d", resultSet.getString(1), resultSet.getInt(2)).append(System.lineSeparator());
        }
    }
}
