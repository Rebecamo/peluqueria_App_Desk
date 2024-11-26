package com.example.peluqueria_app_desk;

import com.example.peluqueria_app_desk.Modelos.HistorialModel;
import com.example.peluqueria_app_desk.Modelos.ReservasModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HistorialReservasController {
    @FXML
    private TableView<ReservasModel> tblReservas;

    @FXML
    private TableColumn<ReservasModel, Integer> clidReserva;
    @FXML
    private TableColumn<ReservasModel, String> clCliente;
    @FXML
    private TableColumn<ReservasModel, String> clServicio;
    @FXML
    private TableColumn<ReservasModel, LocalDate> clFechaReserva;
    @FXML
    private TableColumn<ReservasModel, String> clHoraReserva;
    @FXML
    private TableColumn<ReservasModel, String> clEmpleado;
    @FXML
    private TableColumn<ReservasModel, String> clEstadoReserva;

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem menuItemAtras;

    @FXML
    private MenuItem menuItemGestionHorario;

    @FXML
    private MenuItem menuItemGestionReservas;

    @FXML
    private MenuItem menuItemInicio;


    private ReservasModel reservasModel = new ReservasModel();

    public void initialize() {
        // Configurar columnas

        clidReserva.setCellValueFactory(new PropertyValueFactory<>("idReserva"));
        clCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        clServicio.setCellValueFactory(new PropertyValueFactory<>("servicio"));
        clFechaReserva.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        clHoraReserva.setCellValueFactory(new PropertyValueFactory<>("horaReserva"));
        clEmpleado.setCellValueFactory(new PropertyValueFactory<>("empleado"));
        clEstadoReserva.setCellValueFactory(new PropertyValueFactory<>("estadoReserva"));

        // cargar datos en la tabla
        cargarDatos();
        menuItemGestionHorario.setOnAction(event -> gestionHorario());
        menuItemGestionReservas.setOnAction(event -> gestionReservas());
        menuItemAtras.setOnAction(event -> gestion());
        menuItemInicio.setOnAction(event -> inicio());
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


    private void gestion() {

        vistas("view_Gestion.fxml"); // Redirige al login
    }


    @FXML
    private void cargarDatos() {
        ObservableList<ReservasModel> reservas = reservasModel.getReservas(); // Método ya implementado
        tblReservas.setItems(reservas);
    }
}
