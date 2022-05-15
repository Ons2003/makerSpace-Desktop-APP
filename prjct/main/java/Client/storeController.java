package Client;

import javafx.collections.ObservableList;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import src.DatabaseConnection;
import src.EditItem;
import src.Item;

import javax.xml.transform.Result;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Logger;




public class storeController implements Initializable {

    DatabaseConnection connect = new DatabaseConnection();
    ArrayList<String> selectedItems = new ArrayList<String>();

    @FXML
    private ChoiceBox<String> filterBox;

    @FXML
    private TextField searchField;

    @FXML
    private CheckBox showAvailableCheckBox;

    @FXML
    private VBox vBox;

    @FXML
    private ScrollPane scrollPane ;

    @FXML
    private VBox selectionBox;

    @FXML
    Button bookButton ;

    @FXML
    private final String [] filters = {"name","category","reference"} ;

    public void getAllComponents(ActionEvent event){
        selectionBox.getChildren().clear();
        try {
            PreparedStatement pst = connect.connection.prepareStatement("select status,name,category,reference,quantity from components");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Item it = new Item();
                it.setName(rs.getString("name"));
                it.setCategory(rs.getString("category"));
                it.setReference(rs.getString("reference"));
                it.setQuantity(rs.getInt("quantity"));
                it.setStatus(rs.getString("status"));

                HBox itemBox = new HBox() ;
                itemBox.setMinHeight(100);
                itemBox.setSpacing(20);
                itemBox.alignmentProperty().setValue(Pos.BASELINE_LEFT);

                ImageView icon = new ImageView("C:\\Users\\msi\\Documents\\MedTech\\demo\\prjct\\main\\resources\\Client\\c1.png");
                icon.setPreserveRatio(true);
                icon.setFitHeight(50);
                Label nameLabel = new Label("NAME : " + it.getName());
                Label referenceLabel = new Label("REFERENCE : "+it.getReference()) ;
                Label quantityLabel = new Label("QUANTITY : "+it.getQuantity()) ;
                Label categoryLabel = new Label("CATEGORY : "+ it.getCategory()) ;
                Label statusLabel = new Label(it.getStatus()) ;

                Button selectButton = new Button("select");
                selectButton.setId(it.getName());

                selectButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        selectItem(event);
                    }
                });

                itemBox.getChildren().addAll(statusLabel,icon,nameLabel,referenceLabel,categoryLabel,quantityLabel,selectButton);

                vBox.getChildren().add(itemBox) ;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
    public void selectItem(ActionEvent event) {
        Button btn = (Button) event.getSource();
        ClientItem item = new ClientItem();
        //1) get element info
        try {

            PreparedStatement pst = connect.connection.prepareStatement("select status,name,category,reference,quantity from components " +
                    "where name='"+btn.getId()+"' ;");
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                item.setName(rs.getString("name")) ;
                item.setCategory(rs.getString("category"));
                item.setReference(rs.getString("reference"));
                item.setStatus(rs.getString("status"));
                item.setQuantity( rs.getInt("quantity"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //2)choose quantity
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("chooseQuantity.fxml")) ;
            Parent root = (Parent) loader.load() ;
            selectQuantityController controller = loader.getController();
            controller.setInformation(item);

            Stage stage = new Stage();
            Scene scene = new Scene(root) ;
            stage.setScene(scene);
            stage.showAndWait();

            if(controller.getCompleted()){

                item.setSelectedQuantity(controller.getQuantity());

                //2)show selection
                HBox itemBox = new HBox() ;
                VBox list = new VBox() ;

                Label nameLabel = new Label(item.getName());
                Label quantityLabel = new Label("quantity = "+ item.getSelectedQuantity());

                list.getChildren().addAll(nameLabel,quantityLabel);

                itemBox.getChildren().add(list);

                itemBox.setSpacing(5);
                itemBox.minWidth(100) ;
                itemBox.minHeight(20) ;
                itemBox.alignmentProperty().setValue(Pos.BASELINE_CENTER);

                selectionBox.getChildren().add(itemBox) ;
                selectionBox.setSpacing(10);
                selectionBox.setAlignment(Pos.BASELINE_CENTER);
                selectionBox.minWidth(300);
                selectionBox.minHeight(500);
                selectedItems.add(item.getName()) ;
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public String getSelectedItems(){
        final String[] out = {""};
        selectedItems.forEach((item)->{
            out[0] = out[0] +("'"+item+"',");
        });
        return out[0];
    }
    public void validateBooking(ActionEvent event){
        try {
            Statement pst = connect.connection.createStatement();
            String ex = "UPDATE components SET status = 'UNAVAILABLE' WHERE name in ("+getSelectedItems()+"'none');";
            System.out.println(ex);
            pst.executeUpdate(ex);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connect.connect() ;
        filterBox.getItems().addAll(filters);
        getAllComponents(new ActionEvent());

    }
}
