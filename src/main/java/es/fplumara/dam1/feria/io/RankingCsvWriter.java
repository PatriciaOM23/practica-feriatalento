package es.fplumara.dam1.feria.io;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class RankingCsvWriter {

    public void write(String  path, List<RankingCsvRow> rows) throws IOException {
        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException("La ruta no puede ser null/vacía");
        }
        if (rows == null) {
            throw new IllegalArgumentException("La lista de registros no puede ser null");
        }

        Path outPath = Path.of(path);


        if (outPath == null) throw new IllegalArgumentException("outPath no puede ser null");
        if (rows == null) throw new IllegalArgumentException("rows no puede ser null");

        CSVFormat format = CSVFormat.DEFAULT
                .builder()
                .setHeader("idParticipante", "nombre", "centro", "puntos")
                .build();

        try (Writer writer = Files.newBufferedWriter(outPath, StandardCharsets.UTF_8);
             CSVPrinter printer = new CSVPrinter(writer, format)) {

            for (RankingCsvRow row : rows) {
                printer.printRecord(row.idParticipante(), row.nombre(), row.centro(), row.puntos());
            }
        }
    }
}