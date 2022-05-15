module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    opens src to javafx.fxml;
    exports src;
    opens Client to javafx.fxml;
    exports Client ;
}