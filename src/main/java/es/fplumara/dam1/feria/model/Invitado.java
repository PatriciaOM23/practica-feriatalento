package es.fplumara.dam1.feria.model;

public class Invitado extends Participante{
    private String organizacion;

    public Invitado(String id, String nombres, String centro) {
        super(id, nombres, centro);
    }

    public String getOrganizacion() {
        return organizacion;
    }
}
