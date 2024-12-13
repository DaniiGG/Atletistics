/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Controladores;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


/**
 *
 * @author danie
 */
public class MapaControlador {
    
     @FXML
    private WebView webViewMapa;

    public void mostrarMapa(String lugar) {
        WebEngine webEngine = webViewMapa.getEngine();
        String url = "https://www.google.com/maps?q=" + lugar.replace(" ", "+");
        webEngine.load(url);
    }
    
}
