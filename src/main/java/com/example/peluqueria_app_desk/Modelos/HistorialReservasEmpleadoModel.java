package com.example.peluqueria_app_desk.Modelos;

import com.example.peluqueria_app_desk.Conexion.ConexionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HistorialReservasEmpleadoModel {
    private int idEmpleado;
    private String nombreEmpleado;
    private int totalReservas3;

    public HistorialReservasEmpleadoModel(int idEmpleado, String nombreEmpleado, int totalReservas3) {
        this.idEmpleado = idEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.totalReservas3 = totalReservas3;
    }

    public HistorialReservasEmpleadoModel() {
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

    public int getTotalReservas3() {
        return totalReservas3;
    }

    public void setTotalReservas3(int totalReservas3) {
        this.totalReservas3 = totalReservas3;
    }
    public ObservableList<HistorialReservasEmpleadoModel> getReservasPorEmpleado() {
        ObservableList<HistorialReservasEmpleadoModel> empleados = FXCollections.observableArrayList();
        Connection connection = ConexionDB.connection();
        try {
            Statement statement = connection.createStatement();
            String consulta = """
            SELECT 
                e.id_empleado, 
                e.nombres, 
                COUNT(r.id_reserva) AS total_reservas
            FROM 
                tbl_empleados e
            LEFT JOIN 
                tbl_reservas r ON e.id_empleado = r.id_empleado
            GROUP BY 
                e.id_empleado, e.nombres
            ORDER BY 
                total_reservas DESC;
        """;
            ResultSet resultSet = statement.executeQuery(consulta);
            while (resultSet.next()) {
                HistorialReservasEmpleadoModel empleado = new HistorialReservasEmpleadoModel(
                        resultSet.getInt("id_empleado"),
                        resultSet.getString("nombres"),
                        resultSet.getInt("total_reservas")
                );
                empleados.add(empleado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener reservas por empleado");
        }
        return empleados;
    }
}
