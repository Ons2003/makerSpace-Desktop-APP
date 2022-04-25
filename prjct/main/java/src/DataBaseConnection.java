package src;

import java.sql.Connection ;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    private static final String databaseName = "makerspace";
    private static final String databaseUser = "root";
    private static final String databasePass = "education";
    private static final String url = "jdbc:mysql://localhost:3306/" + databaseName + "?useSSL=false";

    public void insertRecord() throws SQLException {

        // Step 1: Establishing a Connection and
        // try-with-resource statement will auto close the connection.
        try (Connection connection = DriverManager
                .getConnection(url, databaseUser, databasePass);) {
                System.out.println("conected");
            // Step 2:Create a statement using connection object
//             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
//            preparedStatement.setString(1, fullName);
//            preparedStatement.setString(2, emailId);
//            preparedStatement.setString(3, password);
//
//            System.out.println(preparedStatement);
//            // Step 3: Execute the query or update query
//            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // print SQL exception information
            System.out.println(e);
        }
    }
}
