package com.example.ya;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ResourceBundle;
import java.net.URL;
import java.io.File;
import java.io.IOException;

public class ProfilePageController {

    //private Stage stage;
    //private Parent root;
    //private Scene scene;

    //Methods
public void changeSceneNormal(ActionEvent event, String fxmlFile, String title) throws IOException{
    Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setTitle(title);
    stage.setScene(new Scene(root));
    stage.show();
}

//Options Bar (Left Anchor)
    @FXML
    private Button ProfilePicButton;
    @FXML
    private ImageView profilePicture;
    @FXML
    private Button ProfileInformationButton;
    @FXML
    private Button ConfidentialInfoButton;
    @FXML
    private Button ActivityButton;
    @FXML
    private Button SaveChangesButton;

//Profile Information
    @FXML
    private TextField FirstNameTextField;
    @FXML
    private TextField LastNameTextField;
    @FXML
    private TextField UsernameTextField;
    @FXML
    private TextField PhoneNumberTextField;
    @FXML
    private TextField EmailTextField;

//Change Password

    @FXML
    private TextField CurrentPasswordTextField;
    @FXML
    private TextField NewPasswordTextField;



//Activity

    @FXML
    private Button DebugButton;

    public String readFirstNameFromDatabase (String username){
        String firstName = null;
        Connection connection = null;
        PreparedStatement psFn = null;
        //PreparedStatement preparedStatement = null;
        //ResultSet resultSet = null;
        ResultSet rs1 = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/makerschema", "root", "Chebil03");
            psFn = connection.prepareStatement("SELECT FirstName FROM userstable WHERE username = ?"); //*: all , prepared statement for security purposes
            psFn.setString(1, username);
            /*preparedStatement = connection.prepareStatement("SELECT * FROM userstable WHERE username = ?"); //*: all , prepared statement for security purposes
            preparedStatement.setString(1, username);
            //resultSet = preparedStatement.executeQuery();*/
            rs1 = psFn.executeQuery();
            rs1.next();
            firstName = rs1.getString("FirstName");

        } catch (SQLException s) {
            s.printStackTrace();
        }
        return firstName;
    }

    public String readLastNameFromDatabase (String username){
        String lastName = null;
        Connection connection = null;
        PreparedStatement psLn = null;
        ResultSet rs2 = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/makerschema", "root", "Chebil03");
            psLn = connection.prepareStatement("SELECT LastName FROM userstable WHERE username = ?"); //*: all , prepared statement for security purposes
            psLn.setString(1, username);
            /*preparedStatement = connection.prepareStatement("SELECT * FROM userstable WHERE username = ?"); //*: all , prepared statement for security purposes
            preparedStatement.setString(1, username);
            //resultSet = preparedStatement.executeQuery();*/
            rs2 = psLn.executeQuery();
            rs2.next();
            lastName = rs2.getString("LastName");

        } catch (SQLException s) {
            s.printStackTrace();
        }
        return lastName;
    }

    public void displayInformation(ActionEvent event) throws IOException, SQLException { //Testing with Debug button

        String username = "Youssef123"; // Testing with constant username
        String firstName = readFirstNameFromDatabase(username);
        String lastName = readLastNameFromDatabase(username);
        FirstNameTextField.setText(firstName);
        LastNameTextField.setText(lastName);
        UsernameTextField.setText(username);

    }

    public void saveChanges(ActionEvent event) throws IOException{

        if (FirstNameTextField != null && LastNameTextField != null && UsernameTextField != null){

            String firstName = FirstNameTextField.getText();
            String lastName = FirstNameTextField.getText();
            String username = "Youssef123";

            Connection connection = null;
            PreparedStatement psInsert = null;
            PreparedStatement psCheckUserExists = null;
            ResultSet resultSet = null;

            try{
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/makerschema", "root", "Chebil03");
                psCheckUserExists = connection.prepareStatement("SELECT * FROM userstable WHERE username = ?"); //*: all , prepared statement for security purposes
                psCheckUserExists.setString(1, username); // 1 because our '?' is the first one in the prepared statement
                resultSet = psCheckUserExists.executeQuery();
                // if resultSet empty (user doesn't exist) user can sign up with username
                    psInsert = connection.prepareStatement("INSERT INTO userstable (FirstName, LastName) VALUES (?, ?) WHERE username = ?");
                    psInsert.setString(1, firstName);
                    psInsert.setString(2, lastName);
                    psInsert.setString(3, username);
                    psInsert.executeUpdate(); //update the database without returning anything

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill out all the necessary information before saving");
            alert.show();
        }


    }



    public void updateProfilePic(ActionEvent event) throws IOException{

        final FileChooser fc = new FileChooser();
        fc.setTitle("My File Chooser");
        //Set the initial directory for displayed file dialog (user home refers to the path to the user directory)
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        //set extension filters used in displayed file dialog (removing elements from list first)
        fc.getExtensionFilters().clear();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image files", "*.png*", "*.jpg*", "*.gif*"));
        //set selected file or null if no file if no file has been selected
        File file = fc.showOpenDialog(null);

        if (file!= null){
            profilePicture.setImage(new Image(String.valueOf(file.toURI())));
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("The entered type of file is invalid");
            alert.setContentText("You must load a file from the type .img, .jpg, or .gif");
            alert.show();
        }

    }


    public void goToChangePassword(ActionEvent event) throws IOException{
        changeSceneNormal(event, "ProfileChangePassword.fxml", "Change Password");
    }

    public void goToActivity(ActionEvent event) throws IOException{
        changeSceneNormal(event, "ProfileActivity.fxml", "Activity");
    }

    public void goToProfileInfo(ActionEvent event) throws IOException{
        changeSceneNormal(event, "ProfilePage.fxml", "Profile Information");
    }
}
