package Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class mainViewController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button componentsBookingButton;

    @FXML
    private Button makerBookingButton;

    @FXML
    private Button storeButton;

    @FXML
    private AnchorPane view;

    @FXML
    void switchToComponentsBooking(ActionEvent event) {
        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("componentsBooking.fxml")) ;
            borderPane.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchToMakerBooking(ActionEvent event) {
        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("makerBooking.fxml")) ;
            borderPane.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchToStore(ActionEvent event) {
        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("store.fxml")) ;
            borderPane.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
