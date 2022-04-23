module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens src to javafx.fxml;
    exports src;
}