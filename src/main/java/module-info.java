module com.example.iae {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;
    requires com.fasterxml.jackson.databind;

    opens com.example.iae to javafx.fxml;
    exports com.example.iae;
}