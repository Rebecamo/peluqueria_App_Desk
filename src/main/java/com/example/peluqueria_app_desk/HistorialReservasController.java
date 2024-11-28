package com.example.peluqueria_app_desk;


import com.example.peluqueria_app_desk.Modelos.*;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class HistorialReservasController {
    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem menuItemAtras;

    @FXML
    private MenuItem menuItemGestionHorario;

    @FXML
    private MenuItem menuItemGestionReservas;
    @FXML
    private MenuItem menuItemCerrarSesion;
    @FXML
    private MenuItem menuItemInicio;
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
        menuItemCerrarSesion.setOnAction(event -> cerrarSesion());
        menuItemGestionHorario.setOnAction(event -> gestionHorario());
        menuItemGestionReservas.setOnAction(event -> gestionReservas());
        menuItemAtras.setOnAction(event -> historial());
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

    private void historial() {
        vistas("view_historial_reservas.fxml");
    }

    private void cerrarSesion() {
        System.out.println("Cerrando sesión...");
        vistas("view_inicio.fxml"); // Redirige al login
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