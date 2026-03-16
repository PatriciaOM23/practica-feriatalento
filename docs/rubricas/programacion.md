# **Rúbrica — PROGRAMACIÓN (10,00 puntos)**

## **P1. Modelo OO según diagrama (2,0)**

- **2,0**: Participante abstracta + 3 subclases correctas; Bonificable solo en Alumno/Docente; NivelCredencial solo en Alumno/Docente; atributos y getters correctos.
- **1,25**: casi completo con 1 fallo menor.
- **0,50**: estructura parcial o confusa.
- **0,00**: no implementado / no compila.

## **P2. Dominio: reglas de puntos (Asistencia + bonus) (1,25)**

- **1,25**: getPuntosBase() correcto y bonus 0/1/2 en getBonus() según nivel.
- **0,75**: casi correcto con detalle menor.
- **0,25**: incompleto.
- **0,00**: no implementado.

## **P3. Repositorios en memoria con Map (1,75)**

- **1,75**: interfaces + impls; Map real; findById con Optional; listAll devuelve lista; existsByActividadYParticipante correcto.
- **1,25**: funciona pero hay 1 fallo menor (p.ej. listAll devuelve estructura rara o exists... incompleto).
- **0,75**: incompleto pero usable (faltan partes o hay errores de diseño, pero se puede trabajar).
- **0,00**: no usable / no implementado.

## **P4. Service: validaciones + reglas + excepciones (1,75)**

*Se considera validación completa si cubre: null/vacíos, rangos horas/valoración, duplicado por id, existencia de participante, regla 1 asistencia por actividad+participante y excepciones correctas.*

- **1,75**: validaciones completas + excepciones correctas + regla “1 asistencia por actividad y participante”; lógica en service (no en repos).
- **1,25**: casi todo bien, falta 1 validación/regla menor o un caso de excepción.
- **0,75**: service parcial (reglas principales incompletas o validaciones clave ausentes).
- **0,00**: no implementado / no usable.

## **P5. Ranking + ordenación + exclusión sin asistencias (0,75)**

- **0,75**: ranking correcto (suma + bonus), orden puntos desc y nombre asc, excluye sin asistencias.
- **0,50**: casi correcto con 1 fallo.
- **0,00**: no implementado.

## **P6. Set real en centrosParticipantes() (0,50)**

- **0,50**: devuelve Set sin repetidos correctamente.
- **0,00**: no está o no elimina repetidos.

## **P7. Main: integración y transformación DTO↔dominio (2,00)**

*Se considera completo si: registra datos desde DTOs, invoca centrosParticipantes() y ranking(), y genera la salida como List<RankingCsvRow> para escribir el CSV.*

**Qué se evalúa en Main:**

- Crear repos + service
- Leer DTOs *CsvRow
- Transformar DTO → dominio (Alumno/Docente/Invitado y Asistencia)
- Registrar en service
- Llamar a centrosParticipantes() y ranking()
- Transformar LineaRanking → RankingCsvRow y escribir

**Niveles:**

- **2,00**: flujo completo funciona y genera salida coherente; muestra por consola y escribe ranking.csv.
- **1,50**: casi completo (falla escritura o impresión, pero ranking se calcula bien).
- **1,00**: registra datos pero no llega a ranking o no transforma bien.
- **0,50**: Main muy incompleto (solo crea objetos / flujo roto).
- **0,00**: no hay integración.

---

## **Penalizaciones Programación**

- **2,0** si no compila (no se puede evaluar).
- **1,0** si el service no se puede ejecutar (bloquea ranking/centros).
- **0,5** si no respeta paquetes/capas del enunciado.