/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Controladores;

import com.example.Modelos.Atleta;
import com.example.Modelos.Resultados;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Node;

/**
 *
 * @author danie
 */
public class EditarInsertarResultadoControlador implements Initializable{
    
    @FXML
    private Label labelAñadir;
    
    @FXML
    private TextField txtAtleta;

    @FXML
    private TextField txtCompe;

    @FXML
    private TextField txtDorsal;

    @FXML
    private TextField txtMarca;

    @FXML
    private TextField txtPuesto;

    private Resultados resultado;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
   
    }
    
    public void iniciarVentana(Resultados resultado) {
        this.resultado = resultado;
        
        if (resultado != null) { // Modo edición
             labelAñadir.setText("Editando resultado");
            txtAtleta.setText(resultado.getNombreAtleta());
            txtMarca.setText(resultado.getMarca());
            txtPuesto.setText(String.valueOf(resultado.getPuesto()));
            txtDorsal.setText(String.valueOf(resultado.getDorsal()));
            txtCompe.setText(resultado.getEvento());
        } else { // Modo inserción
             labelAñadir.setText("Añadiendo resultado");
            txtAtleta.clear();
            txtMarca.clear();
            txtPuesto.clear();
            txtDorsal.clear();
            txtCompe.clear();
        }
    }

    @FXML
    private void confirmar() {
        String marca = txtMarca.getText();
        int puesto = Integer.parseInt(txtPuesto.getText());
        int dorsal = Integer.parseInt(txtDorsal.getText());
        String nombreAtleta = txtAtleta.getText();
        String evento = txtCompe.getText();

        Atleta atleta = obtenerAtletaPorNombre(nombreAtleta);

        if (atleta == null) {
            System.out.println("Atleta no encontrado con nombre: " + nombreAtleta);
            return;
        }

        if (resultado == null) { 
            resultado = new Resultados(0, evento, marca, puesto, dorsal, atleta);
        } else { // Modo editar
            resultado.setMarca(marca);
            resultado.setPuesto(puesto);
            resultado.setDorsal(dorsal);
            resultado.setAtleta(atleta);
            resultado.setEvento(evento);
        }
        Stage stage = (Stage) txtAtleta.getScene().getWindow();
        stage.close();
    }

    private Atleta obtenerAtletaPorNombre(String nombreCompleto) {
        return null;
    }
    
    @FXML
    void cancelar() {
        Stage stage = (Stage) txtAtleta.getScene().getWindow();
        stage.close();
    }

    
   

    
    
}
