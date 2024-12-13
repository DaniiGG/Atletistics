/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Controladores;

import com.example.Modelos.Atleta;
import com.example.Modelos.Competicion;
import com.example.Modelos.Resultados;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
import java.util.function.Predicate;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

/**
 *
 * @author danie
 */
public class Controlador implements Initializable {

    FXMLLoader loader;
    Connection conexion;
    Statement st;
    ResultSet rs;
    ScaleTransition scaleTransitionA;
    ScaleTransition scaleTransitionB;

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
        actualizarGrid(null);
    }

    @FXML
    protected TableView<Resultados> tableViewResultados;
    @FXML
    private TableColumn<Resultados, String> columnAcciones;
    @FXML
    private TableColumn<Resultados, String> columnEvento;
    @FXML
    private TableColumn<Resultados, String> columnMarca;
    @FXML
    private TableColumn<Resultados, Integer> columnPuesto;
    @FXML
    private TableColumn<Resultados, Integer> columnDorsal;
    @FXML
    private TableColumn<Resultados, String> columnNombreAtleta;
    @FXML
    private TableColumn<Resultados, ImageView> columnFoto;

    @FXML
    protected TableView<Competicion> tableViewCompeticiones;
    @FXML
    private TableColumn<Competicion, String> columnNombreCompeticion;
    @FXML
    private TableColumn<Competicion, ImageView> columnImagenCompeticion;
    @FXML
    private TableColumn<Competicion, String> columnTipoCompeticion;
    @FXML
    private TableColumn<Competicion, Date> columnFechaCompeticion;
    @FXML
    private TableColumn<Competicion, String> columnLugarCompeticion;
    @FXML
    private TableColumn<Competicion, String> columnPremioCompeticion;
    @FXML
    private TableColumn<Competicion, String> columnAccionesCompeticiones;

    @FXML
    private ComboBox<String> comboAtletas;
    private ObservableList<String> listaAtletas = FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> comboEventos;
    @FXML
    private ComboBox<String> comboCompe;
    @FXML
    private ComboBox<String> comboAtleta2;

    private ObservableList<String> listaEventos = FXCollections.observableArrayList();

    @FXML
    protected ProgressIndicator PIResul;
    @FXML
    protected ProgressIndicator PIAtleta;
    @FXML
    protected ProgressIndicator PICompe;

    @FXML
    private GridPane gridAtletas;

    @FXML
    protected ScrollPane scrollPaneAtletas;

    @FXML
    private Button btnAtle;

    @FXML
    private Button btnAñadir;

    @FXML
    private Button btnCompe;

    @FXML
    private Button btnResul;

    @FXML
    private GridPane gridMenu;

    @FXML
    private GridPane gridTitTabla;

    @FXML
    private GridPane gridtabla;

    @FXML
    private HBox hboxTabla;

    @FXML
    private HBox hboxTitulo;

    @FXML
    private Label lTitulo;

    @FXML
    private VBox menuIzda;

    @FXML
    private VBox vboxContent;

    @FXML
    private VBox vboxResultados;
    @FXML
    private VBox vboxCompeticiones;
    @FXML
    private VBox vboxAtletas;

    @FXML
    protected Label mensajeSuperpuestoResul;
    @FXML
    protected Label mensajeSuperpuestoCompe;
    @FXML
    protected Label mensajeSuperpuestoAtle;

    //-----INITIALIZE------
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            conexion = this.getConnection();
            if (conexion != null) {
                this.st = conexion.createStatement();
            }
        } catch (IOException | SQLException e) {
        }
        actualizarGrid(null);
        actualizarCompeticionesTable();
        actualizarResultadosTable();
        cargarNombresEventos(conexion, listaEventos);
        configurarComboBoxEventos();
        configurarComboBoxCompe();
        cargarNombresAtletas(conexion, listaAtletas);
        configurarComboBoxAtletas();
        configurarComboBoxAtletasGrid(comboAtleta2);
        establecerIconoMenu(btnResul, btnCompe, btnAtle);
        btnResul.setContentDisplay(javafx.scene.control.ContentDisplay.TOP);
        btnCompe.setContentDisplay(javafx.scene.control.ContentDisplay.TOP);
        btnAtle.setContentDisplay(javafx.scene.control.ContentDisplay.TOP);

    }

    //------CRUD DE LA TABLA RESULTADOS---------
    private void añadirEditarResultado(String titulo, Resultados resultado) {
        try {
            loader = new FXMLLoader(getClass().getResource("../Ventanas/editarInsertarResultado.fxml"));
            Parent root = loader.load();
            EditarInsertarResultadoControlador controller = loader.getController();
            controller.setControladorPrincipal(this);
            if (resultado == null) {
                controller.iniciarVentana(null);
            } else {
                controller.iniciarVentana(resultado);
            }

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image("img/AtletisticsLogo.jpg"));
            // Efecto de desvanecimiento (Fade In)
            FadeTransition fadeIn = new FadeTransition(Duration.millis(700), root);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);

            // Acción al cerrar la ventana
            stage.setOnCloseRequest(e -> {
                e.consume();
                stage.hide();
            });

            // Mostrar la ventana y reproducir la animación
            stage.show();
            fadeIn.play();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void añadirResul() {
        añadirEditarResultado("Insertar Resultado", null);
    }

    private void editarResultado(Resultados resultado) {
        añadirEditarResultado("Editar Resultado", resultado);
    }

    private void borrarResultado(Resultados resultado) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("Eliminar resultado");
        alert.setContentText("¿Estás seguro de que deseas eliminar resultado de " + resultado.getNombreAtleta() + "?");

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
    private void añadirEditarCompeticion(String titulo, Competicion competicion) {
        try {
            loader = new FXMLLoader(getClass().getResource("../Ventanas/editarInsertarCompeticion.fxml"));
            Parent root = loader.load();
            EditarInsertarCompeticionControlador controller = loader.getController();

            controller.setControladorPrincipal(this);
            if (competicion == null) {
                controller.iniciarVentana(null);
            } else {
                controller.iniciarVentana(competicion);
            }

            Stage stage = new Stage();
            stage.setTitle(titulo);
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

    @FXML
    void añadirCompe() {
        añadirEditarCompeticion("Insertar Competición", null);
    }

    private void editarCompeticion(Competicion competicion) {
        añadirEditarCompeticion("Editar Competición", competicion);
    }

    private void borrarCompeticion(Competicion competicion) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("Eliminar competición");
        alert.setContentText("¿Estás seguro de que deseas eliminar competición de " + competicion.getNombre() + "?");

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
    public void añadirEditarAtleta(String titulo, Atleta atleta) {
        try {
            loader = new FXMLLoader(getClass().getResource("../Ventanas/editarInsertarAtleta.fxml"));
            Parent root = loader.load();
            EditarInsertarAtletaControlador controller = loader.getController();

            controller.setControladorPrincipal(this);
            if (atleta == null) {
                controller.iniciarVentana(null);
            } else {
                controller.iniciarVentana(atleta);
            }

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            root.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
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

    @FXML
    void añadirAtleta() {
        añadirEditarAtleta("Insertar Atleta", null);
    }

    private void editarAtleta(Atleta atleta) {
        añadirEditarAtleta("Editar Atleta", atleta);
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
                    Platform.runLater(() -> actualizarGrid(null));
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

    //-----------CREACIÓN DE COLUMNAS DE RESULTADOS------------
    protected void actualizarResultadosTable() {

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

                        establecerIcono(btnEdit, btnDelete);

                        btnEdit.getStyleClass().addAll("btn", "btn-primary");
                        btnDelete.getStyleClass().addAll("btn", "btn-danger");
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
        menuContext(tableViewResultados,
                item -> añadirEditarResultado("Editar Resultado", item),
                this::borrarResultado);

        tableViewResultados.setItems(obtenerResultadosDesdeDB());

    }

    //----------CREACION DE COLUMNAS DE COMPETICIONES---------------
    protected void actualizarCompeticionesTable() {

        columnNombreCompeticion.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnFechaCompeticion.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        columnLugarCompeticion.setCellValueFactory(new PropertyValueFactory<>("lugar"));
        columnTipoCompeticion.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        columnPremioCompeticion.setCellValueFactory(new PropertyValueFactory<>("premio"));

        columnLugarCompeticion.setCellFactory(new Callback<TableColumn<Competicion, String>, TableCell<Competicion, String>>() {
            @Override
            public TableCell<Competicion, String> call(TableColumn<Competicion, String> param) {
                return new TableCell<Competicion, String>() {
                    private final Button btnLugar = new Button();

                    {
                        btnLugar.setStyle("-fx-background-color: transparent; -fx-text-fill: blue; -fx-underline: true;");
                        btnLugar.setOnAction(event -> {
                            if (!isEmpty()) {
                                Competicion competicion = getTableView().getItems().get(getIndex());
                                String lugar = competicion.getLugar();
                                abrirMapa(lugar);
                            }
                        });
                    }

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null); // Si la celda está vacía, no mostramos nada
                        } else {
                            btnLugar.setText(item); // Establecemos el texto del botón con el lugar
                            setGraphic(btnLugar); // Agregamos el botón a la celda
                        }
                    }
                };
            }
        });

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
                        establecerIcono(btnEdit, btnDelete);

                        btnEdit.getStyleClass().addAll("btn", "btn-primary");
                        btnDelete.getStyleClass().addAll("btn", "btn-danger");
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
        menuContext(tableViewCompeticiones,
                item -> añadirEditarCompeticion("Editar Competición", item),
                this::borrarCompeticion);

        tableViewCompeticiones.setItems(obtenerCompeticionesDesdeDB());

    }

    //----------------CREACIÓN DE CARDS DE ATLETAS----------------
    protected void actualizarGrid(String filtroNombre) {
        gridAtletas.getChildren().clear();

        // Obtener lista de atletas desde la base de datos
        ObservableList<Atleta> atletas = obtenerAtletasDesdeDB();

        if (atletas == null) {
            atletas = FXCollections.observableArrayList(); // Crear una lista vacía si es null
        }

        // Aplicar filtro
        ObservableList<Atleta> atletasFiltrados = atletas.filtered(atleta
                -> filtroNombre == null || filtroNombre.isEmpty()
                || (atleta.getNombre() + " " + atleta.getApellidos()).toLowerCase().contains(filtroNombre.toLowerCase())
        );

        int maxColumns = 3;
        int column = 0;
        int row = 0;

        gridAtletas.setHgap(20);
        gridAtletas.setVgap(40);
        gridAtletas.setMaxWidth(Double.MAX_VALUE);
        gridAtletas.setMaxHeight(Double.MAX_VALUE);

        for (Atleta atleta : atletasFiltrados) {
            VBox card = new VBox();
            card.setSpacing(10);
            card.setPadding(new Insets(15));
            card.setAlignment(Pos.CENTER);
            card.setStyle("-fx-border-color: gray; -fx-border-width: 1; -fx-background-color: #f9f9f9;");

            ImageView imageView = new ImageView(atleta.getFoto());
            aplicarAnimacionDeEscala(imageView);
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);

            Label nombreLabel = crearLabelFormateado("Nombre: ", atleta.getNombre());
            Label apellidosLabel = crearLabelFormateado("Apellidos: ", atleta.getApellidos());
            Label edadLabel = crearLabelFormateado("Edad: ", String.valueOf(atleta.getEdad()));
            Label clubLabel = crearLabelFormateado("Club: ", atleta.getClub());
            Label activoLabel = crearLabelFormateado("Activo: ", atleta.isActivo() ? "Sí" : "No");

            Button btnEdit = new Button("Editar");
            Button btnDelete = new Button("Borrar");

            establecerIcono(btnEdit, btnDelete);

            btnEdit.getStyleClass().addAll("btn", "btn-primary");
            btnDelete.getStyleClass().addAll("btn", "btn-danger");

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
                System.out.println("Excepción SQL: " + e.getMessage());
            }
            return atletas;
        }
        return null;
    }

    //---------OBTENCIÓN DE COMPETICIONES DE BASE DE DATOS----------
    private ObservableList<Competicion> obtenerCompeticionesDesdeDB() {
        ObservableList<Competicion> competiciones = FXCollections.observableArrayList();

        if (conexion != null) {

            String query = """
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
                System.out.println("Excepción SQL: " + e.getMessage());
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
                System.out.println("Excepción SQL: " + e.getMessage());
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

    //----ESTABLECE CONEXIÓN CON LA BASE DE DATOS----
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

    //----ABRE LA VENTANA DEL MAPA-----
    private void abrirMapa(String lugar) {
        try {
            loader = new FXMLLoader(getClass().getResource("../Ventanas/MapView.fxml"));
            Parent root = loader.load();

            MapaControlador controller = loader.getController();
            controller.mostrarMapa(lugar);

            // Crear una nueva ventana
            Stage stage = new Stage();
            stage.setTitle("Mapa de: " + lugar);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //----ANIMACION DE ESCALA DE IMAGEN EN EL APARTADO DE ATLETAS-----
    private void aplicarAnimacionDeEscala(ImageView imageView) {
        // Transición de escala para el zoom
        ScaleTransition scaleTransitionA = new ScaleTransition(Duration.seconds(0.3), imageView);
        scaleTransitionA.setToX(1.5);
        scaleTransitionA.setToY(1.5);

        // Transición de desplazamiento para mover la imagen hacia adelante
        TranslateTransition translateTransitionA = new TranslateTransition(Duration.seconds(0.3), imageView);
        translateTransitionA.setByZ(10);

        // Transición de escala y desplazamiento para el mouseEntered
        imageView.setOnMouseEntered(event -> {
            scaleTransitionA.play();
            translateTransitionA.play();
        });

        // Transición de escala y desplazamiento para el mouseExited
        ScaleTransition scaleTransitionB = new ScaleTransition(Duration.seconds(0.2), imageView);
        scaleTransitionB.setToX(1);
        scaleTransitionB.setToY(1);

        TranslateTransition translateTransitionB = new TranslateTransition(Duration.seconds(0.2), imageView);
        translateTransitionB.setByZ(-10);

        imageView.setOnMouseExited(event -> {
            scaleTransitionA.stop();
            translateTransitionA.stop();
            scaleTransitionB.play();
            translateTransitionB.play();
        });
    }

    //---LABEL FORMATEDO EN GRIDATLETAS----
    public Label crearLabelFormateado(String textoEnNegrita, String textoNormal) {
        Text textoNegrita = new Text(textoEnNegrita);
        textoNegrita.setStyle("-fx-font-weight: bold;");

        Text textoNormalParte = new Text(textoNormal);
        textoNormalParte.setStyle("-fx-font-weight: normal;");

        TextFlow textFlow = new TextFlow(textoNegrita, textoNormalParte);

        Label label = new Label();
        label.setGraphic(textFlow);

        return label;
    }

    //----CREACIÓN Y COFIGURACIÓN DE ICONOS----
    public static void configurarIconoConHover(Button boton, String iconoRuta, String hoverRuta, int n1, int n2) {
        Node iconoNormal = crearIcono(iconoRuta, n1, n2);
        Node iconoHover = crearIcono(hoverRuta, n1, n2);

        if (iconoNormal != null && iconoHover != null) {
            boton.setGraphic(iconoNormal);

            boton.setOnMouseEntered(event -> boton.setGraphic(iconoHover));
            boton.setOnMouseExited(event -> boton.setGraphic(iconoNormal));
        } else {
            System.out.println("Error configurando los íconos para el botón.");
        }
    }

    public static Node crearIcono(String rutaIcono, int n1, int n2) {
        URL iconoUrl = Controlador.class.getClassLoader().getResource(rutaIcono);
        if (iconoUrl != null) {
            Image image = new Image(iconoUrl.toString());
            ImageView icono = new ImageView(image);
            icono.setFitHeight(n1);
            icono.setFitWidth(n2);
            icono.setPreserveRatio(true);
            icono.getStyleClass().add("icono");
            return icono;
        } else {
            System.out.println("No se pudo cargar el ícono de la ruta: " + rutaIcono);
            return null;
        }
    }

    private void establecerIcono(Button botonE, Button botonB) {
        configurarIconoConHover(botonE, "img/iconoLapiz.png", "img/iconoLapizHover.png", 16, 16);
        configurarIconoConHover(botonB, "img/iconoBasura.png", "img/iconoBasuraHover.png", 16, 16);
    }

    private void establecerIconoMenu(Button botonE, Button botonB, Button botonA) {
        configurarIconoConHover(botonE, "img/iconoResul.png", "img/iconoResulHover.png", 50, 50);
        configurarIconoConHover(botonB, "img/iconoTrofeo.png", "img/iconoTrofeoHover.png", 50, 50);
        configurarIconoConHover(botonA, "img/iconoAtleta.png", "img/iconoAtletaHover.png", 50, 50);
    }

    public static void establecerIconoSubVentanas(Button botonE, Button botonB) {
        Controlador.configurarIconoConHover(botonE, "img/iconoTick.png", "img/iconoTickHover.png", 16, 16);
        Controlador.configurarIconoConHover(botonB, "img/iconoCruz.png", "img/iconoCruzHover.png", 16, 16);
    }

    //----CONFIGURACIONES DEL COMBOBOX DEL FILTRO----
    private <T> void configurarComboBoxGenerico(
            ComboBox<String> comboBox,
            FilteredList<String> filteredList,
            ObservableList<T> originalData,
            TableView<T> tableView,
            Predicate<T> filterPredicate,
            Runnable restaurarTabla) {

        configurarComboBox(comboBox, filteredList);

        comboBox.getEditor().textProperty().addListener((obs, oldText, newText) -> {
            if (newText != null && !newText.isEmpty()) {
                // Filtrar los datos originales según el criterio proporcionado
                ObservableList<T> filtrados = FXCollections.observableArrayList();
                for (T item : originalData) {
                    if (filterPredicate.test(item)) {
                        filtrados.add(item);
                    }
                }

                // Asignar los datos filtrados a la tabla
                tableView.setItems(filtrados.isEmpty() ? FXCollections.observableArrayList() : filtrados);
            } else {
                // Restaurar la tabla si el texto está vacío
                restaurarTabla.run();
            }
        });
    }

    private void configurarComboBoxEventos() {
        configurarComboBoxGenerico(
                comboEventos,
                new FilteredList<>(listaEventos, p -> true),
                obtenerResultadosDesdeDB(),
                tableViewResultados,
                resultado -> resultado.getCompeticion().getNombre().toLowerCase()
                        .contains(comboEventos.getEditor().getText().toLowerCase()),
                this::actualizarResultadosTable
        );
    }

    private void configurarComboBoxCompe() {
        configurarComboBoxGenerico(
                comboCompe,
                new FilteredList<>(listaEventos, p -> true),
                obtenerCompeticionesDesdeDB(),
                tableViewCompeticiones,
                compe -> compe.getNombre().toLowerCase()
                        .contains(comboCompe.getEditor().getText().toLowerCase()),
                this::actualizarCompeticionesTable
        );
    }

    private void configurarComboBoxAtletas() {
        configurarComboBoxGenerico(
                comboAtletas,
                new FilteredList<>(listaAtletas, p -> true),
                obtenerResultadosDesdeDB(),
                tableViewResultados,
                resultado -> (resultado.getAtleta().getNombre() + " " + resultado.getAtleta().getApellidos())
                        .toLowerCase().contains(comboAtletas.getEditor().getText().toLowerCase()),
                this::actualizarResultadosTable
        );
    }

    private void configurarComboBoxAtletasGrid(ComboBox<String> comboBox) {
        configurarComboBox(comboBox, new FilteredList<>(listaAtletas, p -> true));

        comboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null && !newSelection.isEmpty()) {
                actualizarGrid(newSelection);
            } else {
                actualizarGrid(null);
            }
        });

        comboBox.getEditor().textProperty().addListener((obs, oldText, newText) -> {
            actualizarGrid(newText);
        });
    }

    public static void configurarComboBox(ComboBox<String> comboBox, FilteredList<String> filteredList) {
        comboBox.setItems(filteredList);
        comboBox.setEditable(true);

        TextField editor = comboBox.getEditor();
        editor.textProperty().addListener((obs, oldText, newText) -> {
            filteredList.setPredicate(item -> item.toLowerCase().startsWith(newText.toLowerCase()));
            if (!comboBox.isShowing()) {
                comboBox.show();
            }
        });

        comboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                editor.positionCaret(editor.getText().length());
            }
        });
    }

    //-----CARGA DE NOMBRES EN COMBO----
    public static void cargarNombresEventos(Connection conexion, ObservableList listaList) {
        String query = "SELECT nombre FROM competiciones";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                listaList.add(resultSet.getString("nombre"));
            }

        } catch (SQLException e) {
            System.out.println("Error al cargar nombres de eventos: " + e.getMessage());
        }
    }

    public static void cargarNombresAtletas(Connection conexion, ObservableList listaList) {
        String query = "SELECT CONCAT(nombre, ' ', apellidos) AS nombreCompleto FROM atletas";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                // Agregar el nombre completo (nombre + apellidos)
                listaList.add(resultSet.getString("nombreCompleto"));
            }

        } catch (SQLException e) {
            System.out.println("Error al cargar nombres de atletas: " + e.getMessage());
        }
    }

    //-----LOADER----
    public void realizarOperacionConLoader(ProgressIndicator PI, Node componente) {
        PI.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        PI.setStyle("-fx-accent: #2196f3; -fx-control-inner-background: transparent;");

        // Mostrar el loader
        PI.setVisible(true);
        componente.setVisible(false);

        new Thread(() -> {
            try {
                Thread.sleep(3000);

                Platform.runLater(() -> {

                    PI.setVisible(false);
                    componente.setVisible(true);
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    //---MENSAJE SUPERPUESTO DE INSERCIÓN O EDICIÓN-----
    public void mostrarMensajeSuperpuesto(String mensaje, int duracionMilisegundos, Label label) {
        label.setLayoutX(label.getScene().getWidth());

        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), label);
        translateTransition.setFromX(label.getScene().getWidth());
        label.setText(mensaje);
        label.setVisible(true);
        translateTransition.setToX(0);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(duracionMilisegundos), label);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        fadeTransition.setOnFinished(event -> label.setVisible(false));

        translateTransition.play();
        fadeTransition.play();
    }

    public void exportarJSON(TableView tableView, String filePath) throws IOException {
        // Obtiene los datos del TableView (usualmente una lista observable)
        ObservableList items = tableView.getItems();

        // Crea un objeto Gson (con formato bonito)
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Convierte la lista de objetos a JSON
        String json = gson.toJson(items);

        // Escribe el JSON a un archivo
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(json);
        }
    }

    @FXML
    void exportarJSON() throws IOException {
        exportarJSON(tableViewResultados, "src/main/resources/json/resultados.json");
    }

    public <T> void exportarCSV(TableView<T> tableView, String filePath) {
        ObservableList<T> items = tableView.getItems();

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {

            String header = String.join(",", tableView.getColumns()
                    .stream()
                    .map(column -> column.getText())
                    .toArray(String[]::new));
            writer.println(header);

            for (T item : items) {
                String row = String.join(",", tableView.getColumns()
                        .stream()
                        .map(column -> {
                            Object cellValue = column.getCellData(item);
                            return cellValue != null ? cellValue.toString() : "";
                        })
                        .toArray(String[]::new));
                writer.println(row);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void exportarCSV() throws IOException {
        exportarCSV(tableViewResultados, "src/main/resources/csv/resultados.csv");
    }

    private <T> void menuContext(TableView<T> tableView,
            ContextMenuAction<T> editAction,
            ContextMenuAction<T> deleteAction) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem editItem = new MenuItem("Editar");
        MenuItem deleteItem = new MenuItem("Eliminar");

        contextMenu.getItems().addAll(editItem, deleteItem);

        editItem.setOnAction(e -> {
            T selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                editAction.execute(selected);
            }
        });

        deleteItem.setOnAction(e -> {
            T selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                deleteAction.execute(selected);
            }
        });

        tableView.setRowFactory(tv -> {
            TableRow<T> row = new TableRow<>();
            row.setOnContextMenuRequested(event -> {
                if (!row.isEmpty()) {
                    contextMenu.show(row, event.getScreenX(), event.getScreenY());
                }
            });
            return row;
        });
    }

    @FunctionalInterface
    interface ContextMenuAction<T> {

        void execute(T item);
    }
}
