package src;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.Connection;

public class SecondController {

    Image myImage = new Image("C:\\Users\\msi\\Documents\\MedTech\\demo\\prjct\\main\\resources\\Media\\image1.jpg") ;
    //items IDs
    @FXML
    Label nameLabel ;
    @FXML
    ImageView myImageView ;
    Button MyButton;

    public void displayImage(){
        myImageView.setImage(myImage);
    }
    public void displayName(String username){
        nameLabel.setText("Welcome "+ username);
    }
}
