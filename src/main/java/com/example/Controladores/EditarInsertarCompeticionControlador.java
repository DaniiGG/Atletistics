/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Controladores;

import com.example.Modelos.Atleta;
import com.example.Modelos.Competicion;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Node;

/**
 *
 * @author danie
 */
public class EditarInsertarCompeticionControlador implements Initializable{
    
    @FXML
    private Label labelAñadir;
    
     @FXML
    private Button seleccImg;

    @FXML
    private TextField txtFecha;

    @FXML
    private TextField txtLugar;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPremio;

    @FXML
    private TextField txtTipo;

    private Competicion competicion;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
   
    }
    
    public void iniciarVentana(Competicion competicion) {
        this.competicion = competicion;
        
        if (competicion != null) { // Modo edición
             labelAñadir.setText("Editando resultado");
            txtNombre.setText(competicion.getNombre());
            txtTipo.setText(competicion.getTipo());
            txtLugar.setText(String.valueOf(competicion.getLugar()));
            txtFecha.setText(String.valueOf(competicion.getFecha()));
            txtPremio.setText(competicion.getPremio());
        } else { // Modo inserción
            labelAñadir.setText("Añadiendo resultado");
            txtNombre.clear();
            txtTipo.clear();
            txtLugar.clear();
            txtFecha.clear();
            txtPremio.clear();
        }
    }

    @FXML
    private void confirmar() {
        String nombre = txtNombre.getText();
        String lugar = txtLugar.getText();
        String tipo = txtTipo.getText();
        String premio = txtPremio.getText();
        String imagen = "";

        Date fecha = null;
        try {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date fechaUtil = formato.parse(txtFecha.getText());
            fecha = new java.sql.Date(fechaUtil.getTime());
        } catch (ParseException e) {
            System.out.println("Formato de fecha inválido");
            e.printStackTrace();
            return;
        }

        if (competicion == null) { 
            // Modo inserción
            competicion = new Competicion(0, nombre, lugar, fecha, tipo, premio, imagen);
        } else { 
            // Modo edición
            competicion.setNombre(nombre);
            competicion.setLugar(lugar);
            competicion.setFecha(fecha);
            competicion.setTipo(tipo);
            competicion.setPremio(premio);
        }

        // Cerrar la ventana
        Stage stage = (Stage) txtPremio.getScene().getWindow();
        stage.close();
    }

    private Atleta obtenerAtletaPorNombre(String nombreCompleto) {
        return null;
    }
    
    @FXML
    void cancelar() {
        Stage stage = (Stage) txtPremio.getScene().getWindow();
        stage.close();
    }

    
   

    
    
}
