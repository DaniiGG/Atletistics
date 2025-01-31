package com.example.Atletistics; //Modificar al package correcto

import java.io.File;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import com.example.Controladores.Controlador; // Importar correctamente el controlador

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
        
        // Cargar el icono
        URL imageUrl = getClass().getClassLoader().getResource("img/AtletisticsLogo.jpg");
        if (imageUrl != null) {
            Image image = new Image(imageUrl.toString());
            primaryStage.getIcons().add(image);
        } else {
            System.out.println("No se encontró la imagen en el ClassLoader.");
        }

        // Cargar el archivo FXML correctamente
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Ventanas/Atletistics.fxml"));
        Parent root = loader.load(); // Ahora podemos obtener el controlador

        // Obtener el controlador y configurar el evento de teclado
        Controlador controller = loader.getController();

        // Crear la escena correctamente
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(controller::handleKeyPress); // Ahora `scene` está definido
        
        // Agregar estilos
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        scene.getStylesheets().add(getClass().getResource("/com/example/Css/atletistics.css").toString());

        // Configurar la ventana principal
        primaryStage.setScene(scene);
        primaryStage.setTitle("Atletistics");
        primaryStage.show();
    }
}
