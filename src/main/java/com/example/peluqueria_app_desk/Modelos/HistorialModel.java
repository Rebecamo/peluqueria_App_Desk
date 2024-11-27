package com.example.peluqueria_app_desk.Modelos;


import com.example.peluqueria_app_desk.Conexion.ConexionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;

public class HistorialModel {


    //CLIENTES FRECUENTES
    private int idCliente;
    private String nombreCliente;
    private int totalReservas;



        public int getIdCliente() {
        return idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public int getTotalReservas() {
        return totalReservas;
    }

    public HistorialModel(int idCliente, String nombreCliente, int totalReservas) {
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.totalReservas = totalReservas;
    }

    public HistorialModel() {
    }

    //CLIENTES FRECUENTES
    public ObservableList<HistorialModel> getClientesFrecuentes() {
        ObservableList<HistorialModel> clientes = FXCollections.observableArrayList();
        Connection connection = ConexionDB.connection();
        try {
            Statement statement = connection.createStatement();
            String consulta = "SELECT c.id_cliente, c.nombre, COUNT(r.id_reserva) AS total_reservas " +
                    "FROM tbl_reservas r " +
                    "JOIN tbl_clientes c ON r.id_cliente = c.id_cliente " +
                    "GROUP BY c.id_cliente, c.nombre " +
                    "ORDER BY total_reservas DESC " +
                    "LIMIT 10;";
            ResultSet resultSet = statement.executeQuery(consulta);
            while (resultSet.next()) {
                HistorialModel cliente = new HistorialModel(
                        resultSet.getInt("id_cliente"),
                        resultSet.getString("nombre"),
                        resultSet.getInt("total_reservas")
                );
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener clientes frecuentes");
        }
        return clientes;
    }





}
