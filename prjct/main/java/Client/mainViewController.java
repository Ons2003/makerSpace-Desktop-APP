package Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static Client.HelloApplication.account ;
public class mainViewController implements Initializable {

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
    private Label usernameLabel;

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
    void switchToStore(ActionEvent event) {
        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("store.fxml")) ;
            borderPane.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        switchToStore(new ActionEvent());
        componentsBookingButton .setStyle("-fx-background-color : #e7ed39;"+"-fx-font-weight:bold;"+"-fx-border-color:black;");
        storeButton.setStyle("-fx-background-color : #e7ed39;"+"-fx-font-weight:bold;"+"-fx-border-color:black;");
        usernameLabel.setText(account.getSID());
    }
}
