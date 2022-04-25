package com.example.test;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuBarController {


        @FXML
        Button LoginButton;
        @FXML
        Label WelcomeLabel;

       LogInController lc = new LogInController();
       StylishController sc = new StylishController();

        public void logout(ActionEvent event) throws IOException {
            lc.switchToLoginScene(event);
        }

        public void setWelcomeLabel(String FirstName, String LastName){
            WelcomeLabel.setText("Welcome " + FirstName + " " + LastName);
        }

    private Stage stage;
    private Scene scene;
    private Parent root;

    /*public void switchToMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("MenuBar.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        setWelcomeLabel(FirstName, LastName);
        stage.show();
    }*/
}
