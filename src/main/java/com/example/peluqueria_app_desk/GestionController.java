package com.example.peluqueria_app_desk;

import com.example.peluqueria_app_desk.Conexion.ConexionDB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;


public class GestionController {
    @FXML
    private MenuItem menuItemCerrarSesion;

    @FXML
    private MenuItem menuItemGestionarHorarios;
    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem menuItemGestionarReservas;

    @FXML
    private MenuItem menuItemHistorial;

    @FXML
    private Menu menuInicio;

    @FXML
    public void initialize() {
        // Manejar acciones de los menús
        menuItemCerrarSesion.setOnAction(event -> cerrarSesion());
        menuItemGestionarHorarios.setOnAction(event -> gestionHorario());
        menuItemGestionarReservas.setOnAction(event -> gestionReservas());
        menuItemHistorial.setOnAction(event -> historial());
        menuInicio.setOnAction(event -> inicio());
    }

    private void vistas(String fxmlFile) {
        try {
            // Cargar la nueva vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            // Obtener el Stage actual
            Stage stage = (Stage) menuBar.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private void inicio() {
        vistas("view_login.fxml");
    }

    private void gestionHorario() {
        vistas("view_horarios.fxml");
    }

    private void gestionReservas() {
        vistas("view_gestion_reservas.fxml");
    }

    private void historial() {
        vistas("view_historial_reservas.fxml");
    }

    private void cerrarSesion() {
        System.out.println("Cerrando sesión...");
        vistas("view_login.fxml"); // Redirige al login
    }
}