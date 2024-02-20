import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Scanner;

public class P06_PrintAllMinionNames {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        DBTools dbTools = new DBTools(username, password, "minions_db");

        // Statement logic -> create query
        ResultSet resultSet = dbTools.getConnection().createStatement().executeQuery("SELECT name FROM minions");

        ArrayDeque<String> deque = new ArrayDeque<>();

        while (resultSet.next()) {
            deque.add(resultSet.getString(1));
        }

        while (!deque.isEmpty()) {
            System.out.println(deque.removeFirst());
            if (!deque.isEmpty()) {
                System.out.println(deque.removeLast());
            }
        }
    }
}
