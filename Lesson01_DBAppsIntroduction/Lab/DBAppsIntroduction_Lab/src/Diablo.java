import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Diablo {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        // Connect to DB
        Properties credentials = new Properties();
        credentials.setProperty("user", username);
        credentials.setProperty("password", password);
        Connection connection = DriverManager.getConnection
                ("jdbc:mysql://localhost:3306/diablo", credentials);

        System.out.println("Enter Username: ");
        String user = scanner.nextLine();

        // Execute Query
        PreparedStatement preparedStatement = connection.prepareStatement
                ("SELECT u.first_name, u.last_name, COUNT(ug.game_id) AS game_count " +
                        "FROM users AS u " +
                        "JOIN users_games AS ug ON ug.user_id = u.id " +
                        "WHERE u.user_name = ? " +
                        "GROUP BY u.first_name, u.last_name;");
        preparedStatement.setString(1, user);
        ResultSet result = preparedStatement.executeQuery();

        // Print Result
        if (result.next()) { // has valid user data
            System.out.printf("User: %s%n %s %s has played %d games",
                    user,
                    result.getString(1),
                    result.getString(2),
                    result.getInt(3));
        } else { // has no user data
            System.out.println("No such user exists");
        }

    }
}
