package com.example.peluqueria_app_desk;


import com.example.peluqueria_app_desk.Conexion.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Autenticacion {
    private static int idEmpleadoLogueado; // ID del empleado autenticado

    public boolean iniciarSesion(String correo, String contraseña) {
        String consulta = "SELECT idempleado FROM tbl_empleados WHERE correo = ? AND contraseña = ?";
        try (Connection conn = ConexionDB.connection();
             PreparedStatement stmt = conn.prepareStatement(consulta)) {

            stmt.setString(1, correo);
            stmt.setString(2, contraseña);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                idEmpleadoLogueado = rs.getInt("id_empleado");
                return true; // Inicio de sesión exitoso
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Fallo en el inicio de sesión
    }

    public static int obtenerIdEmpleadoLogueado() {
        return idEmpleadoLogueado;
    }
}
