# pc1-repaso
Un proyecto de ayuda para repasar para la pc1

## Objetivos

### Entidades y relaciones
* Implementa de manera correcta todas las entidades necesarias.
* Todos los atributos requeridos deben estar presentes.

### Capa de persistencia
* Define correctamente la interfaz de repositorio para cada entidad.
* Define correctamente las configuraciones de conexión a base de datos en el archivo application.properties.
* Usa por lo menos 4 query methods distribuidos entre las 3 interfaces de repositorios

### Implementación de end-points
* Implementar correctamente 13 endpoints solicitados sin ninguna falla de compilación.

### Manejo de excepciones
* Implementar 3 clases propias de excepción y las añadirlas correctamente al controlador global de excepciones.

### Spring security
* Implementa de manera correcta el flujo de inicio de sesión (login), registro (register) y redirección para usuarios no autorizados.
* Los usuarios no autorizados son redirigidos correctamente, con respuestas de error apropiadas como HTTP 401 o 403.

### Testing
* Usar Testcontainers para probar un repositorio que contiene funciones especiales.
* Realiza pruebas para al menos dos endpoints de la aplicación

## Problemática

Tenemos un sistema de cursos en la universidad, cada curso cuenta con un profesor y varios estudiantes. Cada estudiante puede inscribirse en varios cursos y cada curso puede tener varios estudiantes inscritos. Cada curso tiene un nombre, una descripción y un código. Cada profesor tiene un nombre, un correo y una contraseña. Cada estudiante tiene un nombre, un correo y una contraseña.
Los alumnos pueden inscribirse en los cursos, pero solo pueden inscribirse si el curso tiene cupos disponibles. Los cursos tienen un cupo máximo de 10 estudiantes.

Los alumnos pueden realizar las siguientes acciones:
* Listar los cursos en los que está inscrito. (ListarCursosInscrito / StudentService)
* Listar los cursos con cupos disponibles. (ListarCursosConVacantes / CourseService) 
* Inscribirse en un curso. (InscribirAlumno / CourseService)
* Desinscribirse de un curso. (DesinscribirAlumno / CourseService)

Los profesores pueden realizar las siguientes acciones:
* Listar los cursos que dicta. (ListarCursosDictados / TeacherService)
* Listar los estudiantes inscritos en un curso. (ListarEstudiantesInscritos / CourseService)
* Crear un curso. (CrearCurso / CourseService)
* Eliminar un curso. (EliminarCurso / CourseService)
* Actualizar un curso. (ActualizarCurso / CourseService)
* Actualizar la información de un estudiante. (ActualizarEstudiante / StudentService)
* Eliminar un estudiante. (EliminarEstudiante / StudentService)
* Agregar un estudiante a un curso. (InscribirAlumno / CourseService)
* Eliminar un estudiante de un curso. (DesinscribirAlumno / CourseService)
