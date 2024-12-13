/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Controladores;

import com.example.Modelos.Atleta;
import com.example.Modelos.Competicion;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;
import org.w3c.dom.Node;

/**
 *
 * @author danie
 */
public class EditarInsertarCompeticionControlador implements Initializable{
    
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
    private Button fotoBtn;
    
    @FXML
    private Label labelAñadir;
    
     @FXML
    private Button seleccImg;


    @FXML
    private TextField txtLugar;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPremio;

    @FXML
    private TextField txtTipo;
    
    @FXML
    private DatePicker datePickerFecha;

    private Competicion competicion;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            conexion = conexionHelper.getConnection();
            if (conexion != null) {
                this.st = conexion.createStatement();
            }
        } catch (IOException | SQLException e) {
        }
        GraphicValidationDecoration graphicValidationDecoration = new GraphicValidationDecoration();
        
        ValidationSupport validationNombre = new ValidationSupport();
        ValidationSupport validationLugar = new ValidationSupport();
        ValidationSupport validationTipo = new ValidationSupport();
        ValidationSupport validationPremio = new ValidationSupport();
        ValidationSupport validationFecha = new ValidationSupport();

        Validador.validacionTexto(validationNombre, txtNombre, 3, 50, "Los nombres deben tener entre 3 y 50 caracteres");
        Validador.validacionTexto(validationLugar, txtLugar, 3, 50, "El lugar debe tener entre 3 y 50 caracteres");
        Validador.validacionTexto(validationTipo, txtTipo, 3, 50, "El tipo debe tener entre 3 y 50 caracteres");
        Validador.validacionNumero(validationPremio, txtPremio, 10, 10000, "Los premios deben ser entre 10 y 10000");
        
        validationFecha.registerValidator(datePickerFecha, false, (control, value) -> {
            // Comprobar si el campo de fecha está vacío
            if (value == null) {
                return ValidationResult.fromWarning(control, "El campo fecha no puede estar vacío");
            }
            return ValidationResult.fromInfo(control, "Fecha válida");
        });
        
        validadores = new ArrayList<>();
        validadores.addAll(Arrays.asList(validationNombre, validationLugar, validationTipo, validationPremio, validationFecha));
        
    }
    
    @FXML
    private void seleccionarFoto() {
        FileChooser fileChooser = new FileChooser();

        // Configurar filtro para imágenes
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        // Abrir diálogo para seleccionar el archivo
        Stage stage = (Stage) fotoBtn.getScene().getWindow();
        File archivoSeleccionado = fileChooser.showOpenDialog(stage);

        if (archivoSeleccionado != null) {
            try {
                // Cargar la imagen seleccionada directamente sin copiarla al directorio
                Image imagen = new Image(archivoSeleccionado.toURI().toString());

                // Actualizar el gráfico del botón con la nueva imagen
                ImageView imageView = new ImageView(imagen);
                imageView.setFitHeight(50);
                imageView.setFitWidth(50);
                fotoBtn.setGraphic(imageView);

                // No actualizamos el objeto Atleta ni guardamos nada en el disco

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    
    public void iniciarVentana(Competicion competicion) {
        this.competicion = competicion;
        
        if (competicion != null) { // Modo edición
             labelAñadir.setText("Editando competición");
            txtNombre.setText(competicion.getNombre());
            txtTipo.setText(competicion.getTipo());
            txtLugar.setText(competicion.getLugar());
            datePickerFecha.setValue(competicion.getFecha().toLocalDate());
            txtPremio.setText(competicion.getPremio());
        } else { // Modo inserción
            labelAñadir.setText("Añadiendo competición");
            txtNombre.clear();
            txtTipo.clear();
            txtLugar.clear();
            datePickerFecha.setValue(null);
            txtPremio.clear();
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
        }else if (txtNombre.getText().isEmpty() || txtLugar.getText().isEmpty() ||
             txtPremio.getText().isEmpty() || txtTipo.getText().isEmpty() || 
             datePickerFecha.getValue() == null) {
        
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errores en el formulario");
            alert.setContentText("Todos los campos deben ser completados.");
            alert.showAndWait();
            return;
        }
        
        String nombre = txtNombre.getText();
        String lugar = txtLugar.getText();
        String tipo = txtTipo.getText();
        String premio = txtPremio.getText();
        Double premioD = Double.parseDouble(premio);
        String imagen = null;

        LocalDate fechaSeleccionada = datePickerFecha.getValue(); // Actualizado
        
        java.sql.Date fechaSQL = java.sql.Date.valueOf(fechaSeleccionada);

        // Si existe una foto, convertimos la imagen a Base64
        if (competicion == null && fotoBtn.getGraphic() == null) {
            System.out.println("cacjhoerror");
             URL defaultImageUrl = getClass().getClassLoader().getResource("img/default.png");
            imagen = defaultImageUrl != null ? defaultImageUrl.toExternalForm() : null;
            
        }else if(fotoBtn.getGraphic() == null && competicion.getImagen()!=null){
            imagen = competicion.getImagen();
        } else if (fotoBtn.getGraphic() instanceof ImageView) {
            ImageView imageView = (ImageView) fotoBtn.getGraphic();
            try {
                // Convertir la imagen a un array de bytes
                Image imagen2 = imageView.getImage();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(SwingFXUtils.fromFXImage(imagen2, null), "png", baos);
                byte[] imageBytes = baos.toByteArray();

                // Convertir el array de bytes a Base64
                imagen = Base64.getEncoder().encodeToString(imageBytes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (competicion == null) {
            String query = "INSERT INTO competiciones (nombre, lugar, fecha, tipo, premio, imagen) VALUES (?, ?, ?, ?, ?, ?)";

            try {
                PreparedStatement preparedStatement = this.conexion.prepareStatement(query);
                preparedStatement.setString(1, nombre);
                preparedStatement.setString(2, lugar);
                preparedStatement.setDate(3, fechaSQL);
                preparedStatement.setString(4, tipo);
                preparedStatement.setDouble(5, premioD);
                preparedStatement.setString(6, imagen);
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                System.out.println("Excepción: "+e.getMessage());
            }
        }else{
            String query = "UPDATE competiciones SET nombre = ?, lugar = ?, fecha = ?, tipo = ?, premio = ?, imagen = ? WHERE id_competicion = ?";
            try {
                PreparedStatement preparedStatement = this.conexion.prepareStatement(query);
                preparedStatement.setString(1, nombre);
                preparedStatement.setString(2, lugar);
                preparedStatement.setDate(3, fechaSQL);
                preparedStatement.setString(4, tipo);
                preparedStatement.setDouble(5, premioD);
                preparedStatement.setString(6, imagen);
                preparedStatement.setInt(7, competicion.getId_competicion());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Excepción al editar: " + e.getMessage());
            }
        }
        if (controladorPrincipal != null) {
                    String mensaje = (competicion == null) 
                        ? "Competición insertada correctamente." 
                        : "Competición actualizada correctamente.";

                    controladorPrincipal.mostrarMensajeSuperpuesto(mensaje, 5000, controladorPrincipal.mensajeSuperpuestoCompe); // Mensaje por 3 segundos
                    controladorPrincipal.realizarOperacionConLoader(controladorPrincipal.PICompe, controladorPrincipal.tableViewCompeticiones);
                    controladorPrincipal.actualizarCompeticionesTable();
                    controladorPrincipal.actualizarResultadosTable();
        }

        if (competicion == null) { 
            // Modo inserción
            competicion = new Competicion(0, nombre, lugar, fechaSQL, tipo, premio, imagen);
        } else { 
            // Modo edición
            competicion.setNombre(nombre);
            competicion.setLugar(lugar);
            competicion.setFecha(fechaSQL);
            competicion.setTipo(tipo);
            competicion.setPremio(premio);
        }

        // Cerrar la ventana
        Stage stage = (Stage) txtPremio.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    void cancelar() {
        Stage stage = (Stage) txtPremio.getScene().getWindow();
        stage.close();
    }

    
   

    
    
}
