package com.example.test;

import java.sql.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.JarURLConnection;

public class Utility {


    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username, String FirstName, String LastName ){
        Parent root = null;
        if (username != null && FirstName != null && LastName != null) {
            try {
                FXMLLoader loader = new FXMLLoader(Utility.class.getResource(fxmlFile));
                root = loader.load();
                LoggedInController loggedInController = loader.getController();
                loggedInController.setWelcomeLabel(username, FirstName, LastName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
               try {
                   root = FXMLLoader.load(Utility.class.getResource(fxmlFile));

               }catch (IOException e){
                   e.printStackTrace();
               }
            }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public static void signUpUser(ActionEvent event, String fxmlFile, String username, String password, String FirstName, String LastName){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try{
           //establishes connection to database of mySQL
          //arguments of getConnection: url of database, username and password
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/makerschema", "root", "Chebil03");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM userstable WHERE username = ?"); //*: all , prepared statement for security purposes
            psCheckUserExists.setString(1, username); // 1 because our '?' is the first one in the prepared statement
            resultSet = psCheckUserExists.executeQuery();
            // if resultSet empty (user doesn't exist) user can sign up with username
            if (resultSet.isBeforeFirst()){ // checks if result set is empty: will returns true if username is already taken and false if its empty
                System.out.println("User already exists");
                Alert alert = new Alert((Alert.AlertType.ERROR));
                alert.setContentText("Username already exists");
                alert.show();
            }else{
                psInsert = connection.prepareStatement("INSERT INTO userstable (username, password, FirstName, LastName) VALUES (?, ?, ?, ?)");
                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.setString(3, FirstName);
                psInsert.setString(4, LastName);
                psInsert.executeUpdate(); //update the database without returning anything

                //changeScene(event, fxmlFile, "Welcome", username, FirstName, LastName);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null){
                try{
                    resultSet.close();
                }catch ( SQLException e){
                    e.printStackTrace();
                }
            }
            if (psCheckUserExists != null){
                try{
                    psCheckUserExists.close();
                }catch ( SQLException e){
                    e.printStackTrace();
                }
            }
            if (psInsert != null){
                try{
                    psInsert.close();
                }catch ( SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection != null){
                try{
                    connection.close();
                }catch ( SQLException e){
                    e.printStackTrace();
                }
            }
        }

    }

    public static void loginUser (ActionEvent event, String username, String password){
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            try{
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/makerschema", "root", "Chebil03");
                preparedStatement = connection.prepareStatement("SELECT password, FirstName, LastName FROM userstable WHERE username = ?");
                preparedStatement.setString(1, username);
                resultSet = preparedStatement.executeQuery();
            //check if username is in database
                if (!resultSet.isBeforeFirst()){ //if username not found in database
                    System.out.println("User not found in the database");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("The provided credentials are incorrect");
                    alert.show();
                }else{ // if username is found, compare password provided by user with one in database
                    while (resultSet.next()){ // loops through resultSet and retrieve password and First/Last Names (have to do even though they are unique)
                        String retrievePassword = resultSet.getString("password");
                        String retrievedFirstName = resultSet.getString("FirstName");
                        String retrievedLastName = resultSet.getString("LastName")  ;
                    if (retrievePassword.equals(password)){
                        changeScene(event, "LoggedInPage.fxml", "Maker Menu", username, retrievedFirstName, retrievedLastName);}
                    else{
                        System.out.println("Passwords did not match");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("The provided credentials are incorrect");
                        alert.show();
                    }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (resultSet != null){
                    try{
                        resultSet.close();
                    }catch ( SQLException e){
                        e.printStackTrace();
                    }
                }
                if (preparedStatement != null){
                    try{
                        resultSet.close();
                    }catch ( SQLException e){
                        e.printStackTrace();
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
    }

}

