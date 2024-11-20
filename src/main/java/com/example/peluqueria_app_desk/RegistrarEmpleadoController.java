package com.example.peluqueria_app_desk;

import javafx.fxml.FXML;

import java.awt.event.ActionEvent;
import java.sql.*;

public class RegistrarEmpleadoController {
    @FXML
    private void registrarEmpleado(ActionEvent event) {
        
        String nombre = txtNombre.getText();
        String apellidos = txtApellidos.getText();
        int edad = Integer.parseInt(txtEdad.getText());
        String correo = txtCorreo.getText();
        String contrasena = txtContrasena.getText();
        String telefono = txtTelefono.getText();
        String direccion = txtDireccion.getText();
        String rol = cmbRol.getValue(); // Asumiendo que usas un ComboBox

        String sql = "INSERT INTO empleados (nombre, apellidos, edad, correo, contrasena, telefono, direccion, rol) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING id_empleado";

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/db_practicaParcial3", "usuario", "contraseña");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setString(2, apellidos);
            stmt.setInt(3, edad);
            stmt.setString(4, correo);
            stmt.setString(5, contrasena);
            stmt.setString(6, telefono);
            stmt.setString(7, direccion);
            stmt.setString(8, rol);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int idEmpleado = rs.getInt("id_empleado");
                System.out.println("Empleado registrado con ID: " + idEmpleado);

                // Guardar el ID en un DataStore o pasarlo al siguiente formulario
                DataStore.setEmpleadoId(String.valueOf(idEmpleado));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
