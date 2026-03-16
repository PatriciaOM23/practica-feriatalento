## **Ejercicio — “Estadísticas con validación (double[])”**

**Objetivo:** Scanner + validación de entrada + double[] + calcular **máximo, mínimo y media**.

Crea una clase `Recuperacion.java` en paquete  `es.fplumara.dam1.recuperacion`.

### **Requisitos**

1. Pide al usuario el tamaño **N** del array.
    - Debe ser **un número entero** y cumplir: **1 ≤ N ≤ 9** (mayor que 0 y menor que 10).
    - Si no introduce un entero válido o está fuera de rango, **vuelve a pedir N**.
2. Crea un array double[] nums = new double[N].
3. Rellena el array:
    - Para cada posición, pide un número (puede tener decimales).
    - Si el usuario mete algo que **no es número**, vuelve a pedir **esa misma posición**.
4. Una vez relleno, muestra:
    - El **mayor**
    - El **menor**
    - La **media** (double)
    - El **array completo** en una línea.
    - El número total de **números que son mayores que la media**.

## **Rúbrica — “Estadísticas con validación (double[])” (10 puntos)**

### **1) Validación del tamaño N (entero y 1 ≤ N ≤ 9) — 2,0 pts**

- **2,0**: Repite hasta que sea **entero válido** y en rango; si entra texto u otro tipo, no rompe y vuelve a pedir.
- **1,0**: Valida rango pero falla con no-números (o al revés).
- **0,5**: Validación incompleta (p. ej. solo >0).
- **0,0**: No valida N.

---

### **2) Creación del array double[] nums = new double[N] — 1,0 pt**

- **1,0**: Array creado con el tamaño introducido.
- **0,5**: Lo crea pero con error menor (p. ej. tamaño mal asignado y corregido).
- **0,0**: No crea el array o tamaño incorrecto.

---

### **3) Relleno del array con double y validación — 3,0 pts**

- **3,0**: Pide N valores; si el usuario mete algo no numérico, **repite la misma posición** y limpia la entrada inválida.
- **2,0**: Valida, pero a veces avanza de posición cuando no debe o maneja mal algún caso.
- **1,0**: Rellena sin validación robusta (crashea con texto o acepta entradas inválidas).
- **0,0**: No rellena correctamente.

---

### **4) Cálculo de máximo y mínimo — 1,5 pts**

- **1,5**: Max y min correctos; inicializa de forma robusta (por ejemplo con nums[0]).
- **1,0**: Uno correcto y otro con fallo, o inicialización poco robusta (p.ej. 0) que falla con ciertos datos.
- **0,0**: Incorrecto o no lo calcula.

---

### **5) Cálculo de suma y media (double) — 1,5 pts**

- **1,5**: Suma correcta y media = suma / N correcta en double.
- **0,5**: Suma bien pero media con error (p. ej. división entera en algún enfoque).
- **0,0**: Incorrecto.

---

### **6) Mostrar el array completo en una línea — 0,5 pts**

- **0,5**: Muestra todos los elementos del array en orden y de forma legible.
- **0,0**: No lo muestra o está incompleto.

---

### **7) Contar cuántos son mayores que la media — 1,5 pts**

- **1,5**: Cuenta correctamente los valores **estrictamente mayores** que la media (> media) y lo muestra.
- **1,0**: Cuenta pero con criterio ligeramente incorrecto (>= en vez de > o similar) o pequeño error de lógica.
- **0,5**: Intenta pero el contador está mal planteado.
- **0,0**: No lo hace.

---

### **8) Salida final clara y completa — 0,5 pts**

- **0,5**: Muestra **mayor, menor, media, array y contador** con etiquetas claras.
- **0,0**: Salida confusa o falta algún dato.

---

## **Penalizaciones (sobre el total)**

- **–0,5**: No consume la entrada inválida (no hace next() o equivalente) y el programa entra en bucle infinito o se queda bloqueado con texto.
- **–0,25**: Falta de orden/claridad en mensajes (sin etiquetas), aunque los cálculos estén bien.