package src;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml") );
        Parent root = loader.load() ;


        Scene scene = new Scene(root);
        //link styling sheet
        //String css = this.getClass().getResource("style.css").toExternalForm();
        //scene.getStylesheets().add(css);

        Image icon = new Image(String.valueOf(getClass().getResource("c1.png")));
        stage.getIcons().add(icon); //app icon
        stage.setTitle("Maker space"); //app title
        stage.setFullScreen(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        launch();
    }
}