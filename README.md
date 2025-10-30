# UDEA_TP_Envios

Proyecto Operador Logístico (Swing + JTable) con separación por carpetas: `entidades`, `servicios`, `datos`, `iconos`.

## Compilar/Ejecutar
```bash
javac -d bin $(find src -name "*.java")
java -cp bin App
```
---

## Documentación de diseño (POO y buenas prácticas)

El sistema se diseñó aplicando principios de Programación Orientada a Objetos (POO) y código limpio. A continuación se describen los aspectos principales que se implementaron en el proyecto:

### Principios SOLID

- S — Single Responsibility (Responsabilidad única): cada clase tiene una única responsabilidad. Ejemplos:
	- `entidades.Envio` — modelo de dominio (datos y acceso a sus campos).
	- `servicios.EnvioService` — lógica de negocio (validaciones y creación de envíos).
	- `datos.EnMemoriaEnvioRepository` — gestión de persistencia en memoria.

- O — Open/Closed (Abierto/Cerrado): el sistema es extensible para nuevas reglas sin modificar código existente.
	- `servicios.CalculadoraTarifa` es una interfaz; nuevas políticas tarifarias se agregan implementando esa interfaz (p. ej. `CalculadoraTarifaBase` o futuras clases).

- L — Liskov Substitution: las implementaciones de las interfaces pueden sustituirse sin alterar el comportamiento esperado. Por ejemplo, distintas implementaciones de `CalculadoraTarifa` pueden usarse por `EnvioService` sin cambiarlo.

- I — Interface Segregation: las interfaces son pequeñas y específicas (`EnvioRepository`, `CalculadoraTarifa`) para evitar cargar implementaciones con métodos que no necesiten.

- D — Dependency Inversion: los módulos de alto nivel (`EnvioService`) dependen de abstracciones (`EnvioRepository`, `CalculadoraTarifa`) y no de implementaciones concretas. La inyección se realiza en el punto de composición (`FrmEnvios`), lo que permite sustituir `EnMemoriaEnvioRepository` por otra implementación (p. ej. JDBC) sin cambiar la lógica.

### Técnicas de programación y buenas prácticas

- Aplicar nombres descriptivos y convenciones Java (PascalCase para clases, camelCase para métodos/variables).
- Evitar duplicación (DRY): validar la unicidad del código tanto en el servicio como en el repositorio (defensa en profundidad).
- Encapsulamiento: atributos `private` y acceso controlado mediante getters/setters en `Envio`.
- Colecciones genéricas: uso de `List<Envio>` en repositorios y servicios.
- Polimorfismo: cálculo de tarifa por medio de la interfaz `CalculadoraTarifa`.
- Separación de lógica de negocio e interfaz de usuario: `EnvioService` maneja validaciones y creación; `FrmEnvios` sólo interactúa con el usuario y delega en el servicio.

### Validaciones de ejemplo implementadas

- Campos obligatorios y formato: validación de `cliente`, `peso` y `distancia` en `EnvioService` (método `parsePositivo`).
- Unicidad de código: el usuario puede ingresar un `codigo` opcional; si queda vacío se genera uno. Antes de persistir, `EnvioService` verifica `repo.existsByCodigo(...)` y lanza `IllegalArgumentException` si ya existe. El repositorio en memoria (`EnMemoriaEnvioRepository.add`) realiza una comprobación similar.

### Extensibilidad

- Para agregar una nueva política tarifaria: implementar `servicios.CalculadoraTarifa` y pasar la nueva implementación a `EnvioService`.
- Para cambiar persistencia: crear una nueva implementación de `datos.EnvioRepository` y usarla al instanciar `EnvioService`.

---

*Iconos tomados de Flaticon.com*