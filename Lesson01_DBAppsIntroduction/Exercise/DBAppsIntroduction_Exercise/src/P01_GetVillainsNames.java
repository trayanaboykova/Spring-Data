import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class P01_GetVillainsNames {
    public static void main(String[] args) throws SQLException {
        // Connect to DB
        Scanner scanner = new Scanner(System.in);
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        DBTools dbTools = new DBTools(username, password, "minions_db");

        // Statement logic -> create query
        String query =
                "SELECT v.name, COUNT(mv.minion_id) AS count " +
                "FROM villains v " +
                "JOIN minions_villains mv on v.id = mv.villain_id " +
                "GROUP BY v.name " +
                "HAVING count > 15 " +
                "ORDER BY count DESC;";
        ResultSet resultSet = dbTools.getConnection().createStatement().executeQuery(query);

        // Query output
        while (resultSet.next()){
            System.out.printf("%s %d",resultSet.getString(1), resultSet.getInt(2)).append(System.lineSeparator());
        }
    }
}