package com.example.triviamillonaria;

public class Pregunta {
    private String descripcion;
    private String opciones[];
    private String dificultad;
    private int indiceRespuesta;

    public Pregunta(){

    }

    public Pregunta(String descripcion, String dificultad, String[] opciones, int indiceRespuesta) {
        this.opciones = opciones;
        this.dificultad = dificultad;
        this.descripcion = descripcion;
        this.indiceRespuesta = indiceRespuesta;
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

    public int getIndiceRespuesta() {
        return indiceRespuesta;
    }

    public void setIndiceRespuesta(int indiceRespuesta) {
        this.indiceRespuesta = indiceRespuesta;
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

