import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class P03_AddMinion {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        DBTools dbTools = new DBTools(username, password, "minions_db");

        // Statement logic -> create query
        String[] minionsTokens = scanner.nextLine().split("\\s+");
        String minionName = minionsTokens[1];
        int minionAge = Integer.parseInt(minionsTokens[2]);
        String townName = minionsTokens[3];
        String villainName = scanner.nextLine().split("\\s+")[1];

        int townId = findTownIdByName(townName, dbTools);
        if (townId == 0) {
            townId = createTown(townName, dbTools);
            System.out.printf("Town %s was added to the database.%n", townName);
        }

        int minionId = createMinion(minionName, minionAge, townId, dbTools);
        int villainId = findVillainByName(villainName, dbTools);
        if (villainId == 0){
            villainId = createVillain(villainName, dbTools);
            System.out.printf("Villain %s was added to the database.%n", villainName);
        }

        populateMinionsVillains(minionId, villainId, dbTools);
        System.out.printf("Successfully added %s to be minion of %s.", minionName, villainName);
    }

    private static void populateMinionsVillains(int minionId, int villainId, DBTools dbTools) throws SQLException {
        PreparedStatement preparedStatement = dbTools.getConnection().prepareStatement("INSERT INTO minions_villains VALUE (?, ?)");
        preparedStatement.setInt(1, minionId);
        preparedStatement.setInt(2, villainId);
        preparedStatement.executeUpdate();
    }

    private static int createVillain(String villainName, DBTools dbTools) throws SQLException {
        PreparedStatement preparedStatement = dbTools.getConnection().prepareStatement("INSERT INTO villains(name, evilness_factor) VALUE(?, 'evil')");
        preparedStatement.setString(1, villainName);
        preparedStatement.executeUpdate();

        preparedStatement = dbTools.getConnection().prepareStatement("SELECT id FROM villains WHERE NAME = ?");
        preparedStatement.setString(1, villainName);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("id");
    }

    private static int findVillainByName(String villainName, DBTools dbTools) throws SQLException {
        PreparedStatement preparedStatement = dbTools.getConnection().prepareStatement("SELECT id FROM villains WHERE name = ?");
        preparedStatement.setString(1, villainName);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            return resultSet.getInt("id");
        }
        return 0;
    }

    private static int createMinion(String minionName, int minionAge, int townId, DBTools dbTools) throws SQLException {
        PreparedStatement preparedStatement = dbTools.getConnection().prepareStatement("INSERT INTO minions(name, age, town_id) VALUE(?, ?, ?)");
        preparedStatement.setString(1, minionName);
        preparedStatement.setInt(2, minionAge);
        preparedStatement.setInt(3, townId);
        preparedStatement.executeUpdate();

        preparedStatement = dbTools.getConnection().prepareStatement("SELECT id FROM minions WHERE name = ?");
        preparedStatement.setString(1, minionName);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();


        return resultSet.getInt("id");
    }

    private static int createTown(String townName, DBTools dbTools) throws SQLException {
        PreparedStatement preparedStatement = dbTools.getConnection().prepareStatement("INSERT INTO towns(name) VALUE(?)");
        preparedStatement.setString(1, townName);
        // preparedStatement.execute();
        // preparedStatement.executeQuery(); // ResultSet
        preparedStatement.executeUpdate(); // returns count updated rows

        preparedStatement = dbTools.getConnection().prepareStatement("SELECT id FROM towns WHERE name = ?");
        preparedStatement.setString(1, townName);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("id");
    }

    private static Integer findTownIdByName(String townName, DBTools dbTools) throws SQLException {
        PreparedStatement preparedStatement = dbTools.getConnection().prepareStatement("SELECT id FROM towns WHERE name =?");
        preparedStatement.setString(1, townName);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("id");
        }
        return 0;
    }
}
