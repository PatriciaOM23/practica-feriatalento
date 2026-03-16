package es.fplumara.dam1.feria.repository;

import es.fplumara.dam1.feria.model.Asistencia;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AsistenciaRepositoryImpl implements AsistenciaRepository {

    private Map<String,Asistencia> datos ;

    public AsistenciaRepositoryImpl() {
        this.datos = new HashMap<>();
    }

    @Override
    public void save(Asistencia a) {
        datos.put(a.getId(), a);
    }

    @Override
    public Optional<Asistencia> findeByid(String id) {
       return Optional.ofNullable(datos.get(id));
    }

    @Override
    public List<Asistencia> listAll() {
        return datos.values().stream().toList();
    }

    @Override
    public boolean existsByActividadYParticipante(String idActividad, String idParticipante) {
        return false;
    }
}
