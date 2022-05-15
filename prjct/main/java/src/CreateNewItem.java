package src;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;


import java.net.URL;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


public class CreateNewItem  implements Initializable {
        @FXML
        private TextField nameField;
        @FXML
        private TextField referenceField;
        @FXML
        private ChoiceBox<String> categoryBox;
        @FXML
        private Spinner<Integer> quantitySpinner;
        @FXML
        private Button doneButton;


        private  String [] categories = {"accessory","actuator",
          "module","power","sensor"};

    public void validateNewItem(ActionEvent event) {

        String name = nameField.getText() ;
        String ref = referenceField.getText();
        String cat = categoryBox.getValue() ;
        int quantity = quantitySpinner.getValue() ;

        DatabaseConnection connect = new DatabaseConnection();
        connect.connect();

        try {
            Statement pst = connect.connection.createStatement();
            pst.executeUpdate("INSERT INTO components (name,category,reference,quantity) VALUES('" +name+ "','" + cat + "','" + ref + "','" + quantity + "')");
            ((Node)(event.getSource())).getScene().getWindow().hide();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        categoryBox.getItems().addAll(categories) ;

        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1,1000);
        valueFactory.setValue(1);
        quantitySpinner.setEditable(true);
        quantitySpinner.setValueFactory(valueFactory);
    }
}


