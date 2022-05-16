package com.example.ya;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Core extends Application {

    public static DatabaseManager database = new DatabaseManager();

    public static Color yellow = Color.rgb(255, 245, 69);
    public static Color white = Color.rgb(255, 255, 255);
    public static Color green = Color.rgb(87, 191, 121);

    public static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        AccountsManager.Initialize();
        mainStage = stage;
        mainStage.setTitle("YOA");
        mainStage.setMaximized(true);

        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Timeline.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        System.out.println(fxmlLoader.getLocation());
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("GlobalStyle.css").toExternalForm());
        mainStage.setScene(scene);
        mainStage.show();

        ///EXPERIMENTATION
        Utility.loginUser("everyone", "understands");
        ///END OF EXPERIMENTATION

        System.out.println("Most Epic App has been Launched");
    }

    public static void main(String[] args) {
        database.Initialize();
        launch(args); //runs the function start
    }

    public static void changeScene(String fxmlFile, String title) {
        FXMLLoader loader = new FXMLLoader(Utility.class.getResource(fxmlFile));
        mainStage.setTitle(title);
        try{
            mainStage.setScene(new Scene(loader.load()));
        }catch(IOException e){
            System.out.println(e);
        }
    }
}