package es.fplumara.dam1.feria.model;

public class Docente extends Participante implements Bonificable{
   private String departamento;
   private NivelCredencial nivel;


    public Docente(String id, String nombres, String centro) {
        super(id, nombres, centro);
    }

    public String getDepartamento() {
        return departamento;
    }

    public NivelCredencial getNivel() {
        return nivel;
    }

    @Override
    public int getBonus() {
        if (nivel == NivelCredencial.BASIC){
            return 0;
        }if (nivel== NivelCredencial.AVANZADO){
            return 1;
        }if (nivel == NivelCredencial.EXPERTO){
            return 2;
        }
        return 0;
    }
}
