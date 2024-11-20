package com.example.peluqueria_app_desk.Modelos;

import javafx.collections.ObservableList;

public class UsuarioModel {
    private int idUsuario;
    private String nombreUsuario;
    private String passUsuario;
    private String rolUsuario;
    private int idEmpleado;
    private int idCliente;

    public UsuarioModel() {
    }

    public UsuarioModel(int idUsuario, String nombreUsuario, String passUsuario, String rolUsuario, int idEmpleado, int idCliente) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.passUsuario = passUsuario;
        this.rolUsuario = rolUsuario;
        this.idEmpleado = idEmpleado;
        this.idCliente = idCliente;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassUsuario() {
        return passUsuario;
    }

    public void setPassUsuario(String passUsuario) {
        this.passUsuario = passUsuario;
    }

    public String getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(String rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }


}
