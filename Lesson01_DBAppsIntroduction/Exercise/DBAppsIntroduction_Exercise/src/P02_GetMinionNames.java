import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class P02_GetMinionNames {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        DBTools dbTools = new DBTools(username, password, "minions_db");

        // Statement logic -> create query
        int villainId = Integer.parseInt(SCANNER.nextLine());


        String query = "SELECT name FROM villains WHERE id = ?";

        PreparedStatement preparedStatement = dbTools.getConnection().prepareStatement(query);
        preparedStatement.setInt(1, villainId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) {
            System.out.printf("No villain with ID %d exists in the database.", villainId);
            return;
        }
        String villainName = resultSet.getString("name");
        System.out.printf("Villain: %s%n", villainName);

        query = "SELECT m.name, m.age FROM minions m " +
                "JOIN minions_villains mv on m.id = mv.minion_id " +
                "WHERE villain_id = ?";

        preparedStatement = dbTools.getConnection().prepareStatement(query);
        preparedStatement.setInt(1, villainId);
        resultSet = preparedStatement.executeQuery();

        int index = 0;
        while (resultSet.next()) {
            System.out.printf("%d. %s %d",
                    ++index, resultSet.getString("name"),
                    resultSet.getInt("age"))
                    .append(System.lineSeparator());
        }
    }
}
