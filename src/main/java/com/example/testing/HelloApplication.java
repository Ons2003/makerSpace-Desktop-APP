package com.example.testing;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    public static DatabaseManager database = new DatabaseManager();

    public static Color yellow = Color.rgb(255, 245, 69);
    public static Color white = Color.rgb(255, 255, 255);
    public static Color green = Color.rgb(87, 191, 121);


    @Override
    public void start(Stage stage) throws IOException {
        AccountsManager.Initialize();

        SetupStage(stage);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MakerspaceTimeline-View.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("MakerspaceTimeline-View.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
        System.out.println("Most Epic App has been Launched");
    }

    public static void main(String[] args) {
        database.Initialize();
        launch(args); //runs the function start
    }

    public static void SetupStage(Stage stage){
        stage.setTitle("YOA!");
        stage.setMaximized(true);
        Image icon = new Image("icon.png");
        stage.getIcons().add(icon);
    }
}