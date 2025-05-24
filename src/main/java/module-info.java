module com.example.familytree {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.dlsc.formsfx;
    requires java.desktop;

    requires com.fasterxml.jackson.databind;

    opens com.example.familytree to javafx.fxml, com.fasterxml.jackson.databind;
    exports com.example.familytree;
}
