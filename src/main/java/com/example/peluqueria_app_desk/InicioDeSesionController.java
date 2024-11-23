package com.example.peluqueria_app_desk;

import com.example.peluqueria_app_desk.Autenticacion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class InicioDeSesionController {

    @FXML
    private TextField correoField;

    @FXML
    private PasswordField contrasenaField;

    private Autenticacion authService = new Autenticacion();

    @FXML
    private void iniciarSesion() {
        String correo = correoField.getText();
        String contrasena = contrasenaField.getText();

        if (authService.iniciarSesion(correo, contrasena)) {
            try {
                // Cambiar a la pantalla de gestión de horarios
                Stage stage = (Stage) correoField.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/horarios.fxml"))));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alerta = new Alert(Alert.AlertType.ERROR, "Correo o contraseña incorrectos");
            alerta.show();
        }
    }
}