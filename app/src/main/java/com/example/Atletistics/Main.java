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
import org.kordamp.bootstrapfx.BootstrapFX;

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
        
        URL imageUrl = getClass().getClassLoader().getResource("img/AtletisticsLogo.jpg");
        if (imageUrl != null) {
            Image image = new Image(imageUrl.toString());
            primaryStage.getIcons().add(image);
        } else {
            System.out.println("No se encontró la imagen en el ClassLoader.");
        }
            
        Parent root = FXMLLoader.load(getClass().getResource("../Ventanas/Atletistics.fxml"));
        

        
        Scene scene=new Scene(root);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        scene.getStylesheets().add(getClass().getResource("../Css/atletistics.css").toString());
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Atletistics");

        primaryStage.show();
    }

}
