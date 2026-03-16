package es.fplumara.dam1.feria.io;

public record ParticipanteCsvRow(
        String tipo,
        String id,
        String nombre,
        String centro,
        String curso,
        String departamento,
        String organizacion,
        String nivel
) {}