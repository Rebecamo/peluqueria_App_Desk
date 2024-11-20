package com.example.peluqueria_app_desk;

import com.example.peluqueria_app_desk.Conexion.ConexionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestionDeHorariosController {
    @FXML
    private ComboBox<String> cmbHoraFin;
    @FXML
    private ComboBox<String> cmbDiaSemana;
    @FXML
    private ComboBox<String> cmbHoraInicio;
    @FXML
    private ComboBox<String> cmbDisponibilidad;
    @FXML
    private ComboBox<String > cmbServicios;

    public void guardarHorario() {
        String servicioSeleccionado = cmbServicios.getValue();
        String horaInicio = cmbHoraInicio.getValue();
        String horaFin = cmbHoraFin.getValue();
        String disponibilidad = cmbDisponibilidad.getValue();
        String diaSemana = cmbDiaSemana.getValue(); // Si tienes un ComboBox para días

        try (Connection connection = ConexionDB.connection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO tbl_HorariosDisponibles (idEmpleado, diaSemana, horaInicio, horaFin, disponible) VALUES (?, ?, ?, ?, ?)")) {

            statement.setInt(1, idEmpleado);
            statement.setString(2, diaSemana);
            statement.setTime(3, Time.valueOf(horaInicio + ":00"));
            statement.setTime(4, Time.valueOf(horaFin + ":00"));
            statement.setString(5, disponibilidad);

            statement.executeUpdate();
            System.out.println("Horario guardado correctamente");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void initialize(){
        ObservableList<String> horas = FXCollections.observableArrayList(
                "08:00", "08:30", "09:00", "09:30", "10:00", "10:30",
                "11:00", "11:30", "12:00", "12:30", "13:00", "13:30",
                "14:00", "14:30", "15:00", "15:30", "16:00");
        cmbHoraInicio.setItems(horas);
        cmbHoraFin.setItems(horas);
        cmbDisponibilidad.setItems(FXCollections.observableArrayList("Disponible","No disponible" ));
        cmbDiaSemana.setItems(FXCollections.observableArrayList("Lunes","Martes","Miércoles","Jueves","Viernes","Sábado"));

        List<String> servicios = new ArrayList<>();
        try (Connection connection = ConexionDB.connection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT nombreServicio FROM tbl_Servicios")) {

            while (resultSet.next()) {
                servicios.add(resultSet.getString("nombreServicio"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        cmbServicios.setItems(FXCollections.observableArrayList(servicios));
        //para guardar boton


    }


}
