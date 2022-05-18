package com.example.ya;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInController extends Utility {

    private Stage stage;
    private Parent root;
    private Scene scene;

@FXML
    private Button loginButton;
@FXML
    private Button SignUpButton;
@FXML
    private TextField UsernameTextField;
@FXML
    private PasswordField PasswordTextField;


    public void switchToLoginScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("LOGINMaker.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("HomeSuperUser.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSignUp(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void login(ActionEvent event) throws IOException{
        String username = UsernameTextField.getText();
        String password = PasswordTextField.getText();
        loginUser (username, password);
        //Utility.changeScene(event, "Home.fxml", "Maker Menu", null, null, null);
        //switchToMenu(event);
    }

    public void signUp(){
        Core.changeScene("SignUp.fxml", "Sign Up");
        //switchToSignUp(event);
    }

}