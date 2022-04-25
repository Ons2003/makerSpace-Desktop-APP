package com.example.test;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class Stylish extends Application {


    @Override

    public void start(Stage stage)  {

        try{
            Parent root = FXMLLoader.load(getClass().getResource("Stylish.fxml"));
            Scene scene1 = new Scene(root);
            String css = this.getClass().getResource("app.css").toExternalForm();
            scene1.getStylesheets().add(css);
            //scene1.setFill(Color.TRANSPARENT);

            stage.setWidth(600);

            stage.setHeight(500);
            stage.setScene(scene1);
            //stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
