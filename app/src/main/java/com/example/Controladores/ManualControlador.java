package com.example.Controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class ManualControlador implements Initializable {
    
    @FXML
    private WebView webViewManual; // Asegúrate de que coincida con el fx:id en el FXML

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        WebEngine webEngine = webViewManual.getEngine();
        
        // Obtener la URL del archivo
        String htmlUrl = getClass().getResource("/manual/Manual.html") != null ?
                         getClass().getResource("/manual/Manual.html").toExternalForm() : "Archivo no encontrado";
        
        // Imprimir la URL cargada
        System.out.println("URL cargada: " + htmlUrl);
        
        // Cargar el HTML solo si la URL es válida
        if (!htmlUrl.equals("Archivo no encontrado")) {
            webEngine.load(htmlUrl);
        } else {
            System.out.println("No se encontró el archivo HTML.");
        }
    }
}
