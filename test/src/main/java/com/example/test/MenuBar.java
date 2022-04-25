package com.example.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuBar extends Application {

    @Override

    public void start(Stage stage)  {

        try{
            Parent root = FXMLLoader.load(getClass().getResource("MenuBar.fxml"));
            Scene scene1 = new Scene(root);
            String css = this.getClass().getResource("app.css").toExternalForm();
            scene1.getStylesheets().add(css);
            stage.setWidth(600);
            stage.setHeight(500);
            stage.setScene(scene1);
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
