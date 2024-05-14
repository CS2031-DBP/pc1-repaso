# pc1-repaso
Un proyecto de ayuda para repasar para la pc1

## Objetivos

### Entidades y relaciones
* Implementa de manera correcta todas las entidades necesarias.
* Todos los atributos requeridos deben estar presentes.

### Capa de persistencia
* Define correctamente la interfaz de repositorio para cada entidad.
* Define correctamente las configuraciones de conexión a base de datos en el archivo application.properties.
* Define y usa por lo menos 4 query methods distribuidos entre las 4 interfaces de repositorios

### Implementación de end-points
* Implementar correctamente 8 endpoints solicitados sin ninguna falla de compilación.

### Manejo de excepciones
* Implementar 3 clases propias de excepción y las añadirlas correctamente al controlador global de excepciones.

### Spring security
* Implementa de manera correcta el flujo de inicio de sesión (login), registro (register) y redirección para usuarios no autorizados.
* Los usuarios no autorizados son redirigidos correctamente, con respuestas de error apropiadas como HTTP 401 o 403.

### Testing
* Usar Testcontainers para probar un repositorio que contiene funciones especiales.
* Realiza pruebas para al menos dos endpoints de la aplicación
