package Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


public class HelloApplication extends Application {
    public static final int MAX_QUANTITY = 100 ;
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml")) ;
        Parent clientRoot = loader.load() ;
        Scene scene = new Scene(clientRoot);
        //link styling sheet
        //String css = this.getClass().getResource("style.css").toExternalForm();
        //scene.getStylesheets().add(css);

        //Image icon = new Image(String.valueOf(getClass().getResource("c1.png")));
        //stage.getIcons().add(icon); //app icon
        stage.setTitle("space"); //app title
        stage.setFullScreen(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        launch();
    }
}