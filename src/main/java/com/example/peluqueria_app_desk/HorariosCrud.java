package com.example.peluqueria_app_desk;

import com.example.peluqueria_app_desk.Conexion.ConexionDB;
import com.example.peluqueria_app_desk.Modelos.HorarioModel;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;


public class HorariosCrud {
    private static final String INSERT_HORARIO = "INSERT INTO horarios (id_empleado, dia_semana, hora_inicio, hora_fin) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_HORARIO = "UPDATE horarios SET dia_semana = ?, hora_inicio = ?, hora_fin = ? WHERE id_horario = ?";
    private static final String DELETE_HORARIO = "DELETE FROM horarios WHERE id_horario = ?";

    // Método para agregar un horario
    public void agregarHorario(HorarioModel horario) throws SQLException {
        try (Connection con = ConexionDB.connection();
             PreparedStatement pst = con.prepareStatement(INSERT_HORARIO)) {
            pst.setInt(1, horario.getIdEmpleado()); // Cambia esto según tu modelo
            pst.setString(2, horario.getDiaSemana());
            pst.setTime(3, Time.valueOf(horario.getHoraInicio()));
            pst.setTime(4, Time.valueOf(horario.getHoraFin()));
            pst.executeUpdate();
        }
    }


    public void actualizarHorario(HorarioModel horario) throws SQLException {
        try (Connection con = ConexionDB.connection();
             PreparedStatement pst = con.prepareStatement(UPDATE_HORARIO)) {
            pst.setString(1, horario.getDiaSemana());
            pst.setTime(2, Time.valueOf(horario.getHoraInicio()));
            pst.setTime(3, Time.valueOf(horario.getHoraFin()));
            pst.setInt(4, horario.getIdHorario());
            pst.executeUpdate();
        }
    }


    public void eliminarHorario(int idHorario) throws SQLException {
        try (Connection con = ConexionDB.connection();
             PreparedStatement pst = con.prepareStatement(DELETE_HORARIO)) {
            pst.setInt(1, idHorario);
            pst.executeUpdate();
        }
    }



}
