module com.example.peluqueria_app_desk {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

  opens com.example.peluqueria_app_desk.Modelos to javafx.base;
    opens com.example.peluqueria_app_desk to javafx.fxml;
    exports com.example.peluqueria_app_desk;
}