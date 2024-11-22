package com.example.peluqueria_app_desk;
import com.example.peluqueria_app_desk.HorarioS;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

public class HorariosController {

    @FXML
    private ListView<String> diasListView;

    @FXML
    private TextField horaInicioField;

    @FXML
    private TextField horaFinField;

    private HorarioS horariosService = new HorarioS();

    @FXML
    public void initialize() {
        // Configurar los días en el ListView
        diasListView.getItems().addAll("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo");
        diasListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void registrarHorarios() {
        List<String> diasSeleccionados = diasListView.getSelectionModel().getSelectedItems();
        if (diasSeleccionados.isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING, "Debe seleccionar al menos un día.");
            alerta.show();
            return;
        }

        String horaInicio = horaInicioField.getText();
        String horaFin = horaFinField.getText();

        int idEmpleado = Autenticacion.obtenerIdEmpleadoLogueado();

        for (String dia : diasSeleccionados) {
            if (horariosService.registrarHorario(idEmpleado, dia, horaInicio, horaFin)) {
                System.out.println("Horario registrado para " + dia);
            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "Error al registrar el horario para " + dia);
                alerta.show();
            }
        }

        Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Horarios registrados con éxito.");
        alerta.show();
    }


    }

