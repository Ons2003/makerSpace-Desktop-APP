package com.example.test;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.Scanner;

import static javafx.scene.input.KeyCode.F;

public class SceneBuild extends Application {

    @Override

    public void start(Stage stage) throws IOException {
        Scanner S = new Scanner(System.in);

        //Group roots = new Group();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Controller controller = loader.getController();

        scene.setOnKeyPressed(event->{

                System.out.println(event.getCode());
                switch (event.getCode()){
                    case UP:
                        controller.moveUp();
                        break;
                    case DOWN:
                        controller.moveDown();
                        break;
                    case RIGHT:
                        controller.moveRight();
                        break;
                    case LEFT:
                        controller.moveLeft();
                        break;
                    default: break;
                }

        });

//full screen option
        scene.setOnKeyPressed(event->{
            System.out.println(event.getCode());
            if (event.getCode()==F){
               stage.setFullScreen(true);
                }
        });

        stage.setWidth(600);
        stage.setHeight(500);

        stage.setScene(scene);
        stage.show();


        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("e"));
//Alert pops up when we press on red X
        stage.setOnCloseRequest(event -> {
            event.consume(); // prevents program to close when we click cancel after red X
            logout(stage);
        });
    }

    public void logout (Stage stage){

        Alert alert = new Alert (Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You are about to logout");
        alert.setContentText("Are you sure you want to quit without saving");

        if (alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("You have successfully logged out");
            stage.close();
        }
    }

    public static void main(String[] args) {
        launch();
    }



}
