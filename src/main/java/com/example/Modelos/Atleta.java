package com.example.Modelos;

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
        return new Image("file:" + this.fotoPath); // Asume que la ruta es local
    }
}
