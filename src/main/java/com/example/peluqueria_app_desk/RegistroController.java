package com.example.peluqueria_app_desk;

import com.example.peluqueria_app_desk.Modelos.EmpleadoModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RegistroController {
    @FXML
    private Button btnRegistrarse;

    @FXML
    private TextField txtApellidoEm;

    @FXML
    private TextField txtCorreoEm;

    @FXML
    private TextField txtDireccionEm;

    @FXML
    private TextField txtNombreEm;

    @FXML
    private TextField txtPassEm;

    @FXML
    private TextField txtPassEmCon;

    @FXML
    private TextField txtRolEm;

    @FXML
    private TextField txtTelEm;

    @FXML
    private TextField txtUsserEm;

    @FXML
    private void actionBtnRegistrarse() {
        if (validarTextFields()) {
            EmpleadoModel empleado = new EmpleadoModel();
            empleado.setNombreEmpleado(txtNombreEm.getText());
            empleado.setApellidoEmpleado(txtApellidoEm.getText());
            empleado.setCorreoEmpleado(txtCorreoEm.getText());
            empleado.setTelefonoEmpleado(txtTelEm.getText());
            empleado.setDireccionEmpleado(txtDireccionEm.getText());
            empleado.setRolEmpleado(txtRolEm.getText());

            int resultado = empleado.saveEmpleados();
            if (resultado > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registro Exitoso");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Error al guardar el empleado");
                alert.show();
            }

        }
    }


    private boolean validarTextFields() {
        if (txtNombreEm.getText().isEmpty() || txtApellidoEm.getText().isEmpty() ||
                txtCorreoEm.getText().isEmpty() || txtTelEm.getText().isEmpty() ||
                txtDireccionEm.getText().isEmpty() || txtRolEm.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No se permiten campos vacios");
            alert.show();
            return false;
        }
        return true;
    }
}


