package com.example.peluqueria_app_desk.Modelos;

import com.example.peluqueria_app_desk.Conexion.ConexionDB;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HistorialModel {
    private String cliente;
    private String servicio;
    private LocalDateTime fecha;
    private String empleado;
    private String estado;

    public HistorialModel() {
    }

    public HistorialModel(String cliente, String servicio, LocalDateTime fecha, String empleado, String estado) {
        this.cliente = cliente;
        this.servicio = servicio;
        this.fecha = fecha;
        this.empleado = empleado;
        this.estado = estado;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    public List<ReservasModel> obtenerReservas() {
        List<ReservasModel> reservas = new ArrayList<>();
        String query = "SELECT cliente_nombre, servicio, fecha_reserva, empleado_asignado, estado FROM tbl_historial_reservas";

        try (Statement stmt = ConexionDB.connection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                ReservasModel reserva = new ReservasModel(
                        rs.getString("cliente_nombre"),
                        rs.getString("servicio"),
                        rs.getTimestamp("fecha_reserva").toLocalDateTime(),
                        rs.getString("empleado_asignado"),
                        rs.getString("estado")
                );
                reservas.add(reserva);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener reservas: " + e.getMessage());
        }
        return reservas;
    }
}