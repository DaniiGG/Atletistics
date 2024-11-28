package com.example.Modelos;

import javafx.scene.image.Image;

public class Resultados {

    private int id;
    private String evento;
    private String marca;
    private int puesto;
    private int dorsal;
    private Atleta atleta;

    public Resultados(int id, String evento, String marca, int puesto, int dorsal, Atleta atleta) {
        this.id=id;
        this.evento = evento;
        this.marca = marca;
        this.puesto = puesto;
        this.dorsal = dorsal;
        this.atleta = atleta;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getPuesto() {
        return puesto;
    }

    public void setPuesto(int puesto) {
        this.puesto = puesto;
    }

    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    public Image getFotoAtleta() {
        return atleta.getFoto();
    }
    public String getFotoAtletaURL() {
        return atleta.getFotoPath();
    }

    public String getNombreAtleta() {
        return atleta.getNombre() + " " + atleta.getApellidos();
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public Atleta getAtleta() {
        return atleta;
    }

    public void setAtleta(Atleta atleta) {
        this.atleta = atleta;
    }
    

    
}
