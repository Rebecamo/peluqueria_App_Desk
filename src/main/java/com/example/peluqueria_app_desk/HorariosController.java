package com.example.peluqueria_app_desk;
import com.example.peluqueria_app_desk.Conexion.ConexionDB;
import com.example.peluqueria_app_desk.Modelos.HorariosModel;

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
import java.sql.*;

public class HorariosController {

    @FXML
    private TableView<HorariosModel> tblHorarios;

    @FXML
    private TableColumn<HorariosModel, Integer> clidHorario;

    @FXML
    private TableColumn<HorariosModel, Integer> clidEmpleado;

    @FXML
    private TableColumn<HorariosModel, String> clDiaSemana;

    @FXML
    private TableColumn<HorariosModel, String> clHoraEntrada;

    @FXML
    private TableColumn<HorariosModel, String> clHoraSalida;


    @FXML
    private ComboBox<String> cmbDiaSemana;

    @FXML
    private ComboBox<String> cmbHoraFin;

    @FXML
    private ComboBox<String> cmbHoraInicio;

    @FXML
    private TextField txtIdEmpleado;

    @FXML
    private TextField txtIdHorario;

    private ObservableList<HorariosModel> horariosList;
    private int empleadoId;


    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem menuItemAtras;


    @FXML
    private MenuItem menuItemGestionReservas;
    @FXML
    private MenuItem menuItemHistorial;
    @FXML
    private MenuItem menuItemInicio;
    private int idEmpleadoAutenticado;
    @FXML
    public void initialize() {
        // Configurar las columnas de la tabla
        clidHorario.setCellValueFactory(new PropertyValueFactory<>("idHorario"));
        clidEmpleado.setCellValueFactory(new PropertyValueFactory<>("idEmpleado"));
        clDiaSemana.setCellValueFactory(new PropertyValueFactory<>("diaSemana"));
        clHoraEntrada.setCellValueFactory(new PropertyValueFactory<>("horaInicio"));
        clHoraSalida.setCellValueFactory(new PropertyValueFactory<>("horaFin"));
        this.cargarTablaHorarios();
        this.tblHorarios.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                HorariosModel horarioTemporal = tblHorarios.getSelectionModel().getSelectedItem();
                if (horarioTemporal != null) {
                    txtIdHorario.setText(String.valueOf(horarioTemporal.getIdHorario()));
                    txtIdEmpleado.setText(String.valueOf(horarioTemporal.getIdEmpleado()));
                    cmbDiaSemana.setValue(horarioTemporal.getDiaSemana());
                    cmbHoraInicio.setValue(horarioTemporal.getHoraInicio());
                    cmbHoraFin.setValue(horarioTemporal.getHoraFin());
                }
            }
        });


        cmbDiaSemana.getItems().addAll("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado");

        // Rellenar valores para los ComboBox de horas (inicio y fin)
        cmbHoraInicio.getItems().addAll(
                "07:00 am","08:00 am", "09:00 am", "10:00 am", "11:00 am", "12:00 pm"

        );
        cmbHoraFin.getItems().addAll(

                "12:00 pm","01:00 pm","02:00 pm", "03:00 pm", "04:00 pm", "05:00 pm"

        );

        menuItemAtras.setOnAction(event -> gestion());
        menuItemGestionReservas.setOnAction(event -> gestionReservas());
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



    private void gestionReservas() {
        vistas("view_gestion_reservas.fxml");
    }

    private void historial() {
        vistas("view_historial_reservas.fxml");
    }

    private void gestion() {

        vistas("view_Gestion.fxml"); // Redirige al login
    }


    @FXML
    private void clickAgregarHorario() {
        if (cmbDiaSemana.getSelectionModel().isEmpty() || cmbHoraInicio.getSelectionModel().isEmpty() || cmbHoraFin.getSelectionModel().isEmpty()) {
            mostrarAlerta("Error", "Todos los campos del horario deben estar seleccionados.", Alert.AlertType.ERROR);
            return;
        }

        String diaSemana = cmbDiaSemana.getValue();
        String horaInicio = cmbHoraInicio.getValue();
        String horaFin = cmbHoraFin.getValue();

        if (horaInicio.compareTo(horaFin) >= 0) {
            mostrarAlerta("Error", "La hora de inicio debe ser anterior a la hora de fin.", Alert.AlertType.ERROR);
            return;
        }

        HorariosModel nuevoHorario = new HorariosModel();

        nuevoHorario.setDiaSemana(diaSemana);
        nuevoHorario.setHoraInicio(horaInicio);
        nuevoHorario.setHoraFin(horaFin);

        int result = nuevoHorario.saveHorario();
        if (result > 0) {
            mostrarAlerta("Éxito", "Horario agregado correctamente.", Alert.AlertType.INFORMATION);
            cargarTablaHorarios(); // Refresca la tabla
            limpiarCamposHorario();
        } else {
            mostrarAlerta("Error", "No se pudo agregar el horario.", Alert.AlertType.ERROR);
        }
    }
    public void cargarTablaHorarios() {
        tblHorarios.getItems().clear();
        HorariosModel horarios = new HorariosModel();
        ObservableList<HorariosModel> dataHorarios = horarios.getHorarios(); // Solo los del empleado autenticado
        tblHorarios.getItems().addAll(dataHorarios);
    }
    private void limpiarCamposHorario() {
        cmbDiaSemana.getSelectionModel().clearSelection();
        cmbHoraInicio.getSelectionModel().clearSelection();
        cmbHoraFin.getSelectionModel().clearSelection();
    }
    @FXML
    private void clickModificarHorario() {
        HorariosModel horarioSeleccionado = tblHorarios.getSelectionModel().getSelectedItem();

        if (horarioSeleccionado == null) {
            mostrarAlerta("Error", "Debes seleccionar un horario para modificar.", Alert.AlertType.ERROR);
            return;
        }

        if (cmbDiaSemana.getSelectionModel().isEmpty() || cmbHoraInicio.getSelectionModel().isEmpty() || cmbHoraFin.getSelectionModel().isEmpty()) {
            mostrarAlerta("Error", "Todos los campos del horario deben estar seleccionados.", Alert.AlertType.ERROR);
            return;
        }

        String diaSemana = cmbDiaSemana.getValue();
        String horaInicio = cmbHoraInicio.getValue();
        String horaFin = cmbHoraFin.getValue();

        if (horaInicio.compareTo(horaFin) >= 0) {
            mostrarAlerta("Error", "La hora de inicio debe ser anterior a la hora de fin.", Alert.AlertType.ERROR);
            return;
        }

        horarioSeleccionado.setDiaSemana(diaSemana);
        horarioSeleccionado.setHoraInicio(horaInicio);
        horarioSeleccionado.setHoraFin(horaFin);

        int result = horarioSeleccionado.editHorario();
        if (result > 0) {
            mostrarAlerta("Éxito", "Horario modificado correctamente.", Alert.AlertType.INFORMATION);
            cargarTablaHorarios(); // Refresca la tabla
            limpiarCamposHorario();
        } else {
            mostrarAlerta("Error", "No se pudo modificar el horario.", Alert.AlertType.ERROR);
        }
    }
    private void cargarHorarios() {
        horariosList.clear(); // Limpiar la lista antes de cargar datos

        String query = "SELECT * FROM tbl_horarios WHERE id_empleado = ?";
        try (Connection connection = ConexionDB.connection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, empleadoId); // Filtrar por empleado
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                horariosList.add(new HorariosModel(
                        resultSet.getInt("id_horario"),
                        resultSet.getInt("id_empleado"), // Incluye el id_empleado
                        resultSet.getString("dia_semana"),
                        resultSet.getTime("hora_inicio").toString(),
                        resultSet.getTime("hora_fin").toString()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tblHorarios.setItems(horariosList);
    }


    @FXML
    private void clickEliminarHorario() {
        HorariosModel horarioSeleccionado = tblHorarios.getSelectionModel().getSelectedItem();

        if (horarioSeleccionado == null) {
            mostrarAlerta("Error", "Debes seleccionar un horario para eliminar.", Alert.AlertType.ERROR);
            return;
        }

        int result = horarioSeleccionado.deleteHorario();
        if (result > 0) {
            mostrarAlerta("Éxito", "Horario eliminado correctamente.", Alert.AlertType.INFORMATION);
            cargarTablaHorarios(); // Refresca la tabla
        } else {
            mostrarAlerta("Error", "No se pudo eliminar el horario.", Alert.AlertType.ERROR);
        }
    }


    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    // ID del empleado que inició sesión





    }