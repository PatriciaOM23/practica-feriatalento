package es.fplumara.dam1.feria.io;

public record RankingCsvRow(
        String idParticipante,
        String nombre,
        String centro,
        int puntos
) {}