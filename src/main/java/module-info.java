module com.example.familytree {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.familytree to javafx.fxml;
    exports com.example.familytree;
}