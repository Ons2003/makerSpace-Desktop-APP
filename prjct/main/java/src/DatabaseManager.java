package src;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.TableColumn.CellEditEvent;

import java.sql.* ;


public class DatabaseManager implements Initializable {

        DatabaseConnection connect = new DatabaseConnection();
        @FXML
        private TableView<src.Item> tableView;
        @FXML
        private TableColumn<String, Item> statusColumn;
        @FXML
        private TableColumn<String, Item> nameColumn;
        @FXML
        private TableColumn<String, Item> categoryColumn;
        @FXML
        private TableColumn<String, Item> referenceColumn;
        @FXML
        private TableColumn<Integer, Item> quantityColumn;
        @FXML
        private Button newItemButton = new Button() ;
        @FXML
        private  Button loadButton = new Button() ;
        @FXML
        private TextField filterField;
        @FXML
        private CheckBox nameCheckBox ;
        @FXML
        private CheckBox statusCheckBox;
        @FXML
        private CheckBox referenceCheckBox;
        @FXML
        private CheckBox categoryCheckBox;


        public void newItem(ActionEvent actionEvent) throws IOException{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("newItem.fxml")) ;
            Parent root = (Parent) loader.load() ;
            Stage stage = new Stage();
            Scene scene = new Scene(root) ;
            stage.setScene(scene);
            stage.show();
        }
        public void showTable(ActionEvent event) throws  IOException {
            try {
                ObservableList<Item> items = tableView.getItems();
                items.clear();
                PreparedStatement pst = connect.connection.prepareStatement("select status,name,category,reference,quantity from components");
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    Item it = new Item();
                    it.setName(rs.getString("name"));
                    it.setCategory(rs.getString("category"));
                    it.setReference(rs.getString("reference"));
                    it.setQuantity(rs.getInt("quantity"));
                    it.setStatus(rs.getString("status"));
                    items.add(it);
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }

        public void initialize(URL url, ResourceBundle resourceBundle) {


            connect.connect();
            ObservableList<Item> items = tableView.getItems();
            tableView.setEditable(true);


            statusColumn.setCellValueFactory(new PropertyValueFactory<String, Item>("status"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<String, Item>("name"));

            categoryColumn.setCellValueFactory(new PropertyValueFactory<String, Item>("category"));
            referenceColumn.setCellValueFactory(new PropertyValueFactory<String, Item>("reference"));
            quantityColumn.setCellValueFactory(new PropertyValueFactory<Integer, Item>("quantity"));


            try {
                showTable(new ActionEvent());
                FilteredList<Item> filteredData = new FilteredList<>(items, b -> true);
                //listener for selection
                tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                        //Check whether item is selected and set value of selected item to Label
                        if(tableView.getSelectionModel().getSelectedItem() != null)
                        {
                            TableView.TableViewSelectionModel selectionModel = tableView.getSelectionModel();
                            Item item = new Item();
                            item = (Item) selectionModel.getSelectedItem();
                            System.out.println(item.getCategory());
                            try{
                                String name = "hello " ;
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("editItem.fxml")) ;
                                Parent root = (Parent) loader.load() ;

                                EditItem controller = loader.getController();
                                controller.setInformation(item.getName(),item.getReference(),item.getCategory(),item.getStatus(),item.getQuantity());
                                Stage stage = new Stage();
                                Scene scene = new Scene(root) ;
                                stage.setScene(scene);
                                stage.show();
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    }
                });
                // listener for searchField
                filterField.textProperty().addListener((observable, oldValue, newValue) -> {

                    if (nameCheckBox.isSelected()) {
                        filteredData.setPredicate(item -> {
                            if (newValue == null || newValue.isEmpty()) {
                                return true;
                            }
                            String lowerCaseFilter = newValue.toLowerCase();

                            if (item.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                                return true; // Filter matches first name.
                            } else {
                                return false; // Does not match.
                            }
                        });
                    } else if (categoryCheckBox.isSelected()) {
                        filteredData.setPredicate(item -> {
                            if (newValue == null || newValue.isEmpty()) {
                                return true;
                            }
                            String lowerCaseFilter = newValue.toLowerCase();

                            if (item.getCategory().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                                return true; // Filter matches first name.
                            } else {
                                return false; // Does not match.
                            }
                        });
                    } else if (referenceCheckBox.isSelected()) {
                        filteredData.setPredicate(item -> {
                            if (newValue == null || newValue.isEmpty()) {
                                return true;
                            }
                            String lowerCaseFilter = newValue.toLowerCase();

                            if (item.getReference().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                                return true; // Filter matches first name.
                            } else {
                                return false; // Does not match.
                            }
                        });
                    } else if (statusCheckBox.isSelected()) {
                        filteredData.setPredicate(item -> {
                            if (newValue == null || newValue.isEmpty()) {
                                return true;
                            }
                            String lowerCaseFilter = newValue.toLowerCase();

                            if (item.getStatus().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                                return true;
                            } else {
                                return false;
                            }
                        });
                    }

                    // 3. Wrap the FilteredList in a SortedList.
                    SortedList<Item> sortedData = new SortedList<>(filteredData);

                    sortedData.comparatorProperty().bind(tableView.comparatorProperty());

                    tableView.setItems(sortedData);
                });

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
}

