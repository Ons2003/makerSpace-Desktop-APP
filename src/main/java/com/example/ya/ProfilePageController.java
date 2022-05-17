package com.example.ya;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ResourceBundle;
import java.net.URL;
import java.io.File;
import java.io.IOException;


public class ProfilePageController implements Initializable {

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
    private Button SaveChangesButton;
    @FXML
    private Button SaveChangesPasswordButton;
    @FXML
    private Button goHomeButton;

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
    private Label UserSIDLabel;


//Activity

    public String readFirstNameFromDatabase (String username){
        String firstName = null;
        Connection connection = null;
        PreparedStatement psFn = null;
        ResultSet rs1 = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/makerschema", "root", "Chebil03");
            psFn = connection.prepareStatement("SELECT First FROM accounts WHERE SID = ?"); //*: all , prepared statement for security purposes
            psFn.setString(1, username);
            rs1 = psFn.executeQuery();
            rs1.next();
            firstName = rs1.getString("First");

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
            psLn = connection.prepareStatement("SELECT Last FROM accounts WHERE SID = ?"); //*: all , prepared statement for security purposes
            psLn.setString(1, username);
            rs2 = psLn.executeQuery();
            rs2.next();
            lastName = rs2.getString("Last");

        } catch (SQLException s) {
            s.printStackTrace();
        }
        return lastName;
    }

    public String readEmailFromDatabase (String username){
        String email = null;
        Connection connection = null;
        PreparedStatement psLn = null;
        ResultSet rs2 = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/makerschema", "root", "Chebil03");
            psLn = connection.prepareStatement("SELECT Email FROM accounts WHERE SID = ?"); //*: all , prepared statement for security purposes
            psLn.setString(1, username);
            rs2 = psLn.executeQuery();
            rs2.next();
            email = rs2.getString("Email");

        } catch (SQLException s) {
            s.printStackTrace();
        }
        return email;
    }

    public String readNumTelFromDatabase (String username){
        String numTel = null;
        Connection connection = null;
        PreparedStatement psLn = null;
        ResultSet rs2 = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/makerschema", "root", "Chebil03");
            psLn = connection.prepareStatement("SELECT NumTel FROM accounts WHERE SID = ?");
            psLn.setString(1, username);
            rs2 = psLn.executeQuery();
            rs2.next();
            numTel = rs2.getString("NumTel");

        } catch (SQLException s) {
            s.printStackTrace();
        }
        return numTel;
    }


    public void displayInformation(ActionEvent event) throws IOException, SQLException { //Testing with Debug button
        String username = "ahmed123";
        String firstName = readFirstNameFromDatabase(username);
        String lastName = readLastNameFromDatabase(username);
        String email = readEmailFromDatabase(username);
        String numTel = readNumTelFromDatabase(username);
        FirstNameTextField.setText(firstName);
        LastNameTextField.setText(lastName);
        EmailTextField.setText(email);
        PhoneNumberTextField.setText(numTel);
        UserSIDLabel.setText(username);
    }


    public void displayInformation(ActionEvent event, String username) throws IOException, SQLException { //Testing with Debug button

        String firstName = readFirstNameFromDatabase(username);
        String lastName = readLastNameFromDatabase(username);
        String email = readEmailFromDatabase(username);
        String numTel = readNumTelFromDatabase(username);
        FirstNameTextField.setText(firstName);
        LastNameTextField.setText(lastName);
        EmailTextField.setText(email);
        PhoneNumberTextField.setText(numTel);
        UserSIDLabel.setText(username);
    }

    public void saveChanges(ActionEvent event) throws IOException{

        if (FirstNameTextField != null && LastNameTextField != null){

            String firstName = FirstNameTextField.getText();
            String lastName = LastNameTextField.getText();
            String email = EmailTextField.getText();
            String phoneNumber = PhoneNumberTextField.getText();
            String username = "ahmed123";

            Connection connection = null;
            PreparedStatement psInsert = null;
            PreparedStatement psCheckUserExists = null;
            ResultSet resultSet = null;

            try{
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/makerschema", "root", "Chebil03");
                psCheckUserExists = connection.prepareStatement("SELECT * FROM accounts WHERE SID = ?");
                psCheckUserExists.setString(1, username);
                resultSet = psCheckUserExists.executeQuery();
                // if resultSet empty (user doesn't exist) user can sign up with username
                    psInsert = connection.prepareStatement("UPDATE accounts SET Email=?, NumTel=?, First=?, Last=? WHERE SID = ?");
                    psInsert.setString(1, email);
                    psInsert.setString(2, phoneNumber);
                    psInsert.setString(3, firstName);
                    psInsert.setString(4, lastName);
                    psInsert.setString(5, username);
                    psInsert.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }else{
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
        changeSceneNormal(event, "ProfilePage2.fxml", "Change Password");
    }

    public void goToProfileInfo(ActionEvent event) throws IOException{
        changeSceneNormal(event, "ProfilePage1.fxml", "Profile Information");
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
