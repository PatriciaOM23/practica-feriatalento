# **Rúbrica — ENTORNOS DE DESARROLLO (ED) (10,0)**

## **ED1. GitHub Project + Issues (2,0)**

**Evidencias:** Project en el fork + columnas + issues creadas y movidas.

- **2,0**: Project con 4 columnas correctas + ≥2 issues bien descritas + movidas (mínimo: To do → In progress → Done).
- **1,5**: Project correcto + issues creadas pero uso parcial (no se mueven o descripciones pobres).
- **1,0**: Project creado pero incompleto (faltan columnas o issues).
- **0,0**: no hay Project o no hay issues.

## **ED2. Flujo Git: ramas + PR (3,0)**

**Evidencias:** rama feature/*, commits, PR a main.

- **3,0**: trabaja en feature/* y **todo entra por PR**, sin commits directos a main; PR funcional.
- **2,0**: hay rama y PR, pero un desliz menor (ej. un commit directo a main o PR poco clara).
- **1,0**: hay rama pero integración incorrecta o confusa (merge raro / PR sin sentido).
- **0,0**: todo en main sin PR / sin ramas.

## **ED3. PR con auto revisión y trazabilidad (1,5)**

**Evidencias:** comentario de self-review + cierre de issue.

- **1,5**: PR con ≥1 comentario de auto revisión (checklist o nota) **y** referencia/cierre de issue (Closes #...).
- **1,0**: cumple solo una de las dos (review o closes).
- **0,5**: comentario mínimo sin intención de revisión o trazabilidad dudosa.
- **0,0**: sin self-review ni enlace/cierre de issues.

## **ED4. Maven: dependencias + plugins + ejecución (1,5)**

**Evidencias:** pom.xml + comandos.

- **1,5**: pom.xml correcto (deps pedidas) + exec-maven-plugin configurado y funciona:
    - mvn clean test ✅
    - mvn exec:java ✅
- **1,0**: casi todo ok, pero algún detalle menor (p.ej. versión distinta pero compila/ejecuta).
- **0,5**: compila, pero falta algo importante (p.ej. exec:java no funciona).
- **0,0**: no compila o no ejecuta con Maven.

## **ED5. Tests unitarios (JUnit + Mockito) del ranking() (2,0)**

**Evidencias:** tests pasan y usan Mockito con repos mock.

- **2,0**:
    - @ExtendWith(MockitoExtension.class)
    - mocks **solo** de repos
    - dominio real (no mocks)
    - when(...).thenReturn(...) preparando repos
    - verify(...) (mínimo verify(participanteRepo).listAll() y verify(asistenciaRepo).listAll())
    - cubre **los 3 casos mínimos** del enunciado (bonus+suma, ordenación, sin asistencias).
- **1,5**: cumple estructura Mockito + verify, pero falta 1 caso mínimo **o** hay un fallo menor de preparación.
- **1,0**: tests pasan pero cobertura muy justa o Mockito pobre (sin verify o sin aislar bien repos).
- **0,5**: hay tests pero no pasan o están rotos/incompletos.
- **0,0**: sin tests.

### **Penalizaciones ED (opcional, para incumplimientos graves)**

- **2,0** si mvn clean test no pasa.
- **1,0** si no existe PR (merge directo a main).
- **0,5** si no hay evidencia de tablero usado (issues no movidas).