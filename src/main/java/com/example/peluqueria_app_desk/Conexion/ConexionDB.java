package com.example.peluqueria_app_desk.Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final String url="jdbc:postgresql://localhost:5432/db_R&AGlamStudio1" +
            "";
    private static final String USER ="postgres";
    private static final String PASS = "Evf22006";

    public static Connection connection(){
        try {
            Connection conectar = DriverManager.getConnection(url, USER, PASS);
            System.out.println("Conectado a la base");
            return conectar;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
