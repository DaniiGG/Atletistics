/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Controladores;

import com.example.Modelos.Atleta;
import com.example.Modelos.Competicion;
import com.example.Database.DatabaseConnection;
import com.example.Database.DatabaseManager;
import com.example.Modelos.Resultados;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author danie
 */
public class Controlador implements Initializable{
    
    @FXML
    private void mostrarResultados() {
        vboxResultados.setVisible(true);
        vboxCompeticiones.setVisible(false);
        vboxAtletas.setVisible(false);
    }

    @FXML
    private void mostrarCompeticiones() {
        vboxResultados.setVisible(false);
        vboxCompeticiones.setVisible(true);
        vboxAtletas.setVisible(false);
    }

    @FXML
    private void mostrarAtletas() {
        vboxResultados.setVisible(false);
        vboxCompeticiones.setVisible(false);
        vboxAtletas.setVisible(true);
    }
    
    @FXML private TableView<Resultados> tableViewResultados;
    @FXML private TableColumn<Resultados, String> columnAcciones;
    @FXML private TableColumn<Resultados, String> columnEvento;
    @FXML private TableColumn<Resultados, String> columnMarca;
    @FXML private TableColumn<Resultados, Integer> columnPuesto;
    @FXML private TableColumn<Resultados, Integer> columnDorsal;
    @FXML private TableColumn<Resultados, String> columnNombreAtleta;
    @FXML private TableColumn<Resultados, ImageView> columnFoto;
    
    @FXML private TableView<Competicion> tableViewCompeticiones;
    @FXML private TableColumn<Competicion, String> columnNombreCompeticion;
    @FXML private TableColumn<Competicion, ImageView> columnImagenCompeticion;
    @FXML private TableColumn<Competicion, String> columnTipoCompeticion;
    @FXML private TableColumn<Competicion, Date> columnFechaCompeticion;
    @FXML private TableColumn<Competicion, String> columnLugarCompeticion;
    @FXML private TableColumn<Competicion, String> columnPremioCompeticion;
    @FXML private TableColumn<Competicion, String> columnAccionesCompeticiones;

    @FXML private GridPane gridAtletas;
    @FXML private Button btnAtle;

    @FXML private Button btnAñadir;

    @FXML private Button btnCompe;

    @FXML private Button btnResul;

    @FXML private GridPane gridMenu;

    @FXML private GridPane gridTitTabla;

    @FXML private GridPane gridtabla;

    @FXML private HBox hboxTabla;

    @FXML private HBox hboxTitulo;

    @FXML private Label lTitulo;

    @FXML private VBox menuIzda;

    @FXML private VBox vboxContent;

    @FXML private VBox vboxResultados;
    @FXML private VBox vboxCompeticiones;
    @FXML private VBox vboxAtletas;
    
    
    
    //------CRUD DE LA TABLA RESULTADOS---------

    @FXML
    void añadirResul() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Ventanas/editarInsertarResultado.fxml"));
                Parent root = loader.load();
                EditarInsertarResultadoControlador controller = loader.getController();
                controller.iniciarVentana(null); 

                Stage stage = new Stage();
                stage.setTitle("Insertar Resultado");
                stage.setScene(new Scene(root));
                stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
                stage.getIcons().add(new Image("img/AtletisticsLogo.jpg"));
                stage.showAndWait();

                tableViewResultados.refresh();

            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    
    
    private void editarResultado(Resultados resultado) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Ventanas/editarInsertarResultado.fxml"));
            Parent root = loader.load();
            EditarInsertarResultadoControlador controller = loader.getController();
            controller.iniciarVentana(resultado);

            Stage stage = new Stage();
            stage.setTitle("Editar Resultado");
            stage.setScene(new Scene(root));
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image("img/AtletisticsLogo.jpg"));
            stage.showAndWait();

            tableViewResultados.refresh();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void borrarResultado(Resultados resultado) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("Eliminar resultado");
        alert.setContentText("¿Estás seguro de que deseas eliminar resultado de "+resultado.getNombreAtleta()+"?");

        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image("img/AtletisticsLogo.jpg"));
        
        alert.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            //tableViewResultados.getItems().remove(resultado);
            System.out.println("Resultado eliminado: " + resultado);
        } else {
            System.out.println("Eliminación cancelada.");
        }
    }
    
    
    //----------CRUD DE LA TABLA COMPETICIONES---------------
    
    @FXML
    void añadirCompe() {
        
        try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Ventanas/editarInsertarCompeticion.fxml"));
                Parent root = loader.load();
                EditarInsertarCompeticionControlador controller = loader.getController();
                controller.iniciarVentana(null);

                Stage stage = new Stage();
                stage.setTitle("Insertar Competicion");
                stage.setScene(new Scene(root));
                stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
                stage.getIcons().add(new Image("img/AtletisticsLogo.jpg"));
                stage.showAndWait();

                tableViewCompeticiones.refresh();

            } catch (IOException e) {
                e.printStackTrace();
            }

    }
    
    private void editarCompeticion(Competicion competicion) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Ventanas/editarInsertarCompeticion.fxml"));
            Parent root = loader.load();
            EditarInsertarCompeticionControlador controller = loader.getController();
            controller.iniciarVentana(competicion);

            Stage stage = new Stage();
            stage.setTitle("Editar Resultado");
            stage.setScene(new Scene(root));
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image("img/AtletisticsLogo.jpg"));
            stage.showAndWait();

            tableViewCompeticiones.refresh();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void borrarCompeticion(Competicion competicion) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("Eliminar competición");
        alert.setContentText("¿Estás seguro de que deseas eliminar resultado de "+competicion.getNombre()+"?");
        
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image("img/AtletisticsLogo.jpg"));

        alert.initModality(javafx.stage.Modality.APPLICATION_MODAL);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            //tableViewResultados.getItems().remove(resultado);
            System.out.println("Competición eliminado: " + competicion);
        } else {
            System.out.println("Eliminación cancelada.");
        }
    }

    
    
    
    
    //------------CRUD DE LA TABLA ATLETAS--------------
    @FXML
    void añadirAtleta() {
        try 
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Ventanas/editarInsertarAtleta.fxml"));
            Parent root = loader.load();
            EditarInsertarAtletaControlador controller = loader.getController();
            controller.iniciarVentana(null);

            Stage stage = new Stage();
            stage.setTitle("Insertar Atleta");
            stage.setScene(new Scene(root));
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image("img/AtletisticsLogo.jpg"));
            stage.showAndWait();

            tableViewCompeticiones.refresh();

        } catch (IOException e) {
               e.printStackTrace();
        }
    }
    
    private void editarAtleta(Atleta atleta) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Ventanas/editarInsertarAtleta.fxml"));
            Parent root = loader.load();
            EditarInsertarAtletaControlador controller = loader.getController();
            controller.iniciarVentana(atleta);

            Stage stage = new Stage();
            stage.setTitle("Editar Atleta");
            stage.setScene(new Scene(root));
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image("img/AtletisticsLogo.jpg"));
            stage.showAndWait();

            tableViewCompeticiones.refresh();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void borrarAtleta(Atleta atleta) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("Eliminar atleta");
        alert.setContentText("¿Estás seguro de que deseas eliminar resultado de "+atleta.getNombre()+"?");
        
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image("img/AtletisticsLogo.jpg"));

        alert.initModality(javafx.stage.Modality.APPLICATION_MODAL);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            //tableViewResultados.getItems().remove(resultado);
            System.out.println("Atleta eliminado: " + atleta);
        } else {
            System.out.println("Eliminación cancelada.");
        }
    }
    


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //----------CREACION DE COLUMNAS DE COMPETICIONES---------------
        
        columnNombreCompeticion.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnFechaCompeticion.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        columnLugarCompeticion.setCellValueFactory(new PropertyValueFactory<>("lugar"));
        columnTipoCompeticion.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        columnPremioCompeticion.setCellValueFactory(new PropertyValueFactory<>("premio"));
        
        columnImagenCompeticion.setCellValueFactory(cellData -> {
            return cargarImagen(cellData.getValue().getImagen());
        });

        columnAccionesCompeticiones.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Competicion, String> call(final TableColumn<Competicion, String> param) {
                return new TableCell<>() {
                    private final Button btnEdit = new Button("Editar");
                    private final Button btnDelete = new Button("Borrar");
                    private final HBox actionButtons = new HBox(btnEdit, btnDelete);

                    {
                        actionButtons.setSpacing(10);

                        btnEdit.setOnAction(event -> {
                            Competicion competicion = getTableView().getItems().get(getIndex());
                            editarCompeticion(competicion);
                        });

                        btnDelete.setOnAction(event -> {
                            Competicion competicion = getTableView().getItems().get(getIndex());
                            borrarCompeticion(competicion);
                        });
                    }

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(actionButtons);
                        }
                    }
                };
            }
        });
        tableViewCompeticiones.setItems(obtenerCompeticionesDesdeDB());
        
        
        
        
        //-----------CREACIÓN DE COLUMNAS DE RESULTADOS------------
        
        columnEvento.setCellValueFactory(new PropertyValueFactory<>("evento"));
        columnMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        columnPuesto.setCellValueFactory(new PropertyValueFactory<>("puesto"));
        columnDorsal.setCellValueFactory(new PropertyValueFactory<>("dorsal"));
        columnNombreAtleta.setCellValueFactory(new PropertyValueFactory<>("nombreAtleta"));

        /*columnFoto.setCellValueFactory(
                /*cellData -> {
            ImageView imageView = new ImageView(cellData.getValue().getFotoAtleta());
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            return new SimpleObjectProperty<>(imageView);
        });*/


        columnFoto.setCellValueFactory(cellData -> {
            return cargarImagen(cellData.getValue().getFotoAtletaURL());
        });

     
        columnAcciones.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Resultados, String> call(final TableColumn<Resultados, String> param) {
                return new TableCell<>() {
                    private final Button btnEdit = new Button("Editar");
                    private final Button btnDelete = new Button("Borrar");
                    private final HBox actionButtons = new HBox(btnEdit, btnDelete);

                    {
                        actionButtons.setSpacing(10);

                        // Acción al hacer clic en "Editar"
                        btnEdit.setOnAction(event -> {
                            Resultados resultado = getTableView().getItems().get(getIndex());
                            editarResultado(resultado);
                        });

                        // Acción al hacer clic en "Borrar"
                        btnDelete.setOnAction(event -> {
                            Resultados resultado = getTableView().getItems().get(getIndex());
                            borrarResultado(resultado);
                        });
                    }

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(actionButtons);
                        }
                    }
                };
            }
        });
    

    tableViewResultados.setItems(obtenerResultadosDesdeDB());
    
    
    
    
    //----------------CREACIÓN DE CARDS DE ATLETAS----------------
    
        ObservableList<Atleta> atletas = obtenerAtletasDesdeDB();
        int maxColumns = 3;
        int column = 0;
        int row = 0;

        gridAtletas.setHgap(20);
        gridAtletas.setVgap(40);
        gridAtletas.setMaxWidth(Double.MAX_VALUE);
        gridAtletas.setMaxHeight(Double.MAX_VALUE);

        for (Atleta atleta : atletas) {
            VBox card = new VBox();
            card.setSpacing(10);
            card.setPadding(new Insets(15));
            card.setAlignment(Pos.CENTER);
            card.setStyle("-fx-border-color: gray; -fx-border-width: 1; -fx-background-color: #f9f9f9;");

            ImageView imageView = new ImageView(new Image(atleta.getFotoPath()));
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);

            Label nombreLabel = new Label("Nombre: " + atleta.getNombre());
            Label apellidosLabel = new Label("Apellidos: " + atleta.getApellidos());
            Label edadLabel = new Label("Edad: " + atleta.getEdad());
            Label clubLabel = new Label("Club: " + atleta.getClub());
            Label activoLabel = new Label("Activo: " + (atleta.isActivo() ? "Sí" : "No"));

            Button btnEdit = new Button("Editar");
            Button btnDelete = new Button("Borrar");
            btnEdit.setOnAction(event -> editarAtleta(atleta));
            btnDelete.setOnAction(event -> borrarAtleta(atleta));

            HBox actionButtons = new HBox(btnEdit, btnDelete);
            actionButtons.setSpacing(10);
            actionButtons.setAlignment(Pos.CENTER);

            card.getChildren().addAll(imageView, nombreLabel, apellidosLabel, edadLabel, clubLabel, activoLabel, actionButtons);

            gridAtletas.add(card, column, row);

            column++;
            if (column == maxColumns) {  
                column = 0;
                row++;
            }
        }
        
    }
    
    
    //--------OBTENCIÓN DE ATLETAS DE BASE DE DATOS-----------
    
    private ObservableList<Atleta> obtenerAtletasDesdeDB() {

          String query = "SELECT dni, nombre, apellidos, edad, club, activo, foto FROM atletas";
          
        return DatabaseManager.executeQuery(query, rs -> {
            ObservableList<Atleta> atletas = FXCollections.observableArrayList();

            while (rs.next()) {
                atletas.add(new Atleta(
                    rs.getString("dni"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getInt("edad"),
                    rs.getString("club"),
                    rs.getBoolean("activo"),
                    rs.getString("foto")
                ));
            }

            return atletas;
        });
    }
    
    
    
    //---------OBTENCIÓN DE COMPETICIONES DE BASE DE DATOS----------
    private ObservableList<Competicion> obtenerCompeticionesDesdeDB() {
        
         String query ="""
                    SELECT id_competicion, nombre, tipo, fecha, lugar, premio, imagen
                    FROM competiciones
                    """;
        
        return DatabaseManager.executeQuery(query, rs -> {
            ObservableList<Competicion> competiciones = FXCollections.observableArrayList();

            while (rs.next()) {
                Competicion competicion = new Competicion(
                    rs.getInt("id_competicion"),
                    rs.getString("nombre"),
                    rs.getString("tipo"),
                    rs.getDate("fecha"),
                    rs.getString("lugar"),
                    rs.getString("premio"),    
                    rs.getString("imagen")
                );
                competiciones.add(competicion);
            }

        return competiciones;
        });
    }
    
    
    //-------------OBTENCIÓN DE RESULTADOS DE BASE DE DATOS----------
    
    private ObservableList<Resultados> obtenerResultadosDesdeDB() {
        

            String query = """
                    SELECT r.id_resultado, r.marca, r.puesto, r.dorsal, c.nombre AS evento,
                           a.dni, a.nombre, a.apellidos, a.edad, a.club, a.activo, a.foto
                    FROM resultados r
                    JOIN atletas a ON r.dni_atleta = a.dni
                    JOIN competiciones c ON r.id_competicion = c.id_competicion
                    """;
               return DatabaseManager.executeQuery(query, rs -> {
                    ObservableList<Resultados> resultados = FXCollections.observableArrayList();
               

            while (rs.next()) {
                Atleta atleta = new Atleta(
                    rs.getString("dni"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getInt("edad"),
                    rs.getString("club"),           
                    rs.getBoolean("activo"),       
                    rs.getString("foto")
                );

                Resultados resultado = new Resultados(
                    rs.getInt("id_resultado"),
                    rs.getString("evento"),
                    rs.getString("marca"),
                    rs.getInt("puesto"),
                    rs.getInt("dorsal"),
                    atleta
                );

                resultados.add(resultado);
            }
        

        return resultados;
        });
    }
    
    
    //--------FUNCIÓN PARA LA CARGA DE IMAGENES----------
    private SimpleObjectProperty<ImageView> cargarImagen(String urlString) {
        try {
            URL defaultImageUrl = getClass().getClassLoader().getResource("img/default.png");
            Image defaultImage = new Image(defaultImageUrl.toExternalForm());

            if (urlString == null || urlString.isEmpty()) {
                ImageView errorImageView = new ImageView(defaultImage);
                errorImageView.setFitHeight(50);
                errorImageView.setFitWidth(50);
                return new SimpleObjectProperty<>(errorImageView);
            }

            Image image = new Image(urlString, true); 
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            return new SimpleObjectProperty<>(imageView);

        } catch (Exception e) {
            e.printStackTrace(); 
            URL defaultImageUrl = getClass().getClassLoader().getResource("img/default.png");
            ImageView errorImageView = new ImageView(new Image(defaultImageUrl.toExternalForm()));
            errorImageView.setFitHeight(50);
            errorImageView.setFitWidth(50);
            return new SimpleObjectProperty<>(errorImageView);
        }
    }

}
