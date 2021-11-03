package com.example.triviamillonaria;

public class Pregunta {
    private String descripcion;
    private String opciones[];
    private String dificultad;


    public Pregunta(String descripcion, String dificultad, String[] opciones) {
        this.opciones = opciones;
        this.dificultad = dificultad;
        this.descripcion = descripcion;
    }


    public String getDescripcion(){
        return descripcion;
    }

    public String getDificultad(){
        return dificultad;
    }

    public String[] getOpciones(){
        return opciones;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setOpciones(String[] opciones) {
        this.opciones = opciones;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

}

