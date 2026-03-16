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

public class ParticipanteCsvReader {

    /**
     * Lee participantes.csv y devuelve filas como DTOs (sin crear objetos del dominio).
     * Reglas mínimas:
     * - cabecera obligatoria con: tipo,id,nombre,centro,curso,departamento,organizacion,nivel
     * - tipo debe ser ALUMNO/DOCENTE/INVITADO (sin espacios)
     */
    public List<ParticipanteCsvRow> read(Path csvPath) throws IOException {
        if (csvPath == null) throw new IllegalArgumentException("csvPath no puede ser null");

        CSVFormat format = CSVFormat.DEFAULT
                .builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setTrim(true)
                .build();

        List<ParticipanteCsvRow> rows = new ArrayList<>();

        try (Reader reader = Files.newBufferedReader(csvPath, StandardCharsets.UTF_8);
             CSVParser parser = format.parse(reader)) {

            for (CSVRecord r : parser) {
                String tipo = getRequired(r, "tipo");
                // sin espacios y en mayúsculas (por si vienen minúsculas)
                tipo = tipo.trim().toUpperCase();

                if (!tipo.equals("ALUMNO") && !tipo.equals("DOCENTE") && !tipo.equals("INVITADO")) {
                    throw new IllegalArgumentException("Tipo no válido: " + tipo);
                }

                String id = getRequired(r, "id");
                String nombre = getRequired(r, "nombre");
                String centro = getRequired(r, "centro");

                // Estos pueden venir vacíos según tipo (se validará al transformar o en service)
                String curso = getOptional(r, "curso");
                String departamento = getOptional(r, "departamento");
                String organizacion = getOptional(r, "organizacion");
                String nivel = getOptional(r, "nivel");

                rows.add(new ParticipanteCsvRow(tipo, id, nombre, centro, curso, departamento, organizacion, nivel));
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

    private static String getOptional(CSVRecord record, String header) {
        String v = record.isMapped(header) ? record.get(header) : null;
        return v == null ? "" : v.trim();
    }
}