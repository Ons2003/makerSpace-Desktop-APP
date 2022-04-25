package com.example.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ProfilePage extends Application {

    @Override
    public void start(Stage stage)  {

        try{
            Parent root = FXMLLoader.load(getClass().getResource("ProfilePage.fxml"));
            Scene scene1 = new Scene(root);
            //String css = this.getClass().getResource("app.css").toExternalForm();
            //scene1.getStylesheets().add(css);
            //scene1.setFill(Color.TRANSPARENT);

            stage.setWidth(700);
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

