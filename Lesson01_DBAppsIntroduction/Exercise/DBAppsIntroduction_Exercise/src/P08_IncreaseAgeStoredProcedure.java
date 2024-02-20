import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class P08_IncreaseAgeStoredProcedure {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        DBTools dbTools = new DBTools(username, password, "minions_db");

        // Statement logic -> create query
        CallableStatement callableStatement = dbTools.getConnection().prepareCall("CALL usp_get_older(?)");
        callableStatement.setInt(1, Integer.parseInt(scanner.nextLine()));
        ResultSet resultSet = callableStatement.executeQuery();
        resultSet.next();
        System.out.println(resultSet.getString(1) + " " + resultSet.getInt(2));
    }
}
