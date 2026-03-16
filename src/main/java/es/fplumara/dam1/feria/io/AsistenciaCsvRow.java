package es.fplumara.dam1.feria.io;

public record AsistenciaCsvRow(
        String id,
        String idActividad,
        String idParticipante,
        int horas,
        int valoracion
) {}