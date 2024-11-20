package com.example.peluqueria_app_desk.Modelos;

import com.example.peluqueria_app_desk.Conexion.ConexionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class EmpleadoModel {
    private  int idEmpleado;
    private String nombreEmpleado;
    private String apellidoEmpleado;
    private String correoEmpleado;
    private String telefonoEmpleado;
    private String direccionEmpleado;
    private String rolEmpleado;

    public EmpleadoModel() {
    }

    public EmpleadoModel(int idEmpleado, String nombreEmpleado, String apellidoEmpleado, String telefonoEmpleado, String correoEmpleado, String direccionEmpleado, String rolEmpleado) {
        this.idEmpleado = idEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.apellidoEmpleado = apellidoEmpleado;
        this.telefonoEmpleado = telefonoEmpleado;
        this.correoEmpleado = correoEmpleado;
        this.direccionEmpleado = direccionEmpleado;
        this.rolEmpleado = rolEmpleado;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getApellidoEmpleado() {
        return apellidoEmpleado;
    }

    public void setApellidoEmpleado(String apellidoEmpleado) {
        this.apellidoEmpleado = apellidoEmpleado;
    }

    public String getCorreoEmpleado() {
        return correoEmpleado;
    }

    public void setCorreoEmpleado(String correoEmpleado) {
        this.correoEmpleado = correoEmpleado;
    }

    public String getTelefonoEmpleado() {
        return telefonoEmpleado;
    }

    public void setTelefonoEmpleado(String telefonoEmpleado) {
        this.telefonoEmpleado = telefonoEmpleado;
    }

    public String getDireccionEmpleado() {
        return direccionEmpleado;
    }

    public void setDireccionEmpleado(String direccionEmpleado) {
        this.direccionEmpleado = direccionEmpleado;
    }

    public String getRolEmpleado() {
        return rolEmpleado;
    }

    public void setRolEmpleado(String rolEmpleado) {
        this.rolEmpleado = rolEmpleado;
    }

    public ObservableList<EmpleadoModel>getEmpleados(){
        Connection connection = ConexionDB.connection();
        ObservableList<EmpleadoModel> dataEmpleado = FXCollections.observableArrayList();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT *FROM tbl_empleados");
            while (resultSet.next()){
                EmpleadoModel empleadoModel = new EmpleadoModel(
                        resultSet.getInt("idempleado"),
                        resultSet.getString("nombre"),
                        resultSet.getString("apellido"),
                        resultSet.getString("correo"),
                        resultSet.getString("telefono"),
                        resultSet.getString("direccion"),
                        resultSet.getString("rol")
                );
                dataEmpleado.add(empleadoModel);
            }
            return dataEmpleado;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //return null;

    }

    public int saveEmpleados(){
        Connection con = ConexionDB.connection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = con.prepareStatement("INSERT INTO tbl_empleados(nombre, apellido, correo, telefono, direccion, rol) VALUES (?, ?, ?, ?, ?, ?);");
            preparedStatement.setString(1, this.nombreEmpleado);
            preparedStatement.setString(2, this.apellidoEmpleado);
            preparedStatement.setString(3, this.correoEmpleado);
            preparedStatement.setString(4, this.telefonoEmpleado);
            preparedStatement.setString(5, this.direccionEmpleado);
            preparedStatement.setString(6, this.rolEmpleado);
            int retorno = preparedStatement.executeUpdate();
            return retorno;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int editEmpleados(){
        Connection con = ConexionDB.connection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = con.prepareStatement("UPDATE tbl_empleados SET nombre=?, apellido=?, correo=?, telefono=?, direccion=?, rol=? WHERE idempleado=?;");
            preparedStatement.setString(1, this.nombreEmpleado);
            preparedStatement.setString(2, this.apellidoEmpleado);
            preparedStatement.setString(3, this.correoEmpleado);
            preparedStatement.setString(4, this.telefonoEmpleado);
            preparedStatement.setString(5, this.direccionEmpleado);
            preparedStatement.setString(6, this.rolEmpleado);
            preparedStatement.setInt(7, this.idEmpleado);
            int retorno = preparedStatement.executeUpdate();
            return retorno;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteEmpleados(int idEmpleado){
        Connection connection = ConexionDB.connection();
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM tbl_empleados WHERE idempleado=? ;");
            statement.setInt(1, this.idEmpleado);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }







}
