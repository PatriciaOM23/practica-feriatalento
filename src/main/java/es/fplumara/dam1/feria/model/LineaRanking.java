package es.fplumara.dam1.feria.model;

public class LineaRanking {
    private String idParticipante;
    private String nombre;
    private String centro;
    private int puntos;

    public LineaRanking(String idParticipante, String nombre, String centro, int puntos) {
        this.idParticipante = idParticipante;
        this.nombre = nombre;
        this.centro = centro;
        this.puntos = puntos;
    }

    public String getIdParticipante() {
        return idParticipante;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCentro() {
        return centro;
    }

    public int getPuntos() {
        return puntos;
    }
}
