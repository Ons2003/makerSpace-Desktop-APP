package com.example.ya;

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
    public static void signUpUser(String sid, String password, String FirstName, String LastName, String email, String phoneNum){
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try{
           //establishes connection to database of mySQL
          //arguments of getConnection: url of database, username and password
            psCheckUserExists = DatabaseManager.connection.prepareStatement("SELECT * FROM accounts WHERE SID = ?"); //*: all , prepared statement for security purposes
            psCheckUserExists.setString(1, sid); // 1 because our '?' is the first one in the prepared statement
            resultSet = psCheckUserExists.executeQuery();
            // if resultSet empty (user doesn't exist) user can sign up with username
            if (resultSet.isBeforeFirst()){ // checks if result set is empty: will returns true if username is already taken and false if its empty
                DisplayAlert();
            }else{
                psInsert = DatabaseManager.connection.prepareStatement("INSERT INTO accounts (SID, Password, Email, NumTel, First, Last) VALUES (?, ?, ?, ?, ?, ?)");
                psInsert.setString(1, sid);
                psInsert.setString(2, password);
                psInsert.setString(3, email);
                psInsert.setString(4, phoneNum);
                psInsert.setString(5, FirstName);
                psInsert.setString(6, LastName);
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
        }

    }

    public static void loginUser (String SID, String password){
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            try{
                preparedStatement = DatabaseManager.connection.prepareStatement("SELECT password FROM accounts WHERE SID = ?");
                preparedStatement.setString(1, SID);
                resultSet = preparedStatement.executeQuery();
                //check if username is in database
                if (!resultSet.isBeforeFirst()){ //if username not found in database
                    DisplayAlert();
                }else{ // if username is found, compare password provided by user with one in database
                    while (resultSet.next()) { // loops through resultSet and retrieve password and First/Last Names (have to do even though they are unique)
                        String retrievePassword = resultSet.getString("password");
                        if (retrievePassword.equals(password)) {
                            AccountsManager.loggedAccount = DatabaseManager.GetAccountOfSID(SID);
                            Core.changeScene("HomeSuperUser.fxml", "Maker Menu");
                        } else {
                            DisplayAlert();
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
            }
    }

    private static void DisplayAlert(){
        System.out.println("Passwords did not match");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("The provided credentials are incorrect");
        alert.show();
    }
}

