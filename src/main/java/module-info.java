module com.example.peluqueria_app_desk {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.peluqueria_app_desk to javafx.fxml;
    exports com.example.peluqueria_app_desk;
}