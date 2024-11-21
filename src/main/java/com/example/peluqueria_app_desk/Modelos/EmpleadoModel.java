package com.example.peluqueria_app_desk.Modelos;

import com.example.peluqueria_app_desk.Conexion.ConexionDB;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmpleadoModel {
    private String nombre;
    private String apellidos;

    private String correo;

    private String telefono;
    private String direccion;
    private String rol;
    private int edad;
    private String pass;

    public EmpleadoModel(String nombre, String apellidos, String correo, String telefono, String direccion, String rol, int edad, String pass) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.rol = rol;
        this.edad = edad;
        this.pass = pass;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int saveEmpleado() {
        try (Connection con = ConexionDB.connection();
             PreparedStatement preparedStatement = con.prepareStatement(
                     "INSERT INTO tbl_Empleados(nombre, apellidos, correo, telefono, direccion, rol, edad, pass) VALUES(?, ?, ?, ?, ?, ?, ?, ?)"
             )) {

            preparedStatement.setString(1, this.nombre);
            preparedStatement.setString(2, this.apellidos);

            preparedStatement.setString(3, this.correo);

            preparedStatement.setString(4, this.telefono);
            preparedStatement.setString(5, this.direccion);
            preparedStatement.setString(6, this.rol);
            preparedStatement.setInt(7, this.edad);
            preparedStatement.setString(8, this.pass);
            int retorno = preparedStatement.executeUpdate();
            return retorno;

        } catch (SQLException e) {
            System.err.println("Error al guardar el empleado: " + e.getMessage());
            return 0;
        }
    }


}

