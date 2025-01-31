/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Controladores;

import com.example.Controladores.Controlador;
import com.example.Modelos.Atleta;
import com.example.Modelos.Competicion;
import com.example.Modelos.Resultados;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;
import org.w3c.dom.Node;

/**
 *
 * @author danie
 */
public class EditarInsertarResultadoControlador implements Initializable {

    List<ValidationSupport> validadores;

    private Controlador controladorPrincipal;
    Statement st;
    ResultSet rs;

    public void setControladorPrincipal(Controlador controladorPrincipal) {
        this.controladorPrincipal = controladorPrincipal;
    }

    Controlador conexionHelper = new Controlador();

    Connection conexion;
    @FXML
    private Button confirmar;
    @FXML
    private Button cancelar;

    @FXML
    private Label labelAñadir;

    @FXML
    private TextField txtDorsal;

    @FXML
    private TextField txtMarca;

    @FXML
    private TextField txtPuesto;

    private Resultados resultado;
    @FXML
    private ComboBox<String> comboCompe;

    private ObservableList<String> listaCompeticiones = FXCollections.observableArrayList(); // Lista para el ComboBox
    private FilteredList<String> filteredCompeticiones;

    @FXML
    private ComboBox<String> comboAtleta;

    private ObservableList<String> listaAtletas = FXCollections.observableArrayList(); // Lista para el ComboBox
    private FilteredList<String> filteredAtletas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            conexion = conexionHelper.getConnection();
            if (conexion != null) {
                this.st = conexion.createStatement();
            }
            // Cargar y configurar ComboBox de atletas
            Controlador.cargarNombresAtletas(conexion,listaAtletas);
            filteredAtletas = new FilteredList<>(listaAtletas, p -> true);
            Controlador.configurarComboBox(comboAtleta, filteredAtletas);

            // Cargar y configurar ComboBox de competiciones
            Controlador.cargarNombresEventos(conexion, listaCompeticiones);
            filteredCompeticiones = new FilteredList<>(listaCompeticiones, p -> true);
            Controlador.configurarComboBox(comboCompe, filteredCompeticiones);

        } catch (IOException | SQLException e) {
            System.out.println("Error al inicializar: " + e.getMessage());
        }

        ValidationSupport validationPuesto = new ValidationSupport();
        ValidationSupport validationDorsal = new ValidationSupport();
        ValidationSupport validationMarca = new ValidationSupport();
        ValidationSupport validationAtleta = new ValidationSupport();
        ValidationSupport validationCompe = new ValidationSupport();

        Validador.validacionNumero(validationPuesto, txtPuesto, 1, 30, "Los puestos pueden ser de 1 a 30");
        Validador.validacionNumero(validationDorsal, txtDorsal, 3, 1000, "Los dorsales pueden ser de 1 a 1000");
        Validador.validacionTexto(validationMarca, txtMarca, 2, 50, "Las marcas deben tener entre 2 y 15 caracteres");

        Validador.validacionCampoVacio(validationAtleta, comboAtleta, "Debe seleccionar un atleta");
        Validador.validacionCampoVacio(validationCompe, comboCompe, "Debe seleccionar una competición");

        validadores = new ArrayList<>();
        validadores.addAll(Arrays.asList(validationPuesto, validationDorsal, validationMarca, validationAtleta, validationCompe));

    }
    
    public void iniciarVentana(Resultados resultado) {
        this.resultado = resultado;

        if (resultado != null) { // Modo edición
            labelAñadir.setText("Editando resultado");
            comboAtleta.setValue(resultado.getNombreAtleta());
            txtMarca.setText(resultado.getMarca());
            txtPuesto.setText(String.valueOf(resultado.getPuesto()));
            txtDorsal.setText(String.valueOf(resultado.getDorsal()));
            comboCompe.setValue(resultado.getNombreCompe());
        } else { // Modo inserción
            labelAñadir.setText("Añadiendo resultado");
            comboAtleta.setValue(null);
            txtMarca.clear();
            txtPuesto.clear();
            txtDorsal.clear();
            comboCompe.setValue(null);
        }
        Controlador.establecerIconoSubVentanas(confirmar, cancelar);
    }

    @FXML
    private void confirmar() {

        boolean todoOK = true;
        for (ValidationSupport validationSupport : validadores) {
            todoOK = (todoOK && validationSupport.getValidationResult().getErrors().isEmpty());
        }
        if (!todoOK) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errores en el formulario");
            alert.setContentText("Corrige los errores antes de confirmar.");
            alert.showAndWait();
            return;
        } else if (txtDorsal.getText().isEmpty() || txtMarca.getText().isEmpty()
                || txtPuesto.getText().isEmpty() || comboAtleta.getValue() == null
                || comboCompe.getValue() == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errores en el formulario");
            alert.setContentText("Todos los campos deben ser completados.");
            alert.showAndWait();
            return;
        }

        String marca = txtMarca.getText();
        int puesto = Integer.parseInt(txtPuesto.getText());
        int dorsal = Integer.parseInt(txtDorsal.getText());
        String nombreAtleta = comboAtleta.getValue();
        String nombreCompe = comboCompe.getValue();

        Atleta atleta = obtenerAtletaPorNombre(nombreAtleta);
        Competicion competicion = obtenerCompeticionPorNombre(nombreCompe);

        if (atleta == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Ese atleta NO existe!!");
            alert.setContentText("Debes elegir un atleta que exista.");
            alert.showAndWait();
            return;
        }

        if (competicion == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Esa competición NO existe!!");
            alert.setContentText("Debes elegir una competición que exista.");
            alert.showAndWait();
            return;
        }

        try {
            if (resultado == null) { // Modo inserción
                String insertQuery = "INSERT INTO resultados (dni_atleta, id_competicion, marca, puesto, dorsal) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = this.conexion.prepareStatement(insertQuery);
                preparedStatement.setString(1, atleta.getDni()); // DNI del atleta
                preparedStatement.setInt(2, competicion.getId_competicion()); // ID de la competición
                preparedStatement.setString(3, marca); // Marca
                preparedStatement.setInt(4, puesto); // Puesto
                preparedStatement.setInt(5, dorsal); // Dorsal
                preparedStatement.executeUpdate();
                System.out.println("Resultado insertado correctamente.");
            } else { // Modo edición
                String updateQuery = "UPDATE resultados SET dni_atleta = ?, id_competicion = ?, marca = ?, puesto = ?, dorsal = ? WHERE id_resultado = ?";
                PreparedStatement preparedStatement = this.conexion.prepareStatement(updateQuery);
                preparedStatement.setString(1, atleta.getDni()); // DNI del atleta
                preparedStatement.setInt(2, competicion.getId_competicion()); // ID de la competición
                preparedStatement.setString(3, marca); // Marca
                preparedStatement.setInt(4, puesto); // Puesto
                preparedStatement.setInt(5, dorsal); // Dorsal
                preparedStatement.setInt(6, resultado.getId()); // ID del resultado a actualizar
                preparedStatement.executeUpdate();
                System.out.println("Resultado actualizado correctamente.");
            }
            
             if (controladorPrincipal != null) {
                    String mensaje = (resultado == null) 
                        ? "Resultado insertado correctamente." 
                        : "Resultado actualizado correctamente.";

                    controladorPrincipal.mostrarMensajeSuperpuesto(mensaje, 5000, controladorPrincipal.mensajeSuperpuestoResul); // Mensaje por 3 segundos
                    controladorPrincipal.realizarOperacionConLoader(controladorPrincipal.PIResul, controladorPrincipal.tableViewResultados);
                    controladorPrincipal.actualizarResultadosTable(); // Actualizar el grid después del mensaje
                }

            Stage stage = (Stage) comboAtleta.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            System.out.println("Error al guardar el resultado: " + e.getMessage());
        }
    }

    private Atleta obtenerAtletaPorNombre(String nombreCompleto) {
        String query = "SELECT * FROM atletas WHERE CONCAT(nombre, ' ', apellidos) = ?";
        Atleta atleta = null;

        try (PreparedStatement preparedStatement = conexion.prepareStatement(query)) {
            preparedStatement.setString(1, nombreCompleto);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String dni = resultSet.getString("dni");
                    String nombre = resultSet.getString("nombre");
                    String apellidos = resultSet.getString("apellidos");
                    int edad = resultSet.getInt("edad");
                    String club = resultSet.getString("club");
                    boolean activo = resultSet.getBoolean("activo");
                    String fotoPath = resultSet.getString("foto");

                    atleta = new Atleta(dni, nombre, apellidos, edad, club, activo, fotoPath);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener atleta: " + e.getMessage());
        }

        return atleta;
    }

    private Competicion obtenerCompeticionPorNombre(String nombreCompe) {
        String query = "SELECT * FROM competiciones WHERE nombre = ?";
        Competicion competicion = null;

        try (PreparedStatement preparedStatement = conexion.prepareStatement(query)) {
            preparedStatement.setString(1, nombreCompe);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id_competicion");
                    String nombre = resultSet.getString("nombre");
                    String lugar = resultSet.getString("lugar");
                    Date fecha = resultSet.getDate("fecha");
                    String tipo = resultSet.getString("tipo");
                    String premio = resultSet.getString("premio");
                    String imagen = resultSet.getString("imagen");

                    competicion = new Competicion(id, nombre, lugar, fecha, tipo, premio, imagen);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener competición: " + e.getMessage());
        }

        return competicion;
    }

    @FXML
    void cancelar() {
        Stage stage = (Stage) comboAtleta.getScene().getWindow();
        stage.close();
    }

}
