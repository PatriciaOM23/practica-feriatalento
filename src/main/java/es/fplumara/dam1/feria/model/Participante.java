package es.fplumara.dam1.feria.model;

public abstract class Participante {
     private String id;
     private String nombres;
     private String centro;

    public Participante(String id, String nombres, String centro) {
        this.id = id;
        this.nombres = nombres;
        this.centro = centro;
    }

    public String getId() {
        return id;
    }

    public String getNombres() {
        return nombres;
    }

    public String getCentro() {
        return centro;
    }
}
