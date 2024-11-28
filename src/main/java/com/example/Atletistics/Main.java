package com.example.Atletistics; //Modificar al package correcto

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/** 
 * Plantilla JAVAFX
 * Autor: Daniel Escobar Molina
 * Curso y año: 2ºDAM 2024
 * Objetivo de esta clase:
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
         URL imagenUrl = getClass().getClassLoader().getResource("img/default.png");
        if (imagenUrl == null) {
            System.out.println("No se encontró la imagen en la ruta esperada.");
            // Usa una imagen de respaldo o lanza una excepción manejable.
            return;
        }
        
        URL imageUrl = getClass().getClassLoader().getResource("img/AtletisticsLogo.jpg");
        if (imageUrl != null) {
            Image image = new Image(imageUrl.toString());
            primaryStage.getIcons().add(image);
        } else {
            System.out.println("No se encontró la imagen en el ClassLoader.");
        }
            
        Parent root = FXMLLoader.load(getClass().getResource("../Ventanas/Atletistics.fxml"));

        
        Scene scene=new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Atletistics");

        primaryStage.show();
    }

}
