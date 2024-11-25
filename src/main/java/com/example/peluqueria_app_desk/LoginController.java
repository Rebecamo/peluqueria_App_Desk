package com.example.peluqueria_app_desk;


import com.example.peluqueria_app_desk.Modelos.EmpleadoModel;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javafx.scene.input.KeyCode;



import java.io.IOException;


public class LoginController {
    @FXML
    private Button btnIngresar;

    @FXML
    private Label lblMensaje;

    @FXML
    private TextField txtCorreo;
    @FXML
    private StackPane passwordPane;

    @FXML
    private PasswordField txtPass;

    @FXML
    private CheckBox ckbMostrarPass;
    @FXML
    private TextField txtPassVisible;

    private final EmpleadoModel empleadoModel = new EmpleadoModel();

    public void initialize() {
        txtPassVisible.textProperty().bindBidirectional(txtPass.textProperty());

        configurarHabilitacionBoton();
        configurarCambioFoco();
        configurarVisualizacionContraseña();



    }
    public void togglePasswordVisibility() {
        if (ckbMostrarPass.isSelected()) {
            // Mostrar el campo de texto visible
            txtPassVisible.setText(txtPass.getText());
            txtPassVisible.setVisible(true);
            txtPassVisible.setManaged(true);
            txtPass.setVisible(false);
            txtPass.setManaged(false);
        } else {
            // Volver al PasswordField ocultando el campo visible
            txtPass.setText(txtPassVisible.getText());
            txtPass.setVisible(true);
            txtPass.setManaged(true);
            txtPassVisible.setVisible(false);
            txtPassVisible.setManaged(false);
        }

    }
    private void configurarVisualizacionContraseña() {

        ckbMostrarPass.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                txtPassVisible.setText(txtPass.getText());
                txtPassVisible.setVisible(true);
                txtPassVisible.setManaged(true);
                txtPass.setVisible(false);
                txtPass.setManaged(false);
            } else {
                txtPass.setText(txtPassVisible.getText());
                txtPass.setVisible(true);
                txtPass.setManaged(true);
                txtPassVisible.setVisible(false);
                txtPassVisible.setManaged(false);
            }
        });



    }

    private void configurarCambioFoco() {
        txtCorreo.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (ckbMostrarPass.isSelected()) {
                    txtPassVisible.requestFocus();
                } else {
                    txtPass.requestFocus();
                }
            }
        });

        // Acciones para los campos de contraseña (PasswordField y TextField)
        txtPass.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnIngresar.fire();
            }
        });

        txtPassVisible.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnIngresar.fire();
            }
        });


    }

    private void configurarHabilitacionBoton() {
        ChangeListener<String> listener = (observable, oldValue, newValue) -> {
            boolean habilitar = !txtCorreo.getText().trim().isEmpty() &&
                    (!txtPass.getText().trim().isEmpty() || !txtPassVisible.getText().trim().isEmpty());
            btnIngresar.setDisable(!habilitar);
        };

        txtCorreo.textProperty().addListener(listener);
        txtPass.textProperty().addListener(listener);
        txtPassVisible.textProperty().addListener(listener);



    }
    private void mostrarMensaje(String mensaje) {
        lblMensaje.setText(mensaje);
    }
    @FXML
    private void handleLogin() {
        String correo = txtCorreo.getText();
        String contraseña = txtPass.getText();

        if (correo.isEmpty() || contraseña.isEmpty()) {
            mostrarMensaje("Por favor, complete todos los campos.");
            return;
        }

        int idEmpleado = empleadoModel.validarCredenciales(correo, contraseña);

        if (idEmpleado != -1) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("view_Gestion.fxml"));
                Parent root = loader.load();

                // Obtener la ventana actual desde un botón, por ejemplo "btnIngresar"
                Stage currentStage = (Stage) btnIngresar.getScene().getWindow();

                // Crear una nueva escena con la raíz cargada
                Scene scene = new Scene(root);

                // Cambiar la escena de la ventana actual
                currentStage.setScene(scene);
                currentStage.show();




            } catch (IOException e) {
                e.printStackTrace();
                mostrarMensaje("Error al cargar la vista.");
            }
        } else {
            mostrarMensaje("Correo o contraseña incorrectos.");
        }
    }
}
