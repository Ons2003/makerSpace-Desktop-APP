package com.example.ya;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    public static String url = "jdbc:mysql://localhost:3306/makerschema";
    private String username = "root";
    private String password = "Chebil03";
    public static Connection connection;

    public void Initialize() {
        try {
            connection = DriverManager.getConnection(url, username, password);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<TimeEvent> RetrieveTimetableEvents(Timetable timetable) throws SQLException {
        List<TimeEvent> events = new ArrayList<TimeEvent>();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from " + timetable.dataBaseID);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String topic = resultSet.getString("topic");
            String creatorSID = resultSet.getString("creatorSID");
            String startDateEncoded = resultSet.getString("startDate");
            String endDateEncoded = resultSet.getString("endDate");
            String creationDateEncoded = resultSet.getString("creationDate");
            int accessPermission = resultSet.getInt("accessPermission");
            String description = resultSet.getString("description");
            int columnID = resultSet.getInt(("columnID"));

            TimeEvent timeEvent = new TimeEvent(columnID, name, Instant.Decode(startDateEncoded), Instant.Decode(endDateEncoded),
                    topic, description, Instant.Decode(creationDateEncoded),
                    creatorSID, AccountsManager.DecodeAccessPermission(accessPermission));
            timeEvent.id = id;
            events.add(timeEvent);
        }
        statement.close();
        return events;
    }

    public static void RemoveTimetableEvent(String timetableDBId, int eventId) {
        String query = "delete from " + timetableDBId + " where id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, eventId);
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void EditTimetableEvent(Timetable timetable, TimeEvent event) {
        String query = "UPDATE " + timetable.dataBaseID + " SET name=?, topic=?, creatorSID=?, columnID=?, startDate=?, endDate=?, " +
                "creationDate=?, description=?, accessPermission=? WHERE id=?";
        try {
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, event.name);
            statement.setString(2, event.topic);
            statement.setString(3, event.creation.creator.SID);
            statement.setInt(4, event.columnID);
            statement.setString(5, event.start.Encode());
            statement.setString(6, event.end.Encode());
            statement.setString(7, event.creationDate.Encode());
            statement.setString(8, event.description);
            statement.setInt(9, AccountsManager.EncodeAccessPermission(event.creation.accessPermission));
            statement.setInt(10, event.id);
            System.out.println("Targetting " + event.id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void SaveTimetableEvent(TimeEvent event, Timetable timetable) {
        String insertQuery = "INSERT INTO " + timetable.dataBaseID +
                "(name, topic, creatorSID, columnID, startDate, endDate, creationDate, description, accessPermission) VALUES(?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, event.name);
            insertStatement.setString(2, event.topic);
            System.out.println("This event was created by " + event.creation.creator.SID);
            insertStatement.setString(3, event.creation.creator.SID);
            insertStatement.setInt(4, event.columnID);
            insertStatement.setString(5, event.start.Encode());
            insertStatement.setString(6, event.end.Encode());
            insertStatement.setString(7, event.creationDate.Encode());
            insertStatement.setString(8, event.description);
            insertStatement.setInt(9, AccountsManager.EncodeAccessPermission(event.creation.accessPermission));
            int affectedRows = insertStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = insertStatement.getGeneratedKeys()) {
                    long id = 0;
                    if (rs.next()) id = rs.getLong(1);
                    event.id = (int) id;
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static Account GetAccountOfSID(String SID) {
        Account target = new Account();
        target.SID = SID;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = DatabaseManager.connection.prepareStatement("SELECT * FROM accounts WHERE SID = ?");
            preparedStatement.setString(1, SID);
            resultSet = preparedStatement.executeQuery();
            //check if username is in database
            if (resultSet.isBeforeFirst()) { // if username is found, compare password provided by user with one in database
                while (resultSet.next()) { // loops through resultSet and retrieve password and First/Last Names (have to do even though they are unique)
                    target.first = resultSet.getString("First");
                    target.last = resultSet.getString("Last");
                    target.email = resultSet.getString("Email");
                    target.phoneNumber = resultSet.getString("NumTel");
                    target.role = Account.DecodeRole(resultSet.getInt("Role"));
                    target.password = resultSet.getString("Password");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return target;
    }
}
