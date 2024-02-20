import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class P05_RemoveVillains {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        DBTools dbTools = new DBTools(username, password, "minions_db");

        // Statement logic -> create query
        int villainId = Integer.parseInt(scanner.nextLine());
        String villainName = findVillainById(villainId, dbTools);
        if (villainName.isEmpty()) {
            System.out.println("No such villain was found.");
        } else {
            int releasedMinionsCount = releaseMinions(villainId, dbTools);
            deleteVillain(villainId, dbTools);
            System.out.printf("%s was deleted%n%d minions released", villainName, releasedMinionsCount);
        }

    }

    private static void deleteVillain(int villainId, DBTools dbTools) throws SQLException {
        PreparedStatement preparedStatement = dbTools.getConnection().prepareStatement("DELETE FROM villains WHERE id = ?");
        preparedStatement.setInt(1, villainId);
        preparedStatement.executeUpdate();
    }

    private static int releaseMinions(int villainId, DBTools dbTools) throws SQLException {
        PreparedStatement preparedStatement = dbTools.getConnection().prepareStatement("DELETE FROM minions_villains WHERE villain_id = ?");
        preparedStatement.setInt(1, villainId);
        return preparedStatement.executeUpdate();
    }

    private static String findVillainById(int villainId, DBTools dbTools) throws SQLException {
        PreparedStatement preparedStatement = dbTools.getConnection().prepareStatement
                ("SELECT name FROM villains WHERE id = ?");
        preparedStatement.setInt(1, villainId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("name");
        }
        return "";
    }
}
