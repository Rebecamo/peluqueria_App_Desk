package com.example.peluqueria_app_desk;

import com.example.peluqueria_app_desk.Modelos.ReservasModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class GestionReservasController {
    @FXML
    private Button btnAceptar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnModificar;

    @FXML
    private TableColumn<ReservasModel, String> clEstadoReserva;

    @FXML
    private ComboBox<String> cmbEstadoReserva;

    @FXML
    private TableColumn<?, ?> colFechaReserva;

    @FXML
    private TableColumn<ReservasModel, String> colHora;

    @FXML
    private TableColumn<ReservasModel, Integer> colidCliente;

    @FXML
    private TableColumn<ReservasModel, Integer> colidEmpleado;

    @FXML
    private TableColumn<ReservasModel, Integer> colidReserva;

    @FXML
    private TableColumn<ReservasModel, Integer> colidServicio;

    @FXML
    private DatePicker dateFechaReserva;

    @FXML
    private TableView<ReservasModel> tbReservas;

    @FXML
    private TextField txtHoraReserva;

    @FXML
    private TextField txtidReserva;

    private ReservasModel reservas;

    public void initialize(){


        this.colidReserva.setCellValueFactory(new PropertyValueFactory<>("idReserva"));
        this.colidCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        this.colidEmpleado.setCellValueFactory(new PropertyValueFactory<>("idEmpleado"));
        this.colidServicio.setCellValueFactory(new PropertyValueFactory<>("idServicio"));
        this.colFechaReserva.setCellValueFactory(new PropertyValueFactory<>("fechaReserva"));
        this.colHora.setCellValueFactory(new PropertyValueFactory<>("horaReserva"));
        this.clEstadoReserva.setCellValueFactory(new PropertyValueFactory<>("estadoReserva"));
        this.cargarTabla();
        this.cargarComboBox();

        //Obtenemos el objeto al hacer click sobre la fila
        this.tbReservas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ReservasModel reservasTemporal = tbReservas.getSelectionModel().getSelectedItem();
                txtidReserva.setText(String.valueOf(reservasTemporal.getIdReserva()));
                dateFechaReserva.setValue(java.time.LocalDate.parse(reservasTemporal.getFechaReserva()));
                txtHoraReserva.setText(reservasTemporal.getHoraReserva());
                cmbEstadoReserva.setValue(reservasTemporal.getEstadoReserva());
            }
        });

    }


    public void cargarComboBox(){
        ObservableList<String> estadosReserva = FXCollections.observableArrayList(
                "Pendiente", "Confirmada", "Cancelada"
        );
        // Asignar roles al ComboBox
        cmbEstadoReserva.setItems(estadosReserva);
    }
    public void cargarTabla(){
        this.tbReservas.getItems().clear();
        reservas = new ReservasModel();
        ObservableList<ReservasModel>data_reservas = reservas.getReservas();
        for (ReservasModel item_reservas: data_reservas){
            this.tbReservas.getItems().add(item_reservas);
        }

    }

    @FXML
    private void clickAceptar(){
        if(tbReservas.getSelectionModel().getSelectedItem()!= null){
            ReservasModel reservaSeleccionada = tbReservas.getSelectionModel().getSelectedItem();
            reservaSeleccionada.setEstadoReserva("Confirmada");
            //editar la reserva en la base de datos
            int result = reservaSeleccionada.editReservas();
            if(result > 0){
                cargarTabla();
                limpiarCampos();
            }
        }

    }

    @FXML
    private void clickCancelar(){
        if(tbReservas.getSelectionModel().getSelectedItem()!= null){
            ReservasModel reservaSeleccionada = tbReservas.getSelectionModel().getSelectedItem();
            reservaSeleccionada.setEstadoReserva("Cancelada");
            //editar la reserva en la base de datos
            int result = reservaSeleccionada.editReservas();
            if (result > 0){
                cargarTabla();
                limpiarCampos();
            }
        }
    }

    @FXML
    private void clickModificar(){
        if(tbReservas.getSelectionModel().getSelectedItem()!=null){
            ReservasModel reservaSeleccionada = tbReservas.getSelectionModel().getSelectedItem();
            reservaSeleccionada.setFechaReserva(dateFechaReserva.getValue().toString());
            reservaSeleccionada.setHoraReserva(txtHoraReserva.getText());
            reservaSeleccionada.setEstadoReserva(cmbEstadoReserva.getValue());
            int result = reservaSeleccionada.editReservas();
            if (result > 0){
                cargarTabla();
                limpiarCampos();
            }
        }
    }




    private void limpiarCampos(){
        txtidReserva.clear();
        txtHoraReserva.clear();
        cmbEstadoReserva.getSelectionModel().clearSelection();
        dateFechaReserva.setValue(null);
    }




}
