package com.example.peluqueria_app_desk.Modelos;

import com.example.peluqueria_app_desk.Conexion.ConexionDB;
import com.example.peluqueria_app_desk.HistorialReservasController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HistorialHorasModel {
    private int idHorario;
    private String hora;
    private int totalReservas2;

    public HistorialHorasModel() {
    }

    public HistorialHorasModel(int idHorario, String hora, int totalReservas2) {
        this.idHorario = idHorario;
        this.hora = hora;
        this.totalReservas2 = totalReservas2;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getTotalReservas2() {
        return totalReservas2;
    }

    public void setTotalReservas2(int totalReservas2) {
        this.totalReservas2 = totalReservas2;
    }

    public ObservableList<HistorialHorasModel> getHorariosMasDemandados() {
        ObservableList<HistorialHorasModel> horarios = FXCollections.observableArrayList();
        Connection connection = ConexionDB.connection();
        try {
            Statement statement = connection.createStatement();
            String consulta = "SELECT h.id_horario, h.hora_inicio, COUNT(r.id_reserva) AS total_reservas " +
                    "FROM tbl_horarios h " +
                    "LEFT JOIN tbl_reservas r ON h.hora_inicio = r.hora_reserva " +
                    "GROUP BY h.id_horario, h.hora_inicio " +
                    "ORDER BY total_reservas DESC;";
            ResultSet resultSet = statement.executeQuery(consulta);
            while (resultSet.next()) {
                HistorialHorasModel horario = new HistorialHorasModel(
                        resultSet.getInt("id_horario"),
                        resultSet.getTime("hora_inicio").toString(),
                        resultSet.getInt("total_reservas")
                );
                horarios.add(horario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener horarios más demandados");
        }
        return horarios;
    }

}
