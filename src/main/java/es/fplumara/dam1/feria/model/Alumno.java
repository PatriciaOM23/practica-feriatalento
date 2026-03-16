package es.fplumara.dam1.feria.model;

public class Alumno extends Participante implements Bonificable{
   private String curso;
   private NivelCredencial nivel;

    public Alumno(String id, String nombres, String centro) {
        super(id, nombres, centro);
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

    public String getCurso() {
        return curso;
    }

    public NivelCredencial getNivel() {
        return nivel;
    }
}

