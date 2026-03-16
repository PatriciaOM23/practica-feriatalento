package es.fplumara.dam1.feria.app;

public class Main {

    public static void main(String[] args) throws Exception {

        // ============================================================
        // 0) RUTAS DE ENTRADA/SALIDA (no hace falta menú)
        // ============================================================
        // - Ajusta las rutas si en tu proyecto están en otra carpeta.


        // ============================================================
        // 1) CREAR REPOSITORIOS (implementaciones concretas con Map)
        // ============================================================


        // ============================================================
        // 2) CREAR EL SERVICE (pasando repositorios en el constructor)
        // ============================================================


        // ============================================================
        // 3) LEER CSV DE PARTICIPANTES (te lo damos hecho en el paquete io)
        // ============================================================
        // - El reader devuelve DTOs/records (ParticipanteCsvRow).
        // - TU TRABAJO AQUÍ: transformar cada fila a tu MODELO de dominio:
        //   - Si tipo = ALUMNO -> crear Alumno(...)
        //   - Si tipo = DOCENTE -> crear Docente(...)
        //   - Si tipo = INVITADO -> crear Invitado(...)
        // - Pista: NivelCredencial sale del String "nivel" usando valueOf(...)
        // - Después, registrar cada participante con: service.registrarParticipante(...)


        // ============================================================
        // 4) LEER CSV DE ASISTENCIAS
        // ============================================================
        // - El reader devuelve AsistenciaCsvRow con horas/valoración ya parseados a int.
        // - TU TRABAJO AQUÍ: transformar cada fila a tu entidad Asistencia del dominio.
        // - Después, registrar cada asistencia con: service.registrarAsistencia(...)



        // ============================================================
        // 5) MOSTRAR RESULTADOS POR CONSOLA
        // ============================================================

        // 5.1) Set de centros participantes


        // 5.2) Ranking ordenado


        // ============================================================
        // 6) ESCRIBIR ranking.csv
        // ============================================================
        // - TU TRABAJO AQUÍ: transformar tu ranking (LineaRanking) a RankingCsvRow
        // - y usar RankingCsvWriter para escribirlo a disco.


    }
}