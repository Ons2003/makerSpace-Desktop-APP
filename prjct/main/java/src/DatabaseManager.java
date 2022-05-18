package src;

import Client.Booking;
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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.control.TableColumn.CellEditEvent;

import java.sql.* ;


public class DatabaseManager implements Initializable {

        DatabaseConnection connect = new DatabaseConnection();
        ArrayList<Booking> bookings = new ArrayList<Booking>();

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
        @FXML
        private Label approvalText ;
        @FXML
        private Button homeButton;

        @FXML
        private Button refreshButton;

    @FXML
    private VBox bookingView ;
        public void approveBooking(ActionEvent event){
            Button btn = (Button) event.getSource();
            try {
                Statement pst = connect.connection.createStatement();
                pst.executeUpdate("UPDATE bookings SET state = 'CONFIRMED' WHERE id="+Integer.parseInt(btn.getId())+";");

            } catch (SQLException e) {
                e.printStackTrace();
            }
           approvalText.setText("You approved a booking");
        }
        public void newItem(ActionEvent actionEvent) throws IOException{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("newItem.fxml")) ;
            Parent root = (Parent) loader.load() ;
            Stage stage = new Stage();
            Scene scene = new Scene(root) ;
            stage.setScene(scene);
            stage.show();
        }
        public  void getBookings(){
            bookings.clear();
            PreparedStatement pst = null;
            try {
                pst = connect.connection.prepareStatement("select id,state,item1,item2,item3,item4,item5 from bookings");
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    Booking booking = new Booking();

                    booking.setId(rs.getInt("id"));
                    booking.setState(rs.getString("state"));
                    booking.setItem1(rs.getString("item1"));
                    booking.setItem2(rs.getString("item2"));
                    booking.setItem3(rs.getString("item3"));
                    booking.setItem4(rs.getString("item4"));
                    booking.setItem5(rs.getString("item5"));
                    bookings.add(booking);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    public void showBookings() {

        bookings.forEach((booking) -> {
            HBox box = new HBox();
            //
            box.setSpacing(5);
            box.minWidth(100);
            box.minHeight(300);
            box.alignmentProperty().setValue(Pos.BASELINE_CENTER);
            box.setStyle("-fx-padding: 10;" +"-fx-background-color: #e7ed39;" +"-fx-border-style: solid ;"
                    + "-fx-border-width: 2;" + "-fx-border-insets: 0;"
                    + "-fx-border-radius: 5;" + "-fx-border-color: black;"+"");
            //
            Label id = new Label(String.valueOf(booking.getId())+"   |");
            Label items = new Label(booking.getItem1() + " | "+ booking.getItem2() + " | " + booking.getItem3() + " | " + booking.getItem4() + " | " + booking.getItem5());
            Label state = new Label(" | "+ booking.getState());
            Button approveButton = new Button("APPROVE");
            approveButton.setStyle("-fx-background-color : #e7ed39;"+"-fx-font-weight:bold;"+"-fx-border-color:black;");
            id.setStyle("-fx-font-weight : bold;"	);
            state.setStyle("-fx-font-weight : bold;");
            approveButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    approveBooking(event);
                }
            });
            approveButton.setId(String.valueOf(booking.getId()));
            box.getChildren().addAll(id, items, state);
            if (booking.getState().equalsIgnoreCase("NOT CONFIRMED")){
                box.getChildren().add(approveButton);
            }
            box.setAlignment(Pos.TOP_CENTER);
            bookingView.getChildren().add(box) ;

        });
        }
        public void refresh(ActionEvent event){
            approvalText.setText("Managing bookings...");
            bookingView.getChildren().clear();
            getBookings();
            showBookings();
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
            newItemButton.setStyle("-fx-background-color : #e7ed39;"+"-fx-font-weight:bold;"+"-fx-border-color:black;");
            loadButton.setStyle("-fx-background-color : #e7ed39;"+"-fx-font-weight:bold;"+"-fx-border-color:black;");
            homeButton.setStyle("-fx-background-color : #e7ed39;"+"-fx-font-weight:bold;"+"-fx-border-color:black;");
            refreshButton.setStyle("-fx-background-color : #e7ed39;"+"-fx-font-weight:bold;"+"-fx-border-color:black;");
            bookingView.setStyle("-fx-padding: 20;");
            getBookings();
            showBookings();

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

