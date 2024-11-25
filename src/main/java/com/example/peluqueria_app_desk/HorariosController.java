package com.example.peluqueria_app_desk;
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

import java.time.LocalTime;

import java.time.format.DateTimeParseException;


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
    private TextField txtIdEmpleado;
    @FXML
    private TextField txtIdHorario;
    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem menuItemAtras;
    @FXML
    private TextField txtHoraFin;
    @FXML
    private TextField txtHoraInicio;
    @FXML
    private MenuItem menuItemGestionReservas;
    @FXML
    private MenuItem menuItemHistorial;
    @FXML
    private MenuItem menuItemInicio;
    private final ObservableList<String> diasSemana = FXCollections.observableArrayList(
            "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"
    );

    @FXML
    public void initialize() {
        // Configurar las columnas de la tabla

        this.clidHorario.setCellValueFactory(new PropertyValueFactory<>("idHorario"));
        this.clidEmpleado.setCellValueFactory(new PropertyValueFactory<>("idEmpleado"));
        this.clDiaSemana.setCellValueFactory(new PropertyValueFactory<>("diaSemana"));
        this.clHoraEntrada.setCellValueFactory(new PropertyValueFactory<>("horaInicio"));
        this.clHoraSalida.setCellValueFactory(new PropertyValueFactory<>("horaFin"));
        cmbDiaSemana.setItems(diasSemana);
        this.cargarTabla(); // Método que obtiene los datos de horarios y los añade a la tabla

// Configurar el evento para seleccionar una fila
        this.tblHorarios.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                HorariosModel horarioSeleccionado = tblHorarios.getSelectionModel().getSelectedItem();

                // Asignar los valores del objeto seleccionado a los campos de la interfaz
                txtIdHorario.setText(String.valueOf(horarioSeleccionado.getIdHorario()));
                txtIdEmpleado.setText(String.valueOf(horarioSeleccionado.getIdEmpleado()));
                cmbDiaSemana.setValue(horarioSeleccionado.getDiaSemana());
                txtHoraInicio.setText(horarioSeleccionado.getHoraInicio());
                txtHoraFin.setText(horarioSeleccionado.getHoraFin());


            }
        });
        menuItemAtras.setOnAction(event -> gestion());
        menuItemGestionReservas.setOnAction(event -> gestionReservas());
        menuItemHistorial.setOnAction(event -> historial());
        menuItemInicio.setOnAction(event -> inicio());
    }

    public void cargarTabla() {
        try {
            // Crear una instancia del modelo y obtener la lista de horarios
            HorariosModel modelo = new HorariosModel();
            ObservableList<HorariosModel> listaHorarios = modelo.getHorarios(0); // 0 significa cargar todos los horarios

            // Asignar la lista al TableView
            tblHorarios.setItems(listaHorarios);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudieron cargar los horarios", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void guardarHorario() {
        try {
            // Validar entradas
            if (txtIdEmpleado.getText().isEmpty() || cmbDiaSemana.getValue() == null ||
                    txtHoraInicio.getText().isEmpty() || txtHoraFin.getText().isEmpty()) {
                mostrarAlerta("Advertencia", "Todos los campos son obligatorios", Alert.AlertType.WARNING);
                return;
            }
            LocalTime horaInicio;
            LocalTime horaFin;
            try {
                horaInicio = LocalTime.parse(txtHoraInicio.getText()); // Parsear horaInicio
                horaFin = LocalTime.parse(txtHoraFin.getText());       // Parsear horaFin
            } catch (DateTimeParseException e) {
                mostrarAlerta("Error", "Las horas deben estar en el formato HH:mm (por ejemplo, 09:00 )", Alert.AlertType.ERROR);
                return;
            }
            if (horaFin.isBefore(horaInicio.plusHours(6))) {
                mostrarAlerta("Error", "La jornada laboral debe ser de al menos 6 horas", Alert.AlertType.ERROR);
                return;
            }


            // Crear una instancia del modelo
            HorariosModel nuevoHorario = new HorariosModel();
            nuevoHorario.setIdEmpleado(Integer.parseInt(txtIdEmpleado.getText()));
            nuevoHorario.setDiaSemana(cmbDiaSemana.getValue());
            nuevoHorario.setHoraInicio(txtHoraInicio.getText());
            nuevoHorario.setHoraFin(txtHoraFin.getText());

            // Guardar en la base de datos
            int resultado = nuevoHorario.saveHorario();
            if (resultado > 0) {
                mostrarAlerta("Éxito", "Horario guardado correctamente", Alert.AlertType.INFORMATION);
                cargarTabla(); // Actualizar la tabla
                limpiarCampos(); // Limpiar los campos
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El ID del empleado debe ser un número", Alert.AlertType.ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo guardar el horario", Alert.AlertType.ERROR);
        }
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

    //clik para modificar listo
    @FXML
    private void clickModificarHorario() {
        // Verificar si se ha seleccionado un horario en la tabla
        if (tblHorarios.getSelectionModel().getSelectedItem() != null) {
            HorariosModel horarioSeleccionado = tblHorarios.getSelectionModel().getSelectedItem();

            // Obtener las horas ingresadas en los TextField
            String nuevaHoraInicio = txtHoraInicio.getText();
            String nuevaHoraFin = txtHoraFin.getText();

            // Validación del formato HH:mm para ambas horas
            if (!nuevaHoraInicio.matches("\\d{2}:\\d{2}") || !nuevaHoraFin.matches("\\d{2}:\\d{2}")) {
                mostrarAlerta("Error", "Por favor, ingresa las horas en el formato HH:mm (ejemplo: 09:00)", Alert.AlertType.ERROR);
                return;
            }

            try {
                // Convertir las horas ingresadas a objetos LocalTime
                LocalTime horaInicio = LocalTime.parse(nuevaHoraInicio);
                LocalTime horaFin = LocalTime.parse(nuevaHoraFin);

                // Verificar que la hora de inicio sea anterior a la hora de fin
                if (horaInicio.isAfter(horaFin) || horaInicio.equals(horaFin)) {
                    mostrarAlerta("Error", "La hora de inicio debe ser anterior a la hora de fin.", Alert.AlertType.ERROR);
                    return;
                }

                // Actualizar los valores en el modelo seleccionado
                horarioSeleccionado.setHoraInicio(nuevaHoraInicio);
                horarioSeleccionado.setHoraFin(nuevaHoraFin);
                horarioSeleccionado.setDiaSemana(cmbDiaSemana.getValue()); // Día seleccionado en ComboBox


                int result = horarioSeleccionado.editHorario();
                if (result > 0) {
                    mostrarAlerta("Éxito", "Horario modificado correctamente.", Alert.AlertType.INFORMATION);
                    cargarTabla(); // Actualizar la tabla
                    limpiarCampos(); // Limpiar los campos de texto y ComboBox
                } else {
                    mostrarAlerta("Error", "No se pudo modificar el horario.", Alert.AlertType.ERROR);
                }
            } catch (DateTimeParseException e) {
                mostrarAlerta("Error", "Formato de hora inválido. Por favor, ingresa horas válidas en formato HH:mm.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "Por favor, selecciona un horario en la tabla para modificar.", Alert.AlertType.WARNING);
        }
    }
    //limpiar campos listo

    private void limpiarCampos() {
        txtIdEmpleado.clear();
        cmbDiaSemana.getSelectionModel().clearSelection();
        txtHoraInicio.clear();
        txtHoraFin.clear();
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
            cargarTabla(); // Refresca la tabla
        } else {
            mostrarAlerta("Error", "No se pudo eliminar el horario.", Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}
