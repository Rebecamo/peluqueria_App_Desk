package com.example.peluqueria_app_desk.Modelos;

import com.example.peluqueria_app_desk.Conexion.ConexionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReservasModel {

    private int idReserva;
    private int idCliente;
    private int idEmpleado;
    private int idServicio;
    private LocalDate fechaReserva;
    private String horaReserva;
    private String estadoReserva;

    public ReservasModel() {
    }

    public ReservasModel(int idReserva, int idCliente, int idEmpleado, int idServicio, LocalDate fechaReserva, String horaReserva, String estadoReserva) {
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

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDate fechaReserva) {
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
        Connection connection = null;
        try {
            connection = ConexionDB.connection();

        ObservableList<ReservasModel>dataReservas = FXCollections.observableArrayList();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT *FROM tbl_reservas;");
            while(resultSet.next()){
                ReservasModel reservasModel = new ReservasModel();
                reservasModel.setIdReserva(resultSet.getInt("id_reserva"));
                reservasModel.setIdCliente(resultSet.getInt("id_cliente"));
                reservasModel.setIdEmpleado(resultSet.getInt("id_empleado"));
                reservasModel.setIdServicio(resultSet.getInt("id_servicio"));
                reservasModel.setFechaReserva(resultSet.getDate("fecha_reserva").toLocalDate());
                //convertir Time a String(HH:mm)
                Time horaReserva = resultSet.getTime("hora_reserva");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                reservasModel.setHoraReserva(horaReserva.toLocalTime().format(formatter));

                reservasModel.setEstadoReserva(resultSet.getString("estado"));
                dataReservas.add(reservasModel);
            }
            return dataReservas;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int saveReservas(){
        Connection con = null;
        try {
            con = ConexionDB.connection();

        PreparedStatement preparedStatement = null;

            preparedStatement = con.prepareStatement("INSERT INTO tbl_reservas(idcliente, idempleado, idservicio, fechareserva, horareserva, estadoreserva) VALUES (?, ?, ?, ?, ?, ?);");
            preparedStatement.setInt(1,this.idCliente );
            preparedStatement.setInt(2,this.idEmpleado);
            preparedStatement.setInt(3,this.idServicio);

            preparedStatement.setDate(4,java.sql.Date.valueOf(this.fechaReserva));
            preparedStatement.setTime(5, java.sql.Time.valueOf(this.horaReserva));
            preparedStatement.setString(6, this.estadoReserva);
            int retorno = preparedStatement.executeUpdate();
            return retorno;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int editReservas(){
        Connection con = null;
        try {
            con = ConexionDB.connection();

        PreparedStatement preparedStatement = null;

            preparedStatement = con.prepareStatement("UPDATE tbl_reservas SET id_cliente=?, id_empleado=?, id_servicio=?, fecha_reserva=?, hora_reserva=?, estado=? WHERE id_reserva=?;");
            preparedStatement.setInt(1, this.idCliente);
            preparedStatement.setInt(2, this.idEmpleado);
            preparedStatement.setInt(3, this.idServicio);
            preparedStatement.setDate(4, java.sql.Date.valueOf(this.fechaReserva));

            System.out.println("hora antes de la conversion: "+this.horaReserva);
            if(this.horaReserva !=null){
                if(!this.horaReserva.contains(":")){
                    throw new IllegalArgumentException("Formato de hora inválida. La hora debe contener al menos una ':'");

                }if(this.horaReserva.length() ==5){
                    this.horaReserva = this.horaReserva + ":00"; //añadir segundos
                }
            }else{
                throw new IllegalArgumentException("La hora no puede ser nula");
            }

            System.out.println("hora antes de la correccion: "+this.horaReserva);

            try{
                preparedStatement.setTime(5, java.sql.Time.valueOf(this.horaReserva));

            }catch (IllegalArgumentException e){
                throw new RuntimeException("Error al convertir la hora. Formato de hora invalido: "+this.horaReserva, e);

            }
            //preparedStatement.setTime(5, java.sql.Time.valueOf(this.horaReserva));
            preparedStatement.setString(6, this.estadoReserva);
            preparedStatement.setInt(7, this.idReserva);
            int retorno = preparedStatement.executeUpdate();
            if(retorno ==0){
                throw new RuntimeException("No se pudo actualizar la reserva. Verifica que el id de la reserva sea correcto.");
            }
            return retorno;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar la reserva");
        }catch (IllegalArgumentException e){
            throw new RuntimeException("Error en los parametros de la reserva"+ e.getMessage(), e);

        }
    }

    public int deleteReservas(){
        Connection con = null;
        try {
            con = ConexionDB.connection();


            PreparedStatement statement = con.prepareStatement("DELETE FROM tbl_reservas WHERE id_reserva =?;");
            statement.setInt(1, idReserva);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
