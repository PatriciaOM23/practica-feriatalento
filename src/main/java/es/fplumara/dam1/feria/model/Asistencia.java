package es.fplumara.dam1.feria.model;

import es.fplumara.dam1.feria.exception.OperacionNoPermitidaException;

public class Asistencia {
    private String id;
    private String idParticipante;
    private String idAcctividad;
    private int horas;
    private int valoracion;

    public Asistencia(String id, String idParticipante, String idAcctividad, int horas, int valoracion) {
        this.id = id;
        this.idParticipante = idParticipante;
        this.idAcctividad = idAcctividad;
        this.horas = horas;
        this.valoracion = valoracion;
    }

    public String getId() {
        return id;
    }

    public String getIdParticipante() {
        return idParticipante;
    }

    public String getIdAcctividad() {
        return idAcctividad;
    }

    public int getHoras() {
        return horas;
    }

    public int getValoracion() {
        return valoracion;
    }
    public int getPuntosBase(){
        int resultado = horas + valoracion;
        if (horas > 0){
            throw new OperacionNoPermitidaException("Operacion no permitida");
        }
        if (valoracion >1 && valoracion < 5){
            throw new OperacionNoPermitidaException("Valoracion fuera de rango");
        }
        return resultado;
    }
}
