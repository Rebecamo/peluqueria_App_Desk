package com.example.peluqueria_app_desk;

import com.example.peluqueria_app_desk.Modelos.ReservasModel;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.chart.XYChart;

public class HistorialReservasController {

    @FXML
    private TableColumn<ReservasModel, String> clEstadoReserva;

    @FXML
    private TableColumn<ReservasModel, String> clFechaReserva;

    @FXML
    private TableColumn<ReservasModel, String > clHoraReserva;

    @FXML
    private LineChart<String, Number> grafTendencias;

    @FXML
    private ListView<String> listClientesFrecuentes;

    @FXML
    private ListView<String> listHorariosDemandados;
    private CategoryAxis xAxis;
    private NumberAxis yAxis;

    @FXML
    private TableView<ReservasModel> tbViewRservas;

    private ReservasModel reservasModel;



    public HistorialReservasController(){
        reservasModel = new ReservasModel();
    }

    //Metodo para inicializar el controlador y cargar los datos
    public void initialize(){
        this.clFechaReserva.setCellValueFactory(new PropertyValueFactory<>("fechaReserva"));
        this.clHoraReserva.setCellValueFactory(new PropertyValueFactory<>("horaReserva"));
        this.clEstadoReserva.setCellValueFactory(new PropertyValueFactory<>("estadoReserva"));
        cargarTabla();
        cargarClientesFrecuentes();
        cargarHorariosDemandados();

    }

    private void cargarTabla(){
        tbViewRservas.setItems(reservasModel.getReservas());
    }

    private void cargarClientesFrecuentes(){
        listClientesFrecuentes.setItems(reservasModel.getClientesFrecuentes());
    }

    private void cargarHorariosDemandados(){
        listHorariosDemandados.setItems(reservasModel.getHorariosDemandados());
    }

    private void cargarTendencias(){
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
/*
        grafTendencias.setXAxis(xAxis);
        grafTendencias.setYAxis(yAxis);*/

        XYChart.Series<String, Number> series = new XYChart.Series<>();

    }


}
