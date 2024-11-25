package com.example.peluqueria_app_desk;

import com.example.peluqueria_app_desk.Modelos.ReservasModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class GestionReservasController {
    @FXML
    private Button btnAceptar;
    @FXML
    private ComboBox<String> cmbHoraReserva;
    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnModificar;

    @FXML
    private TableColumn<ReservasModel, String> clEstadoReserva;

    @FXML
    private ComboBox<String> cmbEstadoReserva;

    @FXML
    private TableColumn<ReservasModel, LocalDate> clFechaReserva;

    @FXML
    private TableColumn<ReservasModel, String> clHoraReserva;

    @FXML
    private TableColumn<ReservasModel, Integer> clidCliente;

    @FXML
    private TableColumn<ReservasModel, Integer> clidEmpleado;

    @FXML
    private TableColumn<ReservasModel, Integer> clidReserva;

    @FXML
    private TableColumn<ReservasModel, Integer> clidServicio;

    @FXML
    private DatePicker dateFechaReserva;

    @FXML
    private TableView<ReservasModel> tbReservas;

    @FXML
    private TextField txtHoraReserva;

    @FXML
    private TextField txtidReserva;
    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem menuItemGestion;

    @FXML
    private MenuItem menuItemHistorial;

    @FXML
    private MenuItem menuItemHorarios;

    @FXML
    private MenuItem menuItemInicio;

    private ReservasModel reservas;

    public void initialize() {

        this.clidReserva.setCellValueFactory(new PropertyValueFactory<>("idReserva"));
        this.clidCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        this.clidEmpleado.setCellValueFactory(new PropertyValueFactory<>("idEmpleado"));
        this.clidServicio.setCellValueFactory(new PropertyValueFactory<>("idServicio"));
        this.clFechaReserva.setCellValueFactory(new PropertyValueFactory<>("fechaReserva"));
        this.clHoraReserva.setCellValueFactory(new PropertyValueFactory<>("horaReserva"));
        this.clEstadoReserva.setCellValueFactory(new PropertyValueFactory<>("estadoReserva"));
        this.cargarTabla();
        this.cargarComboBox();

        //Obtenemos el objeto al hacer click sobre la fila
        this.tbReservas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ReservasModel reservasTemporal = tbReservas.getSelectionModel().getSelectedItem();
                txtidReserva.setText(String.valueOf(reservasTemporal.getIdReserva()));
                dateFechaReserva.setValue(reservasTemporal.getFechaReserva());
                txtHoraReserva.setText(reservasTemporal.getHoraReserva());
                cmbEstadoReserva.setValue(reservasTemporal.getEstadoReserva());
            }
        });

        menuItemHorarios.setOnAction(event -> gestionHorario());
        menuItemGestion.setOnAction(event -> gestion());
        menuItemHistorial.setOnAction(event -> historial());
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



    private void historial() {
        vistas("view_historial_reservas.fxml");
    }

    private void gestion() {

        vistas("view_Gestion.fxml"); // Redirige al login
    }
    // para pasasr el id del empleado

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

            if("Pendiente".equals(reservaSeleccionada.getEstadoReserva())){
                reservaSeleccionada.setEstadoReserva("Confirmada");
                //editar la reserva en la base de datos
                int result = reservaSeleccionada.editReservas();
                if(result > 0){
                    cargarTabla();
                    limpiarCampos();
                }
            }else{
                showAlert("Infor", "La reserva  no está pendiente, no se puede confirmar.", Alert.AlertType.INFORMATION);
                /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setContentText("La reserva no está pendiente, no se puede confirmar");
                alert.show();*/

            }

        }

    }

    @FXML
    private void clickCancelar(){
        if(tbReservas.getSelectionModel().getSelectedItem()!= null){
            ReservasModel reservaSeleccionada = tbReservas.getSelectionModel().getSelectedItem();
            if("Pendiente".equals(reservaSeleccionada.getEstadoReserva()) || "Confirmada".equals(reservaSeleccionada.getEstadoReserva())){
                reservaSeleccionada.setEstadoReserva("Cancelada");
                //editar la reserva en la base de datos
                int result = reservaSeleccionada.editReservas();
                if (result > 0){
                    cargarTabla();
                    limpiarCampos();
                }
            }else{
                showAlert("Informacion", "La reserva ya está cancelada.", Alert.AlertType.INFORMATION);
                /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Informacion");
                alert.setContentText("La reserva ya esta cancelada");
                alert.show();*/
            }

        }
    }

    @FXML
    private void clickModificar(){
        if(tbReservas.getSelectionModel().getSelectedItem()!=null){
            ReservasModel reservaSeleccionada = tbReservas.getSelectionModel().getSelectedItem();
            //obtener la hora de la caja de texto y asegurar que esta en el formato correcto
            String nuevaHora = txtHoraReserva.getText();
            //validacion para el formato de hora (HH:mm)
            if(nuevaHora.matches("\\d{2}:\\d{2}")){
                reservaSeleccionada.setHoraReserva(nuevaHora);
            }else{
                showAlert("Error","Por favor, ingresa una hora valida en formato HH:mm", Alert.AlertType.ERROR);
                return;
            }
            reservaSeleccionada.setFechaReserva(dateFechaReserva.getValue());
            //reservaSeleccionada.setHoraReserva(txtHoraReserva.getText());
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
            // Limpiar otros campos...

        cmbEstadoReserva.getSelectionModel().clearSelection();
        dateFechaReserva.setValue(null);
    }

    private void showAlert(String title, String message, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }





}
