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

public class EditItem implements Initializable {

    @FXML
    private ChoiceBox<String> categoryBox;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton ;

    @FXML
    private TextField nameField;

    @FXML
    private Spinner<Integer> quantitySpinner;

    @FXML
    private TextField referenceField;

    @FXML
    private CheckBox availableButton ;
    @FXML
    private CheckBox unavailableButton ;

    private  String [] categories = {"accessory","actuator",
            "module","power","sensor"};
    private String itemName ;

    @FXML
    void editItem(ActionEvent event) {
        //save new information
        String name = nameField.getText() ;
        String ref = referenceField.getText();
        String cat = categoryBox.getValue() ;
        String stat ;
        if (availableButton.isSelected()){
            stat = "AVAILABLE" ;
        } else {
            stat = "UNAVAILABLE" ;
        }
        int quantity = quantitySpinner.getValue() ;

        DatabaseConnection connect = new DatabaseConnection();
        connect.connect();

        try {
            Statement pst = connect.connection.createStatement();
            pst.executeUpdate("UPDATE components SET name = '" +name+ "', category ='" + cat + "', reference ='" + ref + "', quantity=" + quantity +", status ='" + stat + "' " +
                    "where name= '"+itemName+"'; ");
            ((Node)(event.getSource())).getScene().getWindow().hide();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteItem(ActionEvent event) {
        DatabaseConnection connect = new DatabaseConnection();
        connect.connect();
        try {
            Statement pst = connect.connection.createStatement();
            pst.executeUpdate(" DELETE FROM components WHERE name = '"+itemName+"';");
            ((Node)(event.getSource())).getScene().getWindow().hide();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void setInformation(String name, String reference, String category,String status ,int quantity){
        itemName = name ;

        nameField.setText(name);
        referenceField.setText(reference);
        categoryBox.setValue(category);

        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1,1000);
        valueFactory.setValue(quantity);
        quantitySpinner.setEditable(true);
        quantitySpinner.setValueFactory(valueFactory);
        System.out.println(status);
        if (status.equals("AVAILABLE")){
            availableButton.setSelected(true);
            unavailableButton.setSelected(false);
        } else {
            availableButton.setSelected(false);
            unavailableButton.setSelected(true);
        }

    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoryBox.getItems().addAll(categories) ;

    }


}
