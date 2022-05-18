package Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class selectQuantityController  {



    @FXML
    private Spinner<Integer> quantitySpinner;

    private int finalQuantity = 1 ;
    private Boolean completed = false ;

    public void setInformation(ClientItem item){

        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1,item.getQuantity());
        valueFactory.setValue(1);
        quantitySpinner.setEditable(true);
        quantitySpinner.setValueFactory(valueFactory);
    }

    @FXML
    private Button validateButton;

    public int getQuantity(){
        return finalQuantity ;
    }
    @FXML
    void validateQuantity(ActionEvent event) {
        completed = true ;
        finalQuantity = quantitySpinner.getValue();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    public Boolean getCompleted(){
        return completed ;
    }


}
