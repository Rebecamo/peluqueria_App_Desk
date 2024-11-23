package com.example.peluqueria_app_desk.Modelos;

import com.example.peluqueria_app_desk.Conexion.ConexionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class HorariosModel {
    private int idHorario;
    private int idEmpleado; // Nuevo campo
    private String diaSemana;
    private String horaInicio;
    private String horaFin;

    // Constructor con idEmpleado
    public HorariosModel(int idHorario, int idEmpleado, String diaSemana, String horaInicio, String horaFin) {
        this.idHorario = idHorario;
        this.idEmpleado = idEmpleado;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    // Getters y setters
    public int getIdHorario() {
        return idHorario;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }


}