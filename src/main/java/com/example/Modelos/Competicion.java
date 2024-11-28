package com.example.Modelos;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.sql.Date;

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

    public void setFoto(String imagen) {
        this.imagen = imagen;
    }
    
    
    
}
