package com.example.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginLaunch extends Application {


    @Override

    public void start(Stage stage) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("SignUpIn.fxml"));
            Scene scene1 = new Scene(root);
            String css = this.getClass().getResource("SignUpInStyle.css").toExternalForm();
            scene1.getStylesheets().add(css);
            scene1.setFill(Color.TRANSPARENT);


            stage.setScene(scene1);
            stage.setTitle("Login");
            //stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
