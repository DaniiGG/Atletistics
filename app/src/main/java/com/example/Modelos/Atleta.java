package com.example.Modelos;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Base64;
import javafx.scene.image.Image;

public class Atleta {

    private String dni;
    private String nombre;
    private String apellidos;
    private int edad;
    private String club;
    private boolean activo;
    private String fotoPath;

    public Atleta(String dni, String nombre, String apellidos, int edad, String club, boolean activo, String fotoPath) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.club = club;
        this.activo = activo;
        this.fotoPath = fotoPath;
    }


    // Getters y setters
     public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getFotoPath() {
        return fotoPath;
    }

    public void setFotoPath(String fotoPath) {
        this.fotoPath = fotoPath;
    }

public Image getFoto() {
    try {
        // Si el `fotoPath` es Base64
        if (fotoPath != null && fotoPath.matches("^[A-Za-z0-9+/=]+$")) {
            // Decodifica la cadena Base64
            byte[] imageBytes = Base64.getDecoder().decode(fotoPath);

            // Crea un flujo de bytes a partir de la imagen decodificada
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);

            // Crea y retorna la imagen a partir del flujo
            return new Image(bis);
        }

        // Si el `fotoPath` es una URL remota (http o https)
        if (fotoPath != null && (fotoPath.startsWith("http://") || fotoPath.startsWith("https://"))) {
            return new Image(fotoPath, true); // `true` permite la carga asincrónica
        }

        // Si el `fotoPath` es una ruta local (en el proyecto)
        if (fotoPath != null) {
            URL resourceUrl = getClass().getClassLoader().getResource(fotoPath);
            if (resourceUrl != null) {
                return new Image(resourceUrl.toExternalForm());
            }
        }

        // Si no se encuentra nada, usar imagen predeterminada
        URL defaultImageUrl = getClass().getClassLoader().getResource("img/default.png");
        return new Image(defaultImageUrl.toExternalForm());

    } catch (Exception e) {
        e.printStackTrace();
        // Si ocurre un error, retorna la imagen predeterminada
        URL defaultImageUrl = getClass().getClassLoader().getResource("img/default.png");
        return new Image(defaultImageUrl.toExternalForm());
    }
}
}
