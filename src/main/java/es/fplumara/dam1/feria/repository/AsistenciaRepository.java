package es.fplumara.dam1.feria.repository;

import es.fplumara.dam1.feria.model.Asistencia;

import java.util.List;
import java.util.Optional;

public interface AsistenciaRepository {
    void save (Asistencia a);
    Optional<Asistencia> findeByid(String id);
    List<Asistencia> listAll();
    boolean existsByActividadYParticipante(String idActividad, String idParticipante);
}
