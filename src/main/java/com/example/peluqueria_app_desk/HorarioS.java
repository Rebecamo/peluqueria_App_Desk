package com.example.peluqueria_app_desk;

import com.example.peluqueria_app_desk.Conexion.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class HorarioS {
    public boolean registrarHorario(int idEmpleado, String diaSemana, String horaInicio, String horaFin) {
        String query = "INSERT INTO tbl_horarios (idempleado, dia_semana, hora_inicio, hora_fin) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionDB.connection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idEmpleado);
            stmt.setString(2, diaSemana);
            stmt.setString(3, horaInicio);
            stmt.setString(4, horaFin);

            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
