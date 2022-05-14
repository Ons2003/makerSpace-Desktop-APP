package src;
import java.sql.Connection;


import java.sql.* ;

public class DatabaseConnection {
    Connection connection ;
    private static final String databaseName = "makerspace";
    private static final String databaseUser = "root";
    private static final String databasePass = "education";
    private static final String url = "jdbc:mysql://localhost:3306/" + databaseName + "?useSSL=false";

    public void connect() {
        try {
            connection = DriverManager.getConnection(url, databaseUser, databasePass);
            System.out.println("connected to data base");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
