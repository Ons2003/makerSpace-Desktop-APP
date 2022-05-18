package Client;


import javafx.css.converter.StringConverter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import src.DatabaseConnection;
import static Client.HelloApplication.account ;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class bookingController implements Initializable {
    @FXML
    VBox view ;
    DatabaseConnection connect = new DatabaseConnection();
    ArrayList<Booking> bookings = new ArrayList<Booking>();

    public void getBookings(){
        try{
            PreparedStatement pst = connect.connection.prepareStatement("select * from bookings");
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
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void showBookings(){
        bookings.forEach((booking)->{
            HBox box = new HBox();
            //
            box.setSpacing(5);
            box.minWidth(100) ;
            box.minHeight(300) ;
            box.alignmentProperty().setValue(Pos.BASELINE_CENTER);
            box.setStyle("-fx-padding: 10;" +"-fx-background-color: #e7ed39;" +"-fx-border-style: solid ;"
                    + "-fx-border-width: 2;" + "-fx-border-insets: 0;"
                    + "-fx-border-radius: 5;" + "-fx-border-color: black;");
            //
            Label id = new Label(String.valueOf(booking.getId())+"   |");
            Label items = new Label(booking.getItem1()+" | "+booking.getItem2()+" | "+booking.getItem3()+" | "+booking.getItem4()+" | "+booking.getItem5());
            Label state = new Label(" | "+booking.getState()) ;
            id.setStyle("-fx-font-weight : bold;"	);
            state.setStyle("-fx-font-weight : bold;");
            box.getChildren().addAll(id,items,state) ;
            box.setAlignment(Pos.BASELINE_CENTER);
            view.setSpacing(10);
            view.setStyle("-fx-padding: 40;" );
            view.getChildren().add(box);

        });
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connect.connect();
        getBookings();
        showBookings();
    }
}
