package com.example.peluqueria_app_desk;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class InicioController {
    @FXML
    private ImageView imageInicio;
    @FXML
    private Button btnEntrar;
    public void initialize(){

        Image imagen = new Image(getClass().getResourceAsStream("/Imagenes/logo_bonito.jpeg"));
        imageInicio.setImage(imagen);
        btnEntrar.setOnAction(event -> inicio());
    }

    private void entrar(String fxmlFile) {
        try {
            // Cargar la nueva vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            // Obtener el Stage actual
            Stage stage = (Stage) btnEntrar.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void inicio() {
        entrar("view_login.fxml");
    }
}
