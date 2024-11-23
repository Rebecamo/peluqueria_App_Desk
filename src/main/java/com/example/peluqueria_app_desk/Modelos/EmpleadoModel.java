package com.example.peluqueria_app_desk.Modelos;

import com.example.peluqueria_app_desk.Conexion.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EmpleadoModel {
    private int idEmpleado;
    private String nombres;
    private String apellidos;
    private int edad;
    private String direccion;
    private String telefono;
    private String correo;
    private String contraseña;

    public EmpleadoModel(int idEmpleado, String nombres, String apellidos, int edad, String direccion, String telefono, String correo, String contraseña) {
        this.idEmpleado = idEmpleado;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.edad = edad;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.contraseña = contraseña;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public int validarCredenciales(String correo, String contraseña) {
        try (Connection connection = ConexionDB.getConnection()) {
            String query = "SELECT id_empleado FROM tblEmpleados WHERE correo = ? AND contraseña = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, correo); // Sustituir el primer "?" por el correo
            statement.setString(2, contraseña); // Sustituir el segundo "?" por la contraseña

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Si encuentra un empleado, retornar su ID
                return resultSet.getInt("id_empleado");
            } else {
                // Retornar -1 si las credenciales son incorrectas
                return -1;
            }
        } catch (Exception e) {
            // Manejar errores y retornar -1 en caso de fallo
            e.printStackTrace();
            return -1;
        }
    }
}
