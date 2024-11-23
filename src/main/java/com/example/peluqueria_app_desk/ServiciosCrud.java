package com.example.peluqueria_app_desk;

import com.example.peluqueria_app_desk.Conexion.ConexionDB;
import com.example.peluqueria_app_desk.Modelos.ServiciosModel;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ServiciosCrud {
    // Query para insertar un nuevo servicio
    private static final String INSERT_SERVICIO =
            "INSERT INTO servicios (id_servicio, nombre_servicio, descripcion) VALUES (?, ?, ?)";
    // Query para actualizar un servicio existente
    private static final String UPDATE_SERVICIO =
            "UPDATE servicios SET nombre_servicio = ?, descripcion = ? WHERE id_servicio = ?";
    // Query para eliminar un servicio por su ID
    private static final String DELETE_SERVICIO =
            "DELETE FROM servicios WHERE id_servicio = ?";

    // Método para agregar un nuevo servicio
    public void agregarServicio(ServiciosModel servicio) throws SQLException {
        try (Connection con = ConexionDB.connection();
             PreparedStatement pst = con.prepareStatement(INSERT_SERVICIO)) {
            pst.setInt(1, servicio.getIdServicio());
            pst.setString(2, servicio.getNombreServicio());
            pst.setString(3, servicio.getDescripcion());
            pst.executeUpdate();
        }
    }

    // Método para actualizar un servicio existente
    public void actualizarServicio(ServiciosModel servicio) throws SQLException {
        try (Connection con = ConexionDB.connection();
             PreparedStatement pst = con.prepareStatement(UPDATE_SERVICIO)) {
            pst.setString(1, servicio.getNombreServicio());
            pst.setString(2, servicio.getDescripcion());
            pst.setInt(3, servicio.getIdServicio());
            pst.executeUpdate();
        }
    }

    // Método para eliminar un servicio por su ID
    public void eliminarServicio(int idServicio) throws SQLException {
        try (Connection con = ConexionDB.connection();
             PreparedStatement pst = con.prepareStatement(DELETE_SERVICIO)) {
            pst.setInt(1, idServicio);
            pst.executeUpdate();
        }
    }

}
