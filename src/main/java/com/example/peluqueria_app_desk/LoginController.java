package com.example.peluqueria_app_desk;


import com.example.peluqueria_app_desk.Conexion.ConexionDB;
import com.example.peluqueria_app_desk.Modelos.EmpleadoModel;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;



public class LoginController {
    @FXML
    private Button btnIngresar;

    @FXML
    private Label lblMensaje;

    @FXML
    private TextField txtCorreo;

    @FXML
    private PasswordField txtPass;
    private final EmpleadoModel empleadoModel = new EmpleadoModel(); // Instancia del modelo

    public void initialize() {


        txtCorreo.requestFocus();

        btnIngresar.setDisable(true);
        ChangeListener<String> listener = (observable, oldValue, newValue) -> {

            txtCorreo.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    txtPass.requestFocus(); // Cambia el foco al campo de contraseña
                }
            });
            // Habilitar el botón solo si ambos campos tienen texto
            boolean habilitar = !txtCorreo.getText().trim().isEmpty() &&
                    !txtPass.getText().trim().isEmpty();
            btnIngresar.setDisable(!habilitar);
            txtPass.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    btnIngresar.fire(); // Ejecuta el botón de login
                }
            });
        };
        txtCorreo.textProperty().addListener(listener);
        txtPass.textProperty().addListener(listener);
    }



        @FXML
        private void handleLogin () {
            // Obtener el correo y contraseña ingresados por el usuario
            String correo = txtCorreo.getText();
            String contraseña = txtPass.getText();

            // Verificar si los campos están vacíos
            if (correo.isEmpty() || contraseña.isEmpty()) {
                lblMensaje.setText("Por favor, complete todos los campos.");
                return;
            }

            // Validar las credenciales usando el modelo
            int idEmpleado = empleadoModel.validarCredenciales(correo, contraseña);

            if (idEmpleado != -1) {
                // Inicio de sesión exitoso
                lblMensaje.setText("Inicio de sesión exitoso.");

                try {
                    // Redirigir al formulario de gestión
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/peluqueria_app_desk/view_horarios.fxml"));
                    Stage stage = (Stage) txtCorreo.getScene().getWindow();
                    stage.setScene(new Scene(fxmlLoader.load()));
                    stage.setTitle("Gestión de Horarios");

                    // Pasar el ID del empleado al controlador de gestión
                   GestionController gestionController = fxmlLoader.getController();
                   gestionController.setEmpleadoId(idEmpleado);

                } catch (Exception e) {
                    e.printStackTrace();
                    lblMensaje.setText("Error al cargar la vista de gestión.");
                }
            } else {
                // Credenciales incorrectas
                lblMensaje.setText("Correo o contraseña incorrectos.");
            }
        }
    }
