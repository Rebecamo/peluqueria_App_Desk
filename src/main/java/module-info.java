module com.example.peluqueria_app_desk {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires java.management;//2.agregar


    opens com.example.peluqueria_app_desk.Modelos to javafx.base;//1.agregar
    opens com.example.peluqueria_app_desk to javafx.fxml;
    exports com.example.peluqueria_app_desk;
}