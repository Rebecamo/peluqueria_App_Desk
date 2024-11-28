package com.example.peluqueria_app_desk;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Glam_Studio_Peluqueria  extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Glam_Studio_Peluqueria.class.getResource("view_inicio.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 350);
        stage.setTitle("Peluqueria Glam Studio");
        stage.setScene(scene);
       stage.setResizable(false);
        stage.show();
    }
}
