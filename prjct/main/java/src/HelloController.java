package src;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    //create IDs for elements with proper type
    @FXML
    TextField nameTextField;
    @FXML
    Label label;

    //created to load next scene
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void login(ActionEvent event) throws IOException {

        //action of switching from main scene to another with information saving


        int age = Integer.parseInt(nameTextField.getText()); //get username from scene1 before switching to scene2

        FXMLLoader loader = new FXMLLoader(getClass().getResource("second-view.fxml"));
        root = loader.load(); //loader for scene2

        SecondController secondController = loader.getController();
        //create instance of scene2 controller to be able to use methods from scene2 with elements from scene1
        secondController.displayName("you're logged in");
        //show scene2
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
