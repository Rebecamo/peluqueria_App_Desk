package com.example.peluqueria_app_desk;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class GestionController {

    @FXML
    private Label lblEmpleado; // Etiqueta para mostrar información del empleado

    @FXML
    private Button btnGestionarHorarios; // Botón para ir a la gestión de horarios

    @FXML
    private Button btnGestionarReservas; // Botón para ir a la gestión de servicios

    @FXML
    private Button btnHistorialReservas;

    private int empleadoId; // ID del empleado que inició sesión

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;

        // Puedes usar este ID para cargar datos del empleado, si es necesario
        lblEmpleado.setText("Bienvenido, empleado ID: " + empleadoId);
    }

    @FXML
    private void gestionarHorarios() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/HorariosView.fxml"));
            Stage stage = (Stage) btnGestionarHorarios.getScene().getWindow();
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.setTitle("Gestión de Horarios");

            // Pasar el ID del empleado al controlador de la vista de horarios
            HorariosController horariosController = fxmlLoader.getController();
            horariosController.setEmpleadoId(empleadoId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que maneja la acción de ir a la vista de gestión de servicios.
     */
    @FXML
    private void gestionarReservas() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/ServiciosView.fxml"));
            Stage stage = (Stage) gestionarServiciosButton.getScene().getWindow();
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.setTitle("Gestión de Servicios");

            // Pasar el ID del empleado al controlador de la vista de servicios
            ReservasController serviciosController = fxmlLoader.getController();
            serviciosController.setEmpleadoId(empleadoId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void HistorialReservas() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/ServiciosView.fxml"));
            Stage stage = (Stage) gestionarServiciosButton.getScene().getWindow();
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.setTitle("Historial reservas");

            // Pasar el ID del empleado al controlador de la vista de servicios
            HistorialReservasController serviciosController = fxmlLoader.getController();
            serviciosController.setEmpleadoId(empleadoId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
