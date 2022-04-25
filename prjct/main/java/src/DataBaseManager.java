package src;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class DataBaseManager implements Initializable{
    @FXML
    private TableView<Item> tableView ;

    @FXML
    private TableColumn<String,Item> statusColumn ;
    @FXML
    private TableColumn<String,Item> nameColumn ;
    @FXML
    private TableColumn<String,Item> categoryColumn ;
    @FXML
    private TableColumn<String,Item> referenceColumn ;
    @FXML
    private TableColumn<Integer,Item> quantityColumn ;


    @FXML

    public void initialize(URL url, ResourceBundle resourceBundle ) {
        statusColumn.setCellValueFactory(new PropertyValueFactory<String,Item>("status"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<String,Item>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<String,Item>("category"));
        referenceColumn.setCellValueFactory(new PropertyValueFactory<String,Item>("reference"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<Integer,Item>("quantity"));

        Item item = new Item("name","cat","123",12,Status.AVAILABLE) ;
        ObservableList<Item> items = tableView.getItems() ;
        items.add(item) ;
        tableView.setItems(items) ;
    }

}
