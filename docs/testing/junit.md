## **Importaciones recomendadas**

En la mayoría de tests:

```
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;
```

---


# **1) Anotaciones principales**

## **1.1 @Test**

**Qué hace:** marca un método como test ejecutable.

**Cuándo usarlo:** siempre que quieras que JUnit ejecute ese método.

**Cuándo no:** en métodos auxiliares.

**Errores típicos:** olvidarlo → “¿por qué no se ejecuta?”

```java
@Test
void shouldReturn5_whenAdding2And3() {
    Calculator c = new Calculator();
    assertEquals(5, c.add(2, 3));
}
```

---

## **1.2 @BeforeEach**

**Qué hace:** se ejecuta antes de cada test.

**Cuándo:** crear el SUT (clase a testear) y dejar estado limpio.

**Cuándo no:** para asserts o lógica del test.

**Error típico:** guardar estado que afecta a otros tests.

```java
class CalculatorTest {
    Calculator c;

    @BeforeEach
    void setUp() {
        c = new Calculator();
    }

    @Test
    void add_works() {
        assertEquals(4, c.add(2, 2));
    }
}
```

---

## **1.3 @AfterEach**

**Qué hace:** se ejecuta después de cada test (pase o falle).

**Cuándo:** cerrar recursos (ficheros, streams, etc.).

**Cuándo no:** para validar resultados.

**Error típico:** “limpiar” cosas que el test necesita para comprobar.

```java
class ResourceTest {
    FakeResource r;

    @BeforeEach void setUp() { r = new FakeResource(); }
    @AfterEach  void tearDown() { r.close(); }

    @Test
    void resourceIsOpenDuringTest() {
        assertTrue(r.isOpen());
    }

    static class FakeResource {
        private boolean open = true;
        void close() { open = false; }
        boolean isOpen() { return open; }
    }
}
```

---

## **1.4 @BeforeAll / @AfterAll**

**Qué hace:** se ejecutan una sola vez por clase de test.

**Cuándo:** inicialización global (costosa).

**Cuándo no:** crear objetos que cambian por test.

**Errores típicos:** olvidar static (si no usas @TestInstance(PER_CLASS)).

```java
class GlobalLifecycleTest {
    static int initCount = 0;

    @BeforeAll
    static void initAll() { initCount = 1; }

    @AfterAll
    static void finishAll() { initCount = 0; }

    @Test
    void initWasCalled() {
        assertEquals(1, initCount);
    }
}
```

---

## **1.5 @TestInstance**

**Qué hace:** controla si JUnit crea una instancia por test o una por clase.

- PER_METHOD (default): más seguro
- PER_CLASS: permite @BeforeAll sin static pero ojo con estado compartido

**Error típico:** usar PER_CLASS y que un test “ensucie” a otro.

```java
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PerClassLifecycleTest {
    int counter;

    @BeforeAll
    void init() { counter = 10; }

    @Test
    void counterIs10() {
        assertEquals(10, counter);
    }
}
```

---

# **2) Assertions (comprobaciones)**

Import:

```java
import static org.junit.jupiter.api.Assertions.*;
```

## **2.1 assertEquals / assertNotEquals**

**Qué hace:** compara valor esperado vs real.

**Cuándo:** resultados deterministas.

**Error típico:** intercambiar orden (pon siempre expected, actual).

```java
@Test
void equalsExamples() {
    assertEquals(5, 2 + 3);
    assertNotEquals(6, 2 + 3);
}
```

---

## **2.2 assertTrue / assertFalse**

**Qué hace:** comprueba condiciones booleanas.

**Cuándo:** validaciones, flags, reglas.

**Error típico:** condiciones confusas (mejor expresiones simples).

```java
@Test
void booleanExamples() {
    int age = 18;
    assertTrue(age >= 18);
    assertFalse(age < 0);
}
```

---

## **2.3 assertNull / assertNotNull**

**Qué hace:** comprueba null.

**Cuándo:** Optional mal usado, valores esperados, objetos creados.

**Error típico:** usar null como “éxito” sin definir contrato.

```java
@Test
void nullExamples() {
    String a = null;
    String b = "hola";
    assertNull(a);
    assertNotNull(b);
}
```

---

## **2.4 assertSame / assertNotSame**

**Qué hace:** comprueba si es el MISMO objeto (misma referencia).

**Cuándo:** singleton, caché, objetos compartidos.

**Cuándo no:** para comparar contenido.

**Error típico:** confundir “igual” con “misma referencia”.

```java
@Test
void sameReferenceExamples() {
    Object x = new Object();
    Object y = x;
    Object z = new Object();

    assertSame(x, y);
    assertNotSame(x, z);
}
```

---

## **2.5 assertArrayEquals / assertIterableEquals**

**Qué hace:** compara arrays / iterables por contenido.

**Error típico:** usar assertEquals en arrays (compara referencias).

```java
@Test
void collectionsExamples() {
    assertArrayEquals(new int[]{1,2,3}, new int[]{1,2,3});
    assertIterableEquals(
        java.util.List.of("a","b"),
        java.util.List.of("a","b")
    );
}
```

---

## **2.6 Mensajes en asserts**

**Qué hace:** ayuda a entender fallos en segundos.

**Cuándo:** siempre que el fallo pueda ser confuso.

```java
@Test
void messageExample() {
    int result = 2 + 2;
    assertEquals(4, result, "2 + 2 debería ser 4");
}
```

---

## **2.7 assertAll**

**Qué hace:** agrupa asserts de un mismo escenario y muestra todos los fallos.

**Cuándo:** validar un objeto con varias propiedades.

**Cuándo no:** mezclar comportamientos.

**Error típico:** meter “de todo” en el mismo assertAll.

```java
@Test
void assertAllExample() {
    User u = new User("ivan", true, 100);

    assertAll("user",
        () -> assertEquals("ivan", u.name()),
        () -> assertTrue(u.active()),
        () -> assertEquals(100, u.points())
    );
}

record User(String name, boolean active, int points) {}
```

---

## **2.8 assertThrows**

**Qué hace:** comprueba que se lanza una excepción.

**Cuándo:** validaciones, inputs inválidos.

**Error típico:** usar try/catch manual.

```java
@Test
void shouldThrowException_whenAgeIsNegative() {
    IllegalArgumentException ex = assertThrows(
        IllegalArgumentException.class,
        () -> validateAge(-5)
    );

    assertEquals("La edad no puede ser negativa", ex.getMessage());
}

static void validateAge(int age) {
    if (age < 0) {
        throw new IllegalArgumentException("La edad no puede ser negativa");
    }
```

---

## **2.9 assertDoesNotThrow**

**Qué hace:** verifica que no se lanza excepción.

**Cuándo:** “caso correcto” del método.

```java
@Test
void doesNotThrowExample() {
    assertDoesNotThrow(() -> divide(10, 2));
}
```

---

## **2.10 Timeouts**

**Qué hace:** asegura que un bloque no excede un tiempo.

- assertTimeout: espera a que termine
- assertTimeoutPreemptively: corta antes

```java
import java.time.Duration;

@Test
void timeoutExample() {
    assertTimeout(Duration.ofMillis(50), () -> Thread.sleep(10));
}

@Test
void preemptiveTimeoutExample() {
    assertTimeoutPreemptively(Duration.ofMillis(50), () -> Thread.sleep(10));
}
```

---

# **3) Assumptions ( assumeTrue )**

**Qué hace:** si la condición no se cumple, el test se marca como skipped.

**Cuándo:** diferencias por SO/CI/variables.

**Cuándo no:** para ocultar bugs.

```java
@Test
void onlyOnWindows() {
    assumeTrue(System.getProperty("os.name").toLowerCase().contains("windows"));
    assertTrue(true); // si llegamos aquí, está habilitado
}
```

---

# **4) Parametrized Tests**

> Requiere junit-jupiter-params (misma versión que JUnit).
>

## **4.1 @ParameterizedTest + @CsvSource**

**Qué hace:** ejecuta el mismo test con varias filas de datos.

**Cuándo:** reglas de negocio, límites, tablas de casos.

```java
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@ParameterizedTest
@CsvSource({
    "1,2,3",
    "2,3,5",
    "-1,1,0"
})
void addCsv(int a, int b, int expected) {
    assertEquals(expected, a + b);
}
```

---

## **4.2 @ValueSource**

```java
import org.junit.jupiter.params.provider.ValueSource;

@ParameterizedTest
@ValueSource(ints = {0, 1, 2, 10})
void nonNegative(int value) {
    assertTrue(value >= 0);
}
```

---

## **4.3 @NullSource / @EmptySource / @NullAndEmptySource**

```java
import org.junit.jupiter.params.provider.NullAndEmptySource;

@ParameterizedTest
@NullAndEmptySource
@org.junit.jupiter.params.provider.ValueSource(strings = {"   "})
void invalidNames(String name) {
    assertTrue(name == null || name.trim().isEmpty());
}
```

---

## **4.4 @MethodSource**

**Qué hace:** datos complejos generados por un método.

**Cuándo:** necesitas objetos, listas, casos más ricos.

```java
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

static Stream<Arguments> ageCases() {
    return Stream.of(
        Arguments.of(0, true),
        Arguments.of(120, true),
        Arguments.of(121, false)
    );
}

@ParameterizedTest
@MethodSource("ageCases")
void validAge(int age, boolean expected) {
    boolean actual = age >= 0 && age <= 120;
    assertEquals(expected, actual);
}
```

---

## **4.5 @EnumSource**

```java
import java.time.DayOfWeek;
import org.junit.jupiter.params.provider.EnumSource;

@ParameterizedTest
@EnumSource(DayOfWeek.class)
void dayNameNotEmpty(DayOfWeek day) {
    assertFalse(day.name().isEmpty());
}
```

---

# **5) Organización y legibilidad**

## **5.1 @DisplayName**

**Qué hace:** nombre humano en el reporte.

```java
import org.junit.jupiter.api.DisplayName;

@Test
@DisplayName("Divide lanza excepción si el divisor es cero")
void divideByZeroThrows() {
    assertThrows(IllegalArgumentException.class, () -> divide(10, 0));
}
```

---

## **5.2 @Nested**

**Qué hace:** agrupa tests por escenario (“cuando pasa X…”).

**Cuándo:** servicios con varios casos.

```java
import org.junit.jupiter.api.Nested;

class LoginServiceTest {

    boolean check(String expected, String input) {
        return expected.equals(input);
    }

    @Nested
    class WhenPasswordIsCorrect {
        @Test
        void returnsTrue() {
            assertTrue(check("1234", "1234"));
        }
    }

    @Nested
    class WhenPasswordIsWrong {
        @Test
        void returnsFalse() {
            assertFalse(check("1234", "0000"));
        }
    }
}
```

---

## **5.3 @DisplayNameGeneration**

**Qué hace:** genera nombres automáticos para tests.

**Cuándo:** proyectos grandes.

---

# **6) Control de ejecución**

## **6.1 @Disabled**

**Qué hace:** desactiva un test (se marca como skipped).

**Cuándo:** feature futura o bug conocido.

```java
import org.junit.jupiter.api.Disabled;

@Disabled("Pendiente de implementar la validación avanzada")
@Test
void disabledExample() {
    fail("No debería ejecutarse");
}
```

---

## **6.2 @Tag**

**Qué hace:** etiqueta tests (fast, slow, integration…).

**Cuándo:** separar suites.

```java
import org.junit.jupiter.api.Tag;

@Tag("fast")
@Test
void fastTest() { assertTrue(true); }

@Tag("slow")
@Test
void slowTest() { assertTrue(true); }
```

---

## **6.3 @RepeatedTest (+ RepetitionInfo)**

**Qué hace:** repite un test N veces.

**Cuándo:** estabilidad, flakiness.

```java
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

@RepeatedTest(3)
void repeatedExample(RepetitionInfo info) {
    assertTrue(info.getCurrentRepetition() <= info.getTotalRepetitions());
}
```

---

## **6.4 Orden de tests ( @TestMethodOrder )**

⚠️ **No recomendado** para unit tests (pero existe).

```java
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderedTest {

    @Test @Order(1)
    void first() { assertTrue(true); }

    @Test @Order(2)
    void second() { assertTrue(true); }
}
```

---

# **7) Condiciones por sistema (anotaciones)**

**Qué hace:** habilita/deshabilita tests según SO, Java, variables.

**Cuándo:** compatibilidad.

**Cuándo no:** para ocultar errores.

```java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

class OsConditionTest {
    @Test
    @EnabledOnOs(OS.WINDOWS)
    void onlyWindows() {
        assertTrue(true);
    }
}
```

---

# **8) Tests dinámicos ( @TestFactory)**

**Qué hace:** genera tests en runtime.

**Cuándo:** casos masivos o bonus.

```java
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

class DynamicTestsExample {

    @TestFactory
    Stream<DynamicTest> dynamicTests() {
        return Stream.of(1, 2, 3).map(n ->
            DynamicTest.dynamicTest("n=" + n, () -> assertTrue(n > 0))
        );
    }
}
```

---

# **9) Utilidades que dan nivel**

## **9.1 @TempDir**

**Qué hace:** crea una carpeta temporal segura (se borra al acabar).

**Cuándo:** exportar ficheros sin tocar el sistema real.

```java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

class TempDirExample {

    @TempDir Path tempDir;

    @Test
    void writesFile() throws Exception {
        Path file = tempDir.resolve("a.txt");
        Files.writeString(file, "hola");

        assertTrue(Files.exists(file));
        assertEquals("hola", Files.readString(file));
    }
}
```

---

## **9.2 TestInfo y TestReporter**

**Qué hacen:**

- TestInfo: información del test (nombre, método)
- TestReporter: publicar “logs” estructurados

```java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;

class MetaExample {

    @Test
    void meta(TestInfo info, TestReporter reporter) {
        reporter.publishEntry("displayName", info.getDisplayName());
        assertNotNull(info.getTestMethod().orElse(null));
    }
}
```