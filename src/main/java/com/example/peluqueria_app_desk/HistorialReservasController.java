package com.example.peluqueria_app_desk;

import com.example.peluqueria_app_desk.Conexion.ConexionDB;
import com.example.peluqueria_app_desk.Modelos.*;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HistorialReservasController {
//CLIENTES FRECUENTES
    @FXML
    private TableColumn<HistorialModel, String> clNombreCliente;

    @FXML
    private TableColumn<HistorialModel, Integer> clTotalReservas;

    @FXML
    private TableColumn<HistorialModel, Integer> clidCliente;
    @FXML
    private TableView<HistorialModel> tblClientesFrecuentes;

    @FXML
    private TableView<HistorialHorasModel> tblHorariosDemandados;
    @FXML
    private TableColumn<HistorialHorasModel, Integer> clTotalReservasHorarios;
    @FXML
    private TableColumn<HistorialHorasModel, String> clHoraReserva;
    @FXML
    private TableColumn<HistorialHorasModel, Integer> clidHora;

    @FXML
    private TableColumn<HistorialReservasEmpleadoModel, Integer> clidEmpleado;
    @FXML
    private TableView<HistorialReservasEmpleadoModel> tblReservasPorEmpleado;
    @FXML
    private TableColumn<HistorialReservasEmpleadoModel, Integer> clTotalReservasEmpleados;
    @FXML
    private TableColumn<HistorialReservasEmpleadoModel, String> clNombreEmpleado;

    public void initialize(){
        cargarTablaClientesFrecuentes();
       cargarTablaHorariosDemandados();
        cargarTablaReservasPorEmpleado();
    }
    public void cargarTablaClientesFrecuentes() {
        clidCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        clNombreCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        clTotalReservas.setCellValueFactory(new PropertyValueFactory<>("totalReservas"));

        HistorialModel modelo = new HistorialModel();
        tblClientesFrecuentes.setItems(modelo.getClientesFrecuentes());

}
    public void cargarTablaHorariosDemandados() {
        clidHora.setCellValueFactory(new PropertyValueFactory<>("idHorario"));
        clHoraReserva.setCellValueFactory(new PropertyValueFactory<>("hora"));
        clTotalReservasHorarios.setCellValueFactory(new PropertyValueFactory<>("totalReservas2"));

        HistorialHorasModel modelo = new HistorialHorasModel();
        ObservableList<HistorialHorasModel> listaHorarios = modelo.getHorariosMasDemandados();
        tblHorariosDemandados.setItems(listaHorarios);
    }
    public void cargarTablaReservasPorEmpleado() {
        clidEmpleado.setCellValueFactory(new PropertyValueFactory<>("idEmpleado"));
        clNombreEmpleado.setCellValueFactory(new PropertyValueFactory<>("nombreEmpleado"));
        clTotalReservasEmpleados.setCellValueFactory(new PropertyValueFactory<>("totalReservas3"));

        HistorialReservasEmpleadoModel modelo = new HistorialReservasEmpleadoModel();
        tblReservasPorEmpleado.setItems(modelo.getReservasPorEmpleado());
    }
}