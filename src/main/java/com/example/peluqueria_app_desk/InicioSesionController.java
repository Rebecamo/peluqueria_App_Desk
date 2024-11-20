package com.example.peluqueria_app_desk;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.io.IOException;

public class InicioSesionController {


    @FXML
    private ImageView InicioSesion;

    @FXML
    private Button btnIngresar;

    @FXML
    private Button btnRegistro;

    @FXML
    private CheckBox checkVerPassSignIn;

    @FXML
    private PasswordField txtPassSignIn;

    @FXML
    private TextField txtPassSignInMask;

    @FXML
    private TextField txtUsuario;

    @FXML
    private Label txtUsuarioEmpleado;

    public void initialize(){







        btnRegistro.setOnMouseEntered(mouseEvent -> {
            btnRegistro.setCursor(Cursor.HAND);
        });

        btnRegistro.setOnMouseExited(mouseEvent -> {
            btnRegistro.setCursor(Cursor.DEFAULT);
        });
    }

    @FXML
    private void actionbtnRegistrar() throws IOException{

        //cargamos el formulario de registro
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/peluqueria_app_desk/view_registro.fxml"));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);
        //toma la ventana actual y cambia la escnea
        Stage stage = (Stage) btnRegistro.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }






}
