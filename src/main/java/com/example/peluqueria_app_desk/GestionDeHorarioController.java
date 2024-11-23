package com.example.peluqueria_app_desk;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class GestionDeHorarioController extends Application {


    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml")); // Archivo FXML para la pantalla de inicio de sesión
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Gestión de Horarios");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
