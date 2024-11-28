/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Controladores;

import com.example.Modelos.Atleta;
import com.example.Modelos.Competicion;
import com.example.Modelos.Resultados;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.System.exit;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
    
    Connection conexion;
    Statement st;
    ResultSet rs;
    
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

    @FXML protected GridPane gridAtletas;
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
                controller.setControladorPrincipal(this); 
                controller.iniciarVentana(null); 

                Stage stage = new Stage();
                stage.setTitle("Insertar Resultado");
                stage.setScene(new Scene(root));
                stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
                stage.getIcons().add(new Image("img/AtletisticsLogo.jpg"));
                stage.setOnCloseRequest(e -> {
                e.consume(); 
                stage.hide();
                });
                stage.show();


            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    
    
    private void editarResultado(Resultados resultado) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Ventanas/editarInsertarResultado.fxml"));
            Parent root = loader.load();
            EditarInsertarResultadoControlador controller = loader.getController();
            controller.setControladorPrincipal(this); 
            controller.iniciarVentana(resultado);

            Stage stage = new Stage();
            stage.setTitle("Editar Resultado");
            stage.setScene(new Scene(root));
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image("img/AtletisticsLogo.jpg"));
            stage.setOnCloseRequest(e -> {
                e.consume(); 
                //if (controller != null) {
                 //  this.actualizarGrid(); 
                //}
                stage.hide();
            });
            stage.show();

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
            String query = "DELETE FROM resultados  WHERE id_resultado = ?";
            try (PreparedStatement preparedStatement = this.conexion.prepareStatement(query)) {
                
                preparedStatement.setInt(1, resultado.getId());
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Resultado eliminado: " + resultado.getNombreCompe());
                        Platform.runLater(() -> actualizarResultadosTable());
                } else {
                    System.out.println("No se encontró el resultado con id: " + resultado.getId());
                }
            } catch (SQLException e) {
                System.out.println("Error al eliminar resultdao: " + e.getMessage());
            }
            
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
                
                controller.setControladorPrincipal(this); 
                controller.iniciarVentana(null);

                Stage stage = new Stage();
                stage.setTitle("Insertar Competicion");
                stage.setScene(new Scene(root));
                stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
                stage.getIcons().add(new Image("img/AtletisticsLogo.jpg"));
                
                stage.setOnCloseRequest(e -> {
                e.consume(); 
                stage.hide();
                });
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }

    }
    
    private void editarCompeticion(Competicion competicion) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Ventanas/editarInsertarCompeticion.fxml"));
            Parent root = loader.load();
            EditarInsertarCompeticionControlador controller = loader.getController();
            controller.setControladorPrincipal(this); 
            controller.iniciarVentana(competicion);

            Stage stage = new Stage();
            stage.setTitle("Editar Resultado");
            stage.setScene(new Scene(root));
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image("img/AtletisticsLogo.jpg"));
            stage.setOnCloseRequest(e -> {
                e.consume(); 
                //if (controller != null) {
                 //  this.actualizarGrid(); 
                //}
                stage.hide();
            });
            stage.show();


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

            String query = "DELETE FROM competiciones WHERE id_competicion = ?";
            try (PreparedStatement preparedStatement = this.conexion.prepareStatement(query)) {
                
                preparedStatement.setInt(1, competicion.getId_competicion());
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Competición eliminada: " + competicion.getNombre());
                        Platform.runLater(() -> actualizarCompeticionesTable());
                } else {
                    System.out.println("No se encontró la competición con id: " + competicion.getId_competicion());
                }
            } catch (SQLException e) {
                System.out.println("Error al eliminar compe: " + e.getMessage());
            }
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
            
            controller.setControladorPrincipal(this); 
            controller.iniciarVentana(null);

            Stage stage = new Stage();
            stage.setTitle("Insertar Atleta");
            stage.setScene(new Scene(root));
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image("img/AtletisticsLogo.jpg"));
            
             stage.setOnCloseRequest(e -> {
                e.consume(); 
                //if (controller != null) {
                 //  this.actualizarGrid(); 
                //}
                stage.hide();
            });
            stage.show();


        } catch (IOException e) {
               e.printStackTrace();
        }
    }
    
    private void editarAtleta(Atleta atleta) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Ventanas/editarInsertarAtleta.fxml"));
            Parent root = loader.load();
            EditarInsertarAtletaControlador controller = loader.getController();
            controller.setControladorPrincipal(this); 
            controller.iniciarVentana(atleta);

            Stage stage = new Stage();
            stage.setTitle("Editar Atleta");
            stage.setScene(new Scene(root));
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image("img/AtletisticsLogo.jpg"));
            stage.setOnCloseRequest(e -> {
                e.consume(); 
                //if (controller != null) {
                 //  this.actualizarGrid(); 
                //}
                stage.hide();
            });
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void borrarAtleta(Atleta atleta) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("Eliminar atleta");
        alert.setContentText("¿Estás seguro de que deseas eliminar a " + atleta.getNombre() + "?");

        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image("img/AtletisticsLogo.jpg"));

        alert.initModality(javafx.stage.Modality.APPLICATION_MODAL);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            String query = "DELETE FROM atletas WHERE dni = ?";
            try (PreparedStatement preparedStatement = this.conexion.prepareStatement(query)) {
                
                preparedStatement.setString(1, atleta.getDni());
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Atleta eliminado: " + atleta.getNombre());
                        Platform.runLater(() -> actualizarGrid());
                } else {
                    System.out.println("No se encontró al atleta con el DNI: " + atleta.getDni());
                }
            } catch (SQLException e) {
                System.out.println("Error al eliminar atleta: " + e.getMessage());
            }
        } else {
            System.out.println("Eliminación cancelada.");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            conexion = this.getConnection();
            if (conexion != null) {
                this.st = conexion.createStatement();
            }
        } catch (IOException | SQLException e) {
        }
        
        actualizarGrid();
        actualizarCompeticionesTable();
        actualizarResultadosTable();
       
       
    }
    
    //-----------CREACIÓN DE COLUMNAS DE RESULTADOS------------
    protected void actualizarResultadosTable(){
         
        columnEvento.setCellValueFactory(new PropertyValueFactory<>("nombreCompe"));
        columnMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        columnPuesto.setCellValueFactory(new PropertyValueFactory<>("puesto"));
        columnDorsal.setCellValueFactory(new PropertyValueFactory<>("dorsal"));
        columnNombreAtleta.setCellValueFactory(new PropertyValueFactory<>("nombreAtleta"));

        columnFoto.setCellValueFactory(cellData -> {
            return cargarImagen(cellData.getValue().getFotoAtleta());
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
        
        
    }
    
     
    //----------CREACION DE COLUMNAS DE COMPETICIONES---------------
        protected void actualizarCompeticionesTable(){
        
        columnNombreCompeticion.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnFechaCompeticion.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        columnLugarCompeticion.setCellValueFactory(new PropertyValueFactory<>("lugar"));
        columnTipoCompeticion.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        columnPremioCompeticion.setCellValueFactory(new PropertyValueFactory<>("premio"));
        
        columnImagenCompeticion.setCellValueFactory(cellData -> {
            ImageView imageView = new ImageView(cellData.getValue().getFoto());
            imageView.setFitHeight(50); 
            imageView.setFitWidth(50);
            return new SimpleObjectProperty<>(imageView);
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
    
    }
    
    //----------------CREACIÓN DE CARDS DE ATLETAS----------------
    protected void actualizarGrid(){
     gridAtletas.getChildren().clear();
        
        ObservableList<Atleta> atletas = obtenerAtletasDesdeDB();
        
        if (atletas == null) {
        atletas = FXCollections.observableArrayList(); // Crear una lista vacía si es null
    }
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

            ImageView imageView = new ImageView(atleta.getFoto());
            
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
    ObservableList<Atleta> atletas = FXCollections.observableArrayList();

        if (conexion != null) {
             String query = "SELECT dni, nombre, apellidos, edad, club, activo, foto FROM atletas";
            try {
                rs = st.executeQuery(query);
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
                   atletas.add(atleta);
                }
            } catch (SQLException e) {
                System.out.println("Excepción SQL: "+e.getMessage());
            }
            return atletas;
        }
        return null;
    }

          
    
    
    
    //---------OBTENCIÓN DE COMPETICIONES DE BASE DE DATOS----------
    private ObservableList<Competicion> obtenerCompeticionesDesdeDB() {
        ObservableList<Competicion> competiciones = FXCollections.observableArrayList();
    
        if (conexion != null) {
        
         String query ="""
                    SELECT id_competicion, nombre, tipo, fecha, lugar, premio, imagen
                    FROM competiciones
                    """;
       
            try {
                rs = st.executeQuery(query);
            while (rs.next()) {
                Competicion competicion = new Competicion(
                    rs.getInt("id_competicion"),
                    rs.getString("nombre"),
                    rs.getString("lugar"),
                    rs.getDate("fecha"),
                    rs.getString("tipo"),
                    rs.getString("premio"),    
                    rs.getString("imagen")
                );
                competiciones.add(competicion);
                }
            
            } catch (SQLException e) {
               System.out.println("Excepción SQL: "+e.getMessage());
            }

        return competiciones;
        }
        return null;
    }
    
    
    //-------------OBTENCIÓN DE RESULTADOS DE BASE DE DATOS----------
    
    private ObservableList<Resultados> obtenerResultadosDesdeDB() {
        ObservableList<Resultados> resultados = FXCollections.observableArrayList();

        if (conexion != null) {
            String query = """
                    SELECT r.id_resultado, r.marca, r.puesto, r.dorsal, c.nombre AS evento,
                                           a.dni, a.nombre, a.apellidos, a.edad, a.club, a.activo, a.foto,
                                           c.id_competicion, c.lugar, c.fecha, c.tipo, c.premio, c.imagen AS fotoCompeticion
                                    FROM resultados r
                                    JOIN atletas a ON r.dni_atleta = a.dni
                                    JOIN competiciones c ON r.id_competicion = c.id_competicion
                    """;
            try {
            rs = st.executeQuery(query);
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
                
                Competicion competicion = new Competicion(
                    rs.getInt("id_competicion"),
                    rs.getString("evento"),
                    rs.getString("lugar"),
                    rs.getDate("fecha"),
                    rs.getString("tipo"),           
                    rs.getString("premio"),       
                    rs.getString("fotoCompeticion")
                );

                Resultados resultado = new Resultados(
                    rs.getInt("id_resultado"),
                    competicion,
                    rs.getString("marca"),
                    rs.getInt("puesto"),
                    rs.getInt("dorsal"),
                    atleta
                );

                resultados.add(resultado);
            }
                } catch (SQLException e) {
               System.out.println("Excepción SQL: "+e.getMessage());
            }
        return resultados;
        }
        return null;
    }
    
    
    //--------FUNCIÓN PARA LA CARGA DE IMAGENES----------
   private SimpleObjectProperty<ImageView> cargarImagen(Image image) {
    try {
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
    
    
    
    
     public Connection getConnection() throws IOException {
        //Importante: hay que separar los datos de conexión del programa, así, al cambiar, no tendría
        //que cambiar nada internamente, o al menos, el mínimo posible.
        Properties properties = new Properties();
        String IP, PORT, BBDD, USER, PWD;
        //Se lee IP desde fuera del jar
        try {
            InputStream input_ip = new FileInputStream("ip.properties");//archivo debe estar junto al jar
            properties.load(input_ip);
            IP = (String) properties.get("IP");
        } catch (FileNotFoundException e) {
            System.out.println("No se pudo encontrar el archivo de propiedades para IP, se establece localhost por defecto");
            IP = "localhost";
        }

        InputStream input = getClass().getClassLoader().getResourceAsStream("db/CredencialesDB.properties");
        if (input == null) {
            System.out.println("No se pudo encontrar el archivo de propiedades");
            return null;
        } else {
            // Cargar las propiedades desde el archivo
            properties.load(input);
            // String IP = (String) properties.get("IP"); //Tiene sentido leerlo desde fuera del Jar por si cambiamos la IP, el resto no debería de cambiar
            //ni debería ser público
            PORT = (String) properties.get("PORT");//En vez de crear con new, lo crea por asignación + casting
            BBDD = (String) properties.get("BBDD");
            USER = (String) properties.get("USER");//USER de MARIADB en LAMP 
            PWD = (String) properties.get("PWD");//PWD de MARIADB en LAMP 

            Connection conn;
            try {
                String cadconex = "jdbc:mariadb://" + IP + ":" + PORT + "/" + BBDD + " USER:" + USER + "PWD:" + PWD;
                System.out.println(cadconex);
                //Si usamos LAMP Funciona con ambos conectores
                conn = DriverManager.getConnection("jdbc:mariadb://" + IP + ":" + PORT + "/" + BBDD, USER, PWD);
                return conn;
            } catch (SQLException e) {
                System.out.println("Error SQL: " + e.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Ha ocurrido un error de conexión");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                exit(0);
                return null;
            }
        }
    }

}


