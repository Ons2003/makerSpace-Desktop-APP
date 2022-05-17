package com.example.ya;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static com.example.ya.ProfilePageController.DisplayAlert;

public class ProfilePage2Controller implements Initializable {


        @FXML
        private Button goHomeButton;


        @FXML
        private Button ConfidentialInfoButton;

        @FXML
        private AnchorPane LeftAnchor;

        @FXML
        private Button ProfileInformationButton;

        @FXML
        private Button SaveChangesPasswordButton;

        @FXML
        private Label UserSIDLabel;

        @FXML
        private PasswordField currentPasswordField;

        @FXML
        private PasswordField newPasswordTextField;

        @FXML
        private ImageView profilePicture;

        public void changeSceneNormal(ActionEvent event, String fxmlFile, String title) throws IOException {
                Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle(title);
                stage.setScene(new Scene(root));
                stage.show();
        }


        public void goToChangePassword(ActionEvent event) throws IOException {
                changeSceneNormal(event, "ProfilePage2.fxml", "Change Password");
        }

        public void goToProfileInfo(ActionEvent event) throws IOException{
                changeSceneNormal(event, "ProfilePage1.fxml", "Profile Information");
        }


        public void saveChanges(ActionEvent event) throws IOException{

                if (!currentPasswordField.getText().isEmpty() && !newPasswordTextField.getText().isEmpty()) {

                        String currentPassword = currentPasswordField.getText();
                        System.out.println(currentPassword);
                        String newPassword = newPasswordTextField.getText();
                        String username = "ahmed123";

                        Connection connection = null;
                        PreparedStatement psInsert = null;
                        ResultSet resultSet;
                        PreparedStatement preparedStatement = null;
                        try {
                                preparedStatement = DatabaseManager.connection.prepareStatement("SELECT * FROM accounts WHERE SID = ?");
                                preparedStatement.setString(1, username);
                                resultSet = preparedStatement.executeQuery();
                                String retrievePassword = resultSet.getString("Password");
                                System.out.println(retrievePassword);

                                if (retrievePassword.equals(currentPassword)) {
                                        // if resultSet empty (user doesn't exist) user can sign up with username
                                        psInsert = connection.prepareStatement("UPDATE accounts SET Password=? WHERE SID=?");
                                        psInsert.setString(1, newPassword);
                                        psInsert.setString(2, username);
                                        psInsert.executeUpdate();
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setHeaderText("Success");
                                        alert.setContentText("Password has been successfully changed");
                                        alert.show();
                                } else {
                                        DisplayAlert("Current password doesn't match with current profile details");
                                }

                                /*if (resultSet.equals(currentPassword)) {
                                        resultSet = preparedStatement.executeQuery();
                                        // if resultSet empty (user doesn't exist) user can sign up with username
                                        psInsert = connection.prepareStatement("UPDATE accounts SET Password=? WHERE SID=?");
                                        psInsert.setString(1, newPassword);
                                        psInsert.setString(2, username);
                                        psInsert.executeUpdate();
                                } else {
                                        DisplayAlert("Current password is false");
                                }*/
                        } catch (SQLException e) {
                                e.printStackTrace();
                        }
                } else{
                        DisplayAlert("Please fill out all the necessary information before saving");
                }

        }

        public void goToHome(ActionEvent event) throws IOException{
                Parent root = FXMLLoader.load(getClass().getResource("HomeUser.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Account Profile");
                stage.setScene(new Scene(root));
                stage.show();
        }


        public static void DisplayAlert(String content){
                System.out.println("Error");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(content);
                alert.show();
        }

        public String readPasswordFromDatabase (String username){
                String password = null;
                Connection connection = null;
                PreparedStatement psFn = null;
                ResultSet rs1 = null;

                try {
                        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/makerschema", "root", "Chebil03");
                        psFn = connection.prepareStatement("SELECT Password FROM accounts WHERE SID = ?"); //*: all , prepared statement for security purposes
                        psFn.setString(1, username);
                        rs1 = psFn.executeQuery();
                        rs1.next();
                        password = rs1.getString("Password");

                } catch (SQLException s) {
                        s.printStackTrace();
                }
                return password;
        }

        public void displayInformation(ActionEvent event, String username) throws IOException, SQLException { //Testing with Debug button
                UserSIDLabel.setText(username);
        }


        ActionEvent event;

        @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

                String username = "ahmed123";
                try {
                        displayInformation(event, username);
                } catch (IOException e) {
                        e.printStackTrace();
                } catch (SQLException e) {
                        e.printStackTrace();
                }
    }
}
