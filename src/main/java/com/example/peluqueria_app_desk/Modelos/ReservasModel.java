package com.example.peluqueria_app_desk.Modelos;

import com.example.peluqueria_app_desk.Conexion.ConexionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ReservasModel {

    private int idReserva;
    private int idCliente;
    private int idEmpleado;
    private int idServicio;
    private String fechaReserva;
    private String horaReserva;
    private String estadoReserva;

    public ReservasModel() {
    }

    public ReservasModel(int idReserva, int idCliente, int idEmpleado, int idServicio, String fechaReserva, String horaReserva, String estadoReserva) {
        this.idReserva = idReserva;
        this.idCliente = idCliente;
        this.idEmpleado = idEmpleado;
        this.idServicio = idServicio;
        this.fechaReserva = fechaReserva;
        this.horaReserva = horaReserva;
        this.estadoReserva = estadoReserva;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public String getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(String fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public String getHoraReserva() {
        return horaReserva;
    }

    public void setHoraReserva(String horaReserva) {
        this.horaReserva = horaReserva;
    }

    public String getEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(String estadoReserva) {
        this.estadoReserva = estadoReserva;
    }

    public ObservableList<ReservasModel> getReservas(){
        Connection connection = ConexionDB.connection();
        ObservableList<ReservasModel>dataReservas = FXCollections.observableArrayList();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT *FROM tbl_reservas;");
            while(resultSet.next()){
                ReservasModel reservasModel = new ReservasModel();
                reservasModel.setIdReserva(resultSet.getInt("id_reserva"));
                reservasModel.setIdCliente(resultSet.getInt("id_cliente"));
                reservasModel.setIdEmpleado(resultSet.getInt("id_empleado"));
                reservasModel.setIdServicio(resultSet.getInt("id_servicio"));
                reservasModel.setFechaReserva(resultSet.getString("fecha_reserva"));
                Time horaReserva = resultSet.getTime("hora_reserva");
                reservasModel.setEstadoReserva(resultSet.getString("estado"));
                dataReservas.add(reservasModel);
            }
            return dataReservas;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int saveReservas(){
        Connection con = ConexionDB.connection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement("INSERT INTO tbl_reservas(idcliente, idempleado, idservicio, fechareserva, horareserva, estadoreserva) VALUES (?, ?, ?, ?, ?, ?);");
            preparedStatement.setInt(1,this.idCliente );
            preparedStatement.setInt(2,this.idEmpleado);
            preparedStatement.setInt(3,this.idServicio);
            preparedStatement.setString(4, this.fechaReserva);
            preparedStatement.setTime(5, java.sql.Time.valueOf(this.horaReserva));
            preparedStatement.setString(6, this.estadoReserva);
            int retorno = preparedStatement.executeUpdate();
            return retorno;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int editReservas(){
        Connection con = ConexionDB.connection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement("UPDATE tbl_reservas SET id_cliente=?, id_empleado=?, id_servicio=?, fecha_reserva=?, hora_reserva=?, estado=? WHERE id_reserva=?;");
            preparedStatement.setInt(1, this.idCliente);
            preparedStatement.setInt(2, this.idEmpleado);
            preparedStatement.setInt(3, this.idServicio);
            preparedStatement.setString(4, this.fechaReserva);
            preparedStatement.setTime(5, java.sql.Time.valueOf(this.horaReserva));
            preparedStatement.setString(6, this.estadoReserva);
            preparedStatement.setInt(7, this.idReserva);
            int retorno = preparedStatement.executeUpdate();
            return retorno;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteReservas(){
        Connection con = ConexionDB.connection();
        try {
            PreparedStatement statement = con.prepareStatement("DELETE FROM tbl_reservas WHERE id_reserva =?;");
            statement.setInt(1, idReserva);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }





}
