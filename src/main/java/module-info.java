module com.example.ya {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.ya to javafx.fxml;
    exports com.example.ya;
}