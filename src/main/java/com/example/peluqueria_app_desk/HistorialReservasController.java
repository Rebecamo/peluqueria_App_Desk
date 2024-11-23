package com.example.peluqueria_app_desk;

import com.example.peluqueria_app_desk.Modelos.HistorialModel;
import com.example.peluqueria_app_desk.Modelos.ReservasModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.chart.XYChart;

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

        // Cargar datos en la tabla
        cargarDatos();
    }

    @FXML
    private void cargarDatos() {
        ObservableList<ReservasModel> reservas = reservasModel.getReservas(); // Método ya implementado
        tblReservas.setItems(reservas);
    }
}
