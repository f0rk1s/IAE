module com.example.iae {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;
    requires com.fasterxml.jackson.databind;
    requires javafx.graphics;

    opens com.example.iae to javafx.fxml;

    exports com.example.iae;

    opens main.java.org.example to javafx.graphics;

    exports main.java.org.example;
}