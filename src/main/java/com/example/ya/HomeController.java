package com.example.ya;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {
@FXML
    private Button LogoutButton;
@FXML
    private Label WelcomeLabel;
@FXML
    private Text MenuText;
@FXML
    private Button ProfileButton;

    public void goToProfileButton(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("ProfilePage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Account Profile");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void setWelcomeLabel(String username, String FirstName, String LastName){
        WelcomeLabel.setText("Welcome " + username);
        MenuText.setText(FirstName + " " + LastName + " meet, the MakerSpace");
    }

    public void logout(ActionEvent event) throws IOException {
        Core.changeScene("SignUpIn.fxml", "LogIn");

    }

    public void OpenTimeline(){
        Core.changeScene("Timeline.fxml", "Timeline");
    }
}
