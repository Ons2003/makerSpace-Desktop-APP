package com.example.ya;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.Scanner;

public class Controller {

    @FXML
    private Circle myCircle;
    private double x;
    private double y;

    public void up(ActionEvent e){
        System.out.println("UP");
        myCircle.setCenterY(y-=10);
    }
    public void down(ActionEvent e){
        System.out.println("DOWN");
        myCircle.setCenterY(y+=10);
    }
    public void right(ActionEvent e){
        System.out.println("RIGHT");
        myCircle.setCenterX(x+=10);
    }
    public void left(ActionEvent e){
        System.out.println("LEFT");
        myCircle.setCenterX(x-=10);
    }

    public void moveUp(){
        System.out.println("Moving up");
        myCircle.setCenterY(y-=10);
    }
    public void moveDown(){
        System.out.println("Moving down");
        myCircle.setCenterY(y+=10);
    }
    public void moveRight(){
        System.out.println("Moving right");
        myCircle.setCenterX(x+=10);
    }
    public void moveLeft(){
        System.out.println("Moving left");
        myCircle.setCenterX(x-=10);
    }

    @FXML
    private Button logoutButton;
    @FXML
    private AnchorPane scenePane;
    Stage stage;
    public void logout (ActionEvent event){

        Alert alert = new Alert (Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You are about to logout");
        alert.setContentText("Are you sure you want to quit without saving");

        //Alert appears when we press logout button
        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) scenePane.getScene().getWindow();
            System.out.println("You have successfully logged out");
            stage.close(); // alert closes when we press OK
        }
    }

}
