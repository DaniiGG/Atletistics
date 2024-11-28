package com.example.Modelos;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.Date;
import java.util.Base64;
import javafx.scene.image.Image;

/**
 *
 * @author danie
 */
public class Competicion {
    
    private int id_competicion;
    private String nombre;
    private String lugar;
    private Date fecha;
    private String tipo;
    private String premio;
    private String imagen;

    public Competicion(int id_competicion, String nombre, String lugar, Date fecha, String tipo, String premio, String imagen) {
        this.id_competicion = id_competicion;
        this.nombre = nombre;
        this.lugar = lugar;
        this.fecha = fecha;
        this.tipo = tipo;
        this.premio = premio;
        this.imagen = imagen;
    }

    public int getId_competicion() {
        return id_competicion;
    }

    public void setId_competicion(int id_competicion) {
        this.id_competicion = id_competicion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getPremio() {
        return premio;
    }

    public void setPremio(String premio) {
        this.premio = premio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
    

    public Image getFoto() {
    try {
        // Si el `fotoPath` es Base64
        if (imagen != null && imagen.matches("^[A-Za-z0-9+/=]+$")) {
            // Decodifica la cadena Base64
            byte[] imageBytes = Base64.getDecoder().decode(imagen);

            // Crea un flujo de bytes a partir de la imagen decodificada
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);

            // Crea y retorna la imagen a partir del flujo
            return new Image(bis);
        }

        // Si el `fotoPath` es una URL remota (http o https)
        if (imagen != null && (imagen.startsWith("http://") || imagen.startsWith("https://"))) {
            return new Image(imagen, true); // `true` permite la carga asincrónica
        }

        // Si el `fotoPath` es una ruta local (en el proyecto)
        if (imagen != null) {
            URL resourceUrl = getClass().getClassLoader().getResource(imagen);
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

    public void setFoto(String imagen) {
        this.imagen = imagen;
    }
    
    
    
}
