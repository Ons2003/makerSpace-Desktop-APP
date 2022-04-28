package com.example.ya;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.w3c.dom.events.UIEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class StylishController extends Utility{
    public Text WarningAgeText;
    //sign up page

    @FXML
    Label FirstNameLabel;
    @FXML
    Label LastNameLabel;
    @FXML
    Label UsernameLabel;
    @FXML
    Label PasswordLabel;

    @FXML
    Button ConfirmButton;
    @FXML
    Text WarningNamesText;
    @FXML
    Text WarningUsernameText;
    @FXML
    Text WarningPasswordText;

    @FXML
    TextField FirstNameTextField;
    @FXML
    TextField LastNameTextField;
    @FXML
    TextField UsernameTextField;
    @FXML
    PasswordField PasswordField1;

    @FXML
    ImageView myImageView;
    @FXML
    Button SwitchButton;

    @FXML
    Button DebuggerButton;
    @FXML
    Text Debugger;

    Image myImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Photo2.jpg")));

    public void displayImage() {
        myImageView.setImage(myImage);
    }


    public int CountnbDigits(String message) {
        int nbDigits = 0;
        for (int i = 0; i < message.length(); ++i) {
            if (Character.isDigit(message.charAt(i)))
                nbDigits++;
        }
        return nbDigits;
    }

    public int CountnbCharacters(String message) {
        int nbCharacters = 0;
        //int nbSpecialCharacters = 0;
        for (int i = 0; i < message.length(); ++i) {
            if (Character.isAlphabetic(message.charAt(i)))
                nbCharacters++;
            //if character is a special character
            // if (Character.)
        }
        return nbCharacters;
    }

    public void clearNamesTextFields() {
        FirstNameTextField.setText(null);
        LastNameTextField.setText(null);
    }

    public void clearUsernamePasswordTextFields() {
        UsernameTextField.setText(null);
        PasswordField1.setText(null);
        //AgeTextField.setText(null);
    }

    public void clearAnchorPane() {
        clearUsernamePasswordTextFields();
    }

    /*public void changeWarningUsernameTextGreen(){
        WarningUsernameText.setFill(Color.GREEN);
    }
    public void changeWarningNamesTextGreen(){
        WarningUsernameText.setFill(Color.GREEN);
    }*/

    String FirstName;
    String LastName;
    String username;
    String password;

    boolean NamesVerified = false;
    boolean UsernameVerified = false;
    boolean PasswordVerifed = false;
    boolean AgeVerified = false;

    private Stage stage;
    private Scene scene;
    private Parent root;


    /*public void switchToMenu(ActionEvent event) throws IOException {
        MenuBarController mc = new MenuBarController();
        root = FXMLLoader.load(getClass().getResource("MenuBar.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        mc.setWelcomeLabel(FirstName, LastName);
        stage.show();
    }*/

/*
    public int checkCredentials() throws IOException {

        FirstName = FirstNameTextField.getText();
        LastName = LastNameTextField.getText();

        username = UsernameTextField.getText();
        System.out.println(username);
        password = PasswordField1.getText();
        System.out.println(password);

        if (CountnbDigits(FirstName) == 0 && CountnbDigits(LastName) == 0 && FirstName.length() > 0 && LastName.length() > 1) {
            WarningNamesText.setText("Names are verified");
            NamesVerified = true;
            //changeWarningNamesTextGreen();
        } else {
            while (CountnbDigits(FirstName) > 0 || CountnbDigits(LastName) > 0) {
                clearNamesTextFields();
                WarningNamesText.setText("Please make sure your name doesn't contain a digit");
                FirstName = FirstNameTextField.getText();
                System.out.println(FirstName);
                LastName = LastNameTextField.getText();
                System.out.println(LastName);
            }
            NamesVerified = true;
        }


        if (CountnbCharacters(username) > 2 && CountnbDigits(username) > 2) {
            WarningUsernameText.setText("Username is verified");
            UsernameVerified = true;
        }

    //changeWarningUsernameTextGreen();}

        else {
            while (CountnbCharacters(username) < 2 || CountnbDigits(username) < 2) {
                clearUsernamePasswordTextFields();
                WarningUsernameText.setText("Your username should contain at least 2 alphabetical characters and at least 2 digits");
                username = UsernameTextField.getText();
                System.out.println(username);
                password = PasswordField1.getText();
                System.out.println(password);
            }
            UsernameVerified = true;
        }

        if (password.length() >= 8) {
            WarningPasswordText.setText("Password is verified");
            PasswordVerifed = true;
        }
        //changeWarningUsernameTextGreen();}
        else {
            while (password.length() < 8) {
                PasswordField1.setText(null);
                WarningPasswordText.setText("Your password should contain at least 8 characters (digits or alphabetical characters)");
                password = PasswordField1.getText();
                System.out.println(password);
            }
            PasswordVerifed = true;
        }

        if (NamesVerified == true && UsernameVerified == true && PasswordVerifed == true) {
            return 0;
        } else return 1;
    }
*/

    /*@FXML
    public void Debugging() throws IOException{
        int a = checkCredentials();
        Debugger.setText(a + "\nUsername : " + username + "\nPassword : " + password);
    }

      /*  try {
            age = Integer.parseInt(AgeTextField.getText());
            System.out.println(age);
        } catch (NumberFormatException e) {
            WarningAgeText.setText("Please make sure you only enter numbers for your age");
        } catch (Exception e) {
            WarningAgeText.setText("error");
        }

        if (NamesVerified == true && UsernameVerified == true && PasswordVerifed == true) {
            return 0;
        } else return 1;
    }*/


    public void confirm(ActionEvent event) throws IOException{

        FirstName = FirstNameTextField.getText();
        LastName = LastNameTextField.getText();
        username = UsernameTextField.getText();
        password = PasswordField1.getText();

       // if (checkCredentials() == 0) {
                if (!username.trim().isEmpty() && !password.trim().isEmpty() && !FirstName.trim().isEmpty() && !LastName.trim().isEmpty()){
                    signUpUser(username, password, FirstName, LastName);
                    root = FXMLLoader.load(getClass().getResource("MenuBar.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    //MenuBarController.setWelcomeLabel(FirstName, LastName);
                    stage.show();
                }
                else{
                    System.out.println("Please fill in all information to sign up");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all information to sign up");
                    alert.show();
                }
           // }


    }
}
