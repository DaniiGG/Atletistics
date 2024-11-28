/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Controladores;

import com.example.Modelos.Atleta;
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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Node;

/**
 *
 * @author danie
 */
public class EditarInsertarAtletaControlador implements Initializable{
   
    
    @FXML
    private CheckBox activoCheck;
    
    @FXML
    private Button fotoBtn;
    
    @FXML
    private Label labelAñadir;

    @FXML
    private TextField txtApellidos;

    @FXML
    private TextField txtClub;

    @FXML
    private TextField txtDni;

    @FXML
    private TextField txtEdad;

    @FXML
    private TextField txtNombre;
    
    private Atleta atleta;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
   
    }
    
    public void iniciarVentana(Atleta atleta) {
        this.atleta = atleta;
        
        if (atleta != null) { // Modo edición
             labelAñadir.setText("Editando resultado");
            txtDni.setText(atleta.getDni());
            txtNombre.setText(atleta.getNombre());
            txtApellidos.setText(atleta.getApellidos());
            txtEdad.setText(String.valueOf(atleta.getEdad()));
            txtClub.setText(atleta.getClub());
            activoCheck.setSelected(atleta.isActivo());
            //fotoBtn.set
        } else { // Modo inserción
             labelAñadir.setText("Añadiendo resultado");
            txtDni.clear();
            txtNombre.clear();
            txtApellidos.clear();
            txtEdad.clear();
            txtClub.clear();
            activoCheck.setSelected(false);
        }
    }

    @FXML
    private void confirmar() {
        String dni = txtDni.getText();
        String nombre = txtNombre.getText();
        String apellidos = txtApellidos.getText();
        int edad = Integer.parseInt(txtEdad.getText());
        String club = txtClub.getText();
        Boolean activo = activoCheck.isSelected();
        String foto = "";

        

        if (atleta == null) { 
            atleta = new Atleta(dni, nombre, apellidos, edad, club, activo, foto);
        } else { // Modo editar
            atleta.setDni(dni);
            atleta.setNombre(nombre);
            atleta.setApellidos(apellidos);
            atleta.setEdad(edad);
            atleta.setClub(club);
            atleta.setActivo(activo);
            atleta.setFotoPath(foto);
        }
        Stage stage = (Stage) txtDni.getScene().getWindow();
        stage.close();
    }

    @FXML
    void cancelar() {
        Stage stage = (Stage) txtDni.getScene().getWindow();
        stage.close();
    }

    
   

    
    
}
