package es.fplumara.dam1.feria.io;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AsistenciaCsvReader {

    /**
     * Lee asistencias.csv y devuelve filas como DTOs.
     * cabecera obligatoria: id,idActividad,idParticipante,horas,valoracion
     */
    public List<AsistenciaCsvRow> read(String ruta) throws IOException {

        if (ruta == null || ruta.isBlank()) {
            throw new IllegalArgumentException("La ruta no puede ser null/vacía");
        }

        Path csvPath = Path.of(ruta);


        CSVFormat format = CSVFormat.DEFAULT
                .builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setTrim(true)
                .build();

        List<AsistenciaCsvRow> rows = new ArrayList<>();

        try (Reader reader = Files.newBufferedReader(csvPath, StandardCharsets.UTF_8);
             CSVParser parser = format.parse(reader)) {

            for (CSVRecord r : parser) {
                String id = getRequired(r, "id");
                String idActividad = getRequired(r, "idActividad");
                String idParticipante = getRequired(r, "idParticipante");

                int horas = parseIntRequired(r, "horas");
                int valoracion = parseIntRequired(r, "valoracion");

                rows.add(new AsistenciaCsvRow(id, idActividad, idParticipante, horas, valoracion));
            }
        }

        return rows;
    }

    private static String getRequired(CSVRecord record, String header) {
        String v = record.get(header);
        if (v == null || v.trim().isEmpty()) {
            throw new IllegalArgumentException("Campo obligatorio vacío: " + header);
        }
        return v.trim();
    }

    private static int parseIntRequired(CSVRecord record, String header) {
        String v = getRequired(record, header);
        try {
            return Integer.parseInt(v);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Campo numérico inválido (" + header + "): " + v);
        }
    }
}