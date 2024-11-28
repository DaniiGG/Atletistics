/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Controladores;

import com.example.Controladores.Controlador;
import com.example.Modelos.Atleta;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import javax.xml.validation.*;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;
import org.w3c.dom.Node;

/**
 *
 * @author danie
 */
public class EditarInsertarAtletaControlador extends Controlador implements Initializable{
    
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
        try {
            conexion = conexionHelper.getConnection();
            if (conexion != null) {
                this.st = conexion.createStatement();
            }
        } catch (IOException | SQLException e) {
        }
    
        ValidationSupport validationNombre = new ValidationSupport();
        ValidationSupport validationApellidos = new ValidationSupport();
        ValidationSupport validationClub = new ValidationSupport();
        ValidationSupport validationEdad = new ValidationSupport();
        ValidationSupport validationDni = new ValidationSupport();
        
        // Decoradores gráficos
        GraphicValidationDecoration graphicValidationDecoration = new GraphicValidationDecoration();
        // Validación para el campo de nombre (entre 3 y 50 caracteres)
        Validador.validacionTexto(validationNombre, txtNombre, 3, 50, "Los nombres deben tener entre 3 y 50 caracteres");
        Validador.validacionTexto(validationApellidos, txtApellidos, 3, 50, "Los apellidos deben tener entre 3 y 50 caracteres");
        Validador.validacionTexto(validationClub, txtClub, 3, 50, "Los apellidos deben tener entre 3 y 50 caracteres");
        Validador.validacionNumero(validationEdad, txtEdad, 10, 80, "Las edades deben ser entre 10 y 80");


        // Validación para DNI (9 caracteres: 8 números y una letra)
       validationDni.registerValidator(txtDni, false, (control, texto) -> {
            String textoStr = texto != null ? texto.toString().trim() : "";

            if (textoStr.matches("\\d{8}[A-Za-z]")) {
                return ValidationResult.fromInfo(control, "DNI válido");
            } else if (textoStr.isEmpty()) {
                return ValidationResult.fromWarning(control, "El campo no puede estar vacío");
            } else {
                return ValidationResult.fromError(control, "El DNI debe contener exactamente 9 caracteres (8 números y 1 letra)");
            }
        });
        
        validadores = new ArrayList<>();
        validadores.addAll(Arrays.asList(validationNombre, validationApellidos, validationClub, validationEdad, validationDni));
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
    
    public void iniciarVentana(Atleta atleta) {
        this.atleta = atleta;
        
        if (atleta != null) { // Modo edición
             labelAñadir.setText("Editando atleta");
            txtDni.setText(atleta.getDni());
            txtNombre.setText(atleta.getNombre());
            txtApellidos.setText(atleta.getApellidos());
            txtEdad.setText(String.valueOf(atleta.getEdad()));
            txtClub.setText(atleta.getClub());
            activoCheck.setSelected(atleta.isActivo());
            //fotoBtn.set
        } else { // Modo inserción
             labelAñadir.setText("Añadiendo atleta");
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
        }else if (txtNombre.getText().isEmpty() || txtApellidos.getText().isEmpty() ||
             txtDni.getText().isEmpty() || txtClub.getText().isEmpty() || 
             txtEdad.getText().isEmpty()) {
        
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errores en el formulario");
            alert.setContentText("Todos los campos deben ser completados.");
            alert.showAndWait();
            return;
        }
        
        String dni = txtDni.getText();
        String nombre = txtNombre.getText();
        String apellidos = txtApellidos.getText();
        int edad = Integer.parseInt(txtEdad.getText());
        String club = txtClub.getText();
        Boolean activo = activoCheck.isSelected();
        String foto = null;

        // Si existe una foto, convertimos la imagen a Base64
        if (atleta == null && fotoBtn.getGraphic() == null) {
            System.out.println("cacjhoerror");
             URL defaultImageUrl = getClass().getClassLoader().getResource("img/default.png");
            foto = defaultImageUrl != null ? defaultImageUrl.toExternalForm() : null;
            
        }else if(fotoBtn.getGraphic() == null && atleta.getFotoPath()!=null){
            foto = atleta.getFotoPath();
        } else if (fotoBtn.getGraphic() instanceof ImageView) {
            ImageView imageView = (ImageView) fotoBtn.getGraphic();
            try {
                // Convertir la imagen a un array de bytes
                Image imagen = imageView.getImage();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(SwingFXUtils.fromFXImage(imagen, null), "png", baos);
                byte[] imageBytes = baos.toByteArray();

                // Convertir el array de bytes a Base64
                foto = Base64.getEncoder().encodeToString(imageBytes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

   
        if (atleta == null) { // Modo Inserción
            String query = "INSERT INTO atletas (dni, nombre, apellidos, edad, club, activo, foto) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try {
                PreparedStatement preparedStatement = this.conexion.prepareStatement(query);
                preparedStatement.setString(1, dni);
                preparedStatement.setString(2, nombre);
                preparedStatement.setString(3, apellidos);
                preparedStatement.setInt(4, edad);
                preparedStatement.setString(5, club);
                preparedStatement.setBoolean(6, activo);
                preparedStatement.setString(7, foto);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Excepción al insertar: " + e.getMessage());
            }
        } else { // Modo Edición
            String query = "UPDATE atletas SET dni= ?, nombre = ?, apellidos = ?, edad = ?, club = ?, activo = ?, foto = ? WHERE dni = ?";
            try {
                PreparedStatement preparedStatement = this.conexion.prepareStatement(query);
                preparedStatement.setString(1, dni);
                preparedStatement.setString(2, nombre);
                preparedStatement.setString(3, apellidos);
                preparedStatement.setInt(4, edad);
                preparedStatement.setString(5, club);
                preparedStatement.setBoolean(6, activo);
                preparedStatement.setString(7, foto);
                preparedStatement.setString(8, dni);
                preparedStatement.executeUpdate();
                System.out.println(foto);
            } catch (SQLException e) {
                System.out.println("Excepción al editar: " + e.getMessage());
            }
        }
         
        if (atleta == null) { 
            atleta = new Atleta(dni, nombre, apellidos, edad, club, activo, foto);
            
        } else { 
        // Modo editar
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
        Platform.runLater(() -> {
           
            PauseTransition pause = new PauseTransition(Duration.millis(1000));
            pause.setOnFinished(event -> {
                if (controladorPrincipal != null) {
                    controladorPrincipal.actualizarGrid();  
                }
            });
            pause.play();
        });
    }

    @FXML
    void cancelar() {
        Stage stage = (Stage) txtDni.getScene().getWindow();
        stage.close();
    }
    
    
}
