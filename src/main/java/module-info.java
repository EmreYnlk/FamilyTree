module com.example.familytree {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.desktop;

    opens com.example.familytree to javafx.fxml;
    exports com.example.familytree;
}