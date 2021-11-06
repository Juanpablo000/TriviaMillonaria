package com.example.triviamillonaria;

public class Comodin {
    private int imgFoto;
    private String titulo;
    private String descripcion;

    public Comodin(int imgFoto, String titulo, String descripcion) {
        this.imgFoto = imgFoto;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public int getImgFoto() {
        return imgFoto;
    }

    public void setImgFoto(int imgFoto) {
        this.imgFoto = imgFoto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
