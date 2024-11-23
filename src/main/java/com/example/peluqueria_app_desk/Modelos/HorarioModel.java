package com.example.peluqueria_app_desk.Modelos;


import java.time.LocalTime;

public class HorarioModel {
    private int idHorario;
    private int idEmpleado;
    private String diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    // Constructor
    public HorarioModel(int idHorario, int idEmpleado, String diaSemana, LocalTime horaInicio, LocalTime horaFin) {
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

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }
}

