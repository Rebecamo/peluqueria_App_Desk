package com.example.peluqueria_app_desk;


import com.example.peluqueria_app_desk.Modelos.EmpleadoModel;

import com.example.peluqueria_app_desk.Modelos.HorariosModel;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javafx.scene.input.KeyCode;



import java.io.IOException;
import java.util.List;


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
    private MenuBar menuBar;

    @FXML
    private MenuItem menuItemGestionarHorarios;

    @FXML
    private MenuItem menuItemGestionarReservas;

    @FXML
    private MenuItem menuItemHistorial;

    @FXML
    private MenuItem menuItemInicio;
    @FXML
    private PasswordField txtPass;
    @FXML
    private ImageView imgLogin;
    @FXML
    private CheckBox ckbMostrarPass;
    @FXML
    private TextField txtPassVisible;
    private int idEmpleadoActivo = -1;

    private void habilitarMenuBar(boolean habilitar) {
        menuBar.setDisable(!habilitar);
    }

    private final EmpleadoModel empleadoModel = new EmpleadoModel();


    public void initialize() {
        Image imagen = new Image(getClass().getResourceAsStream("/Imagenes/inicio-sesion.jpeg"));
        imgLogin.setImage(imagen);



        txtPassVisible.textProperty().bindBidirectional(txtPass.textProperty());

        configurarHabilitacionBoton();
        configurarCambioFoco();
        configurarVisualizacionContraseña();
        habilitarMenuBar(false);



        menuItemGestionarHorarios.setOnAction(event -> gestionHorario());
        menuItemGestionarReservas.setOnAction(event -> gestionReservas());
        menuItemHistorial.setOnAction(event -> historial());
        menuItemInicio.setOnAction(event -> inicio());

    }
    private void vistas(String fxmlFile) {
        try {
            // Cargar la nueva vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            // Obtener el Stage actual
            Stage stage = (Stage) menuBar.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void inicio() {
        vistas("view_login.fxml");
    }

    private void gestionHorario() {
        vistas("view_horarios.fxml");
    }

    private void gestionReservas() {
        vistas("view_gestion_reservas.fxml");
    }

    private void historial() {
        vistas("view_historial_reservas.fxml");
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
    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
    @FXML
    private void handleLogin() {
        String correo = txtCorreo.getText();
        String contraseña = txtPass.getText();

        if (correo.isEmpty() || contraseña.isEmpty()) {
            mostrarAlerta("Error","Complete todos los campos por favor.", Alert.AlertType.WARNING);
            return;
        }

        int idEmpleado = empleadoModel.validarCredenciales(correo, contraseña);

        if (idEmpleado != -1) {
            Sesion.setIdEmpleado(idEmpleado);
            habilitarMenuBar(true);
          mostrarAlerta("Éxito","Se inicio sesión correctamente.", Alert.AlertType.INFORMATION);


        } else {
          mostrarAlerta("Error","Correo o contraseña incorrectos.",Alert.AlertType.ERROR);
            habilitarMenuBar(false);
        }















    }
}
