package es.fplumara.dam1.feria.repository;

import es.fplumara.dam1.feria.model.Participante;

import java.util.List;
import java.util.Optional;

public interface ParticipanteRepository {

    void save(Participante p);
    Optional<Participante> findeByid(String id);
    List<Participante> listAll();
}
