package com.example.peluqueria_app_desk.Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final String URL = "jdbc:postgresql://localhost:5432/RAGlamStudio1";
        private static final String USER = "postgres"; // Cambiar por el usuario de tu DB
        private static final String PASSWORD = "Evf22006"; // Cambiar por la contraseña de tu DB

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
    }

