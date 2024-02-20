import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class P07_IncreaseMinionsAge {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        DBTools dbTools = new DBTools(username, password, "minions_db");

        // Statement logic -> create query
        int[] minionIds = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        Arrays.stream(minionIds).forEach(id -> {
            String sql = "UPDATE minions SET name = LOWER(name), age = age + 1 WHERE id = ?";
            try {
                PreparedStatement preparedStatement = dbTools.getConnection().prepareStatement(sql);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        ResultSet resultSet = dbTools.getConnection().createStatement().executeQuery("SELECT name, age FROM minions");
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1) + " " + resultSet.getInt(2));
        }

    }
}
