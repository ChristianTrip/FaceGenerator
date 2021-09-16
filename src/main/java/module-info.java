module com.example.facegenerator {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.facegenerator to javafx.fxml;
    exports com.example.facegenerator;
}