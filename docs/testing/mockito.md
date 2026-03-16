## 1) Integración con JUnit 5: @ExtendWith(MockitoExtension.class)**

### **Qué hace**

Le dice a JUnit:

> “En esta clase de tests, Mockito se encarga de inicializar los mocks automáticamente”.
>

### **Cuándo usarlo**

- Siempre que uses @Mock, @Spy, @InjectMocks, @Captor

### **Error típico**

- Olvidarlo → @Mock queda a null y el test revienta

### **Ejemplo**

```java
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest { }
```

---

## **2) Anotaciones principales de Mockito**

## **2.1 @Mock**

### **Qué hace**

Crea un objeto falso que:

- no ejecuta lógica real
- devuelve valores “vacíos” por defecto
- tú lo programas con when(...).thenReturn(...)

### **Cuándo usarlo**

- Para dependencias externas: repos, email, API, etc.

### **Error típico**

- Intentar usarlo como si fuese real sin stubbing

### **Ejemplo**

```java
@Mock UserRepository repo;
```

---

## **2.2 @InjectMocks**

### **Qué hace**

Crea el SUT e intenta inyectar los mocks:

- por constructor (preferido)
- por campos (si no hay constructor)

### **Cuándo usarlo**

- Cuando quieres un test limpio y rápido de preparar

### **Error típico**

- Tener un constructor raro o múltiples constructores y que no inyecte como esperas

### **Ejemplo**

```java
@InjectMocks UserService service;
```

---

## **2.3 @Captor**

### **Qué hace**

Crea un ArgumentCaptor<T> para capturar argumentos usados en llamadas.

### **Cuándo usarlo**

- Cuando el objeto se crea dentro del SUT y no puedes compararlo “a pelo”

### **Ejemplo**

```java
@Captor ArgumentCaptor<User> userCaptor;
```

---

## **2.4 @Spy**

### **Qué hace**

Crea un objeto real “envuelto”:

- por defecto ejecuta métodos reales
- puedes stubear algunos métodos

### **Cuándo usarlo**

- Muy puntualmente (último recurso)
- Cuando necesitas parte real + parte controlada

### **Error típico**

- when(spy.method()) ejecuta el método real → te explota
- Mejor: doReturn(...)

---

# **3) Caso base completo (mini-proyecto “empresa”)**

### **Código de producción (ejemplo)**

```java
interface UserRepository {
    boolean existsByEmail(String email);
    void save(User user);
}

interface EmailSender {
    void sendWelcomeEmail(String email);
}

record User(String email) {}

class UserService {
    private final UserRepository repo;
    private final EmailSender emailSender;

    UserService(UserRepository repo, EmailSender emailSender) {
        this.repo = repo;
        this.emailSender = emailSender;
    }

    void register(String email) {
        if (repo.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already used");
        }
        repo.save(new User(email));
        emailSender.sendWelcomeEmail(email);
    }
}
```

### **Test completo**

```java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock UserRepository repo;
    @Mock EmailSender emailSender;

    @InjectMocks UserService service;

    @Test
    void register_whenEmailIsNew_savesAndSendsEmail() {
        // Arrange (stubbing)
        when(repo.existsByEmail("a@b.com")).thenReturn(false);

        // Act
        service.register("a@b.com");

        // Assert (verify)
        verify(repo).save(new User("a@b.com"));
        verify(emailSender).sendWelcomeEmail("a@b.com");
    }

    @Test
    void register_whenEmailExists_throwsAndDoesNothingElse() {
        when(repo.existsByEmail("a@b.com")).thenReturn(true);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.register("a@b.com")
        );

        assertEquals("Email already used", ex.getMessage());
        verify(repo, never()).save(any());
        verify(emailSender, never()).sendWelcomeEmail(anyString());
    }
}
```

---

# **4) Stubbing (programar el mock)**

## **4.1 when(...).thenReturn(...)**

### **Qué hace**

Le dices al mock:

> “Cuando te llamen con X, devuelve Y”.
>

### **Cuándo usarlo**

- Para controlar el flujo del SUT

### **Errores típicos**

- Stubear algo que nunca se usa (test mentiroso)

```java
when(repo.existsByEmail("x@y.com")).thenReturn(true);
```

---

## **4.2 Respuestas múltiples ( thenReturn en cadena)**

### **Qué hace**

Cada llamada devuelve un valor distinto.

```java
when(repo.existsByEmail("x@y.com"))
    .thenReturn(false)
    .thenReturn(true);
```

**Uso típico:** simular “primera vez no existe, segunda ya existe”.

---

## **4.3 thenThrow(...)**

### **Qué hace**

Simula que la dependencia falla.

```java
when(repo.existsByEmail("x@y.com"))
    .thenThrow(new RuntimeException("DB down"));
```

**Cuándo usarlo:** probar comportamiento ante fallos externos.

---

## **4.4 doThrow / doNothing para void**

### **Qué hace**

Para métodos void no puedes usar when(...).thenReturn.

```java
doThrow(new RuntimeException("SMTP down"))
    .when(emailSender).sendWelcomeEmail("a@b.com");
```

**Error típico:** intentar when(emailSender.sendWelcomeEmail(...)) (no compila / no sirve).

---

# **5) Verificación (lo más importante)**

## **5.1 verify(mock).method(...)**

### **Qué hace**

Comprueba que se llamó a un método.

```java
verify(emailSender).sendWelcomeEmail("a@b.com");
```

---

## **5.2 times(n), atLeastOnce(), atMost(n)**

### **Qué hace**

Verifica cuántas veces se llamó.

```java
verify(emailSender, times(1)).sendWelcomeEmail(anyString());
verify(repo, atLeastOnce()).existsByEmail(anyString());
verify(repo, atMost(2)).existsByEmail(anyString());
```

**Error típico:** usar times(0) en vez de never() (mejor never()).

---

## **5.3 never()**

### **Qué hace**

Verifica que NO se llamó.

```java
verify(repo, never()).save(any());
```

---

## **5.4 InOrder (orden de llamadas)**

### **Qué hace**

Comprueba que se llamaron en un orden concreto.

```java
import org.mockito.InOrder;

InOrder inOrder = inOrder(repo, emailSender);
inOrder.verify(repo).save(any(User.class));
inOrder.verify(emailSender).sendWelcomeEmail("a@b.com");
```

**Cuándo usarlo:** cuando el orden importa por lógica de negocio.

---

## **5.5 verifyNoMoreInteractions(...)**

### **Qué hace**

Asegura que no hubo más llamadas.

```java
verifyNoMoreInteractions(repo, emailSender);
```

⚠️ **Ojo**: puede volver tests frágiles si se usa en exceso.

---

# **6) Matchers (any, eq, argThat…)**

## **6.1 any(), anyString(), eq()**

### **Qué hacen**

Permiten verificar o stubear sin especificar el valor exacto.

✅ Bien:

```java
when(repo.existsByEmail(anyString())).thenReturn(false);
verify(emailSender).sendWelcomeEmail(eq("a@b.com"));
```

❌ Mal (mezclar matchers y literales en el mismo método con varios args):

```java
// Ejemplo típico si hubiera 2 parámetros:
verify(emailSender).send("a@b.com", anyString()); // mal
```

---

## **6.2 argThat(...)**

### **Qué hace**

Matcher personalizado con condición.

```java
verify(repo).save(argThat(u -> u.email().endsWith("@b.com")));
```

**Cuándo usar:** cuando el objeto es complejo y quieres comprobar una propiedad.

---

# **7) ArgumentCaptor (capturar argumentos)**

### **Qué hace**

Captura el argumento real que se envió a un mock.

### **Cuándo usar**

- El SUT crea el objeto internamente (no puedes compararlo exacto)

### **Ejemplo**

```java
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

@Captor ArgumentCaptor<User> userCaptor;

@Test
void register_capturesSavedUser() {
    when(repo.existsByEmail(anyString())).thenReturn(false);

    service.register("a@b.com");

    verify(repo).save(userCaptor.capture());
    User saved = userCaptor.getValue();

    assertEquals("a@b.com", saved.email());
}
```

---

# **8) Spy (y su peligro)**

### **Qué hace**

Un spy es un objeto real al que puedes “interceptar” algunos métodos.

```java
import java.util.ArrayList;
import java.util.List;

@Test
void spyExample() {
    List<String> spyList = spy(new ArrayList<>());

    spyList.add("hola");

    verify(spyList).add("hola");
}
```

### **Peligro típico**

when(spy.method()) ejecuta el método real.

✅ Mejor:

```java
doReturn(10).when(spyList).size();
assertEquals(10, spyList.size());
```

---

# **9) Buen diseño para testear (lo que hace Mockito fácil)**

### **Regla de oro**

> Inyección por constructor + dependencias como interfaces = tests sencillos.
>

❌ Mal (difícil de testear):

```java
class PaymentService {
    void pay() {
        PaymentGateway gw = new PaymentGateway();
        gw.charge();
    }
}
```

✅ Bien:

```java
class PaymentService {
    private final PaymentGateway gw;
    PaymentService(PaymentGateway gw) { this.gw = gw; }

    void pay() { gw.charge(); }
}

interface PaymentGateway { void charge(); }
```

---

# **10) Estilo BDDMockito (opcional)**

### **Qué hace**

Es la misma idea de Mockito, pero con nombres “Given/When/Then”.

```java
import static org.mockito.BDDMockito.*;

given(repo.existsByEmail("a@b.com")).willReturn(false);

service.register("a@b.com");

then(repo).should().save(any(User.class));
then(emailSender).should().sendWelcomeEmail("a@b.com");
```

**Cuándo usar:** si quieres tests más “narrativos”.

---

# **11) Mockear métodos estáticos (BONUS)**

### **Cuándo usarlo**

- Último recurso (normalmente indica diseño mejorable)

### **Dependencia**

Necesitas mockito-inline (no mockito-core).

### **Ejemplo**

```java
import org.mockito.MockedStatic;

class Utils {
  static String normalize(String s) { return s.trim().toLowerCase(); }
}

@Test
void staticMock_example() {
    try (MockedStatic<Utils> mocked = mockStatic(Utils.class)) {
        mocked.when(() -> Utils.normalize("  A  ")).thenReturn("x");

        assertEquals("x", Utils.normalize("  A  "));

        mocked.verify(() -> Utils.normalize("  A  "));
    }
}
```