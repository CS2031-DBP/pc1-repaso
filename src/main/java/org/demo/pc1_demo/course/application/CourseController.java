package org.demo.pc1_demo.course.application;

import org.demo.pc1_demo.course.domain.Course;
import org.demo.pc1_demo.course.domain.CourseService;
import org.demo.pc1_demo.student.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    CourseService courseService;

    @GetMapping()
    public ResponseEntity<List<Course>> ListarCursosConVacantes() {
        return ResponseEntity.ok(courseService.ListarCursosConVacantes());
    }

    @PatchMapping("/{courseId}/student/{studentId}")
    public ResponseEntity<Void> InscribirAlumno(@PathVariable Long courseId, @PathVariable Long studentId) {
        courseService.InscribirAlumno(courseId, studentId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{courseId}/student/{studentId}")
    public ResponseEntity<Void> DesinscribirAlumno(@PathVariable Long courseId, @PathVariable Long studentId) {
        courseService.DesinscribirAlumno(courseId, studentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<List<Student>> ListarAlumnosInscritos(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.ListarAlumnosInscritos(id));
    }

    @PostMapping()
    public ResponseEntity<Course> CrearCurso(@RequestBody Course course) {
        return ResponseEntity.ok(courseService.CrearCurso(course));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Course> ActualizarCurso(@PathVariable Long id, @RequestBody Course course) {
        return ResponseEntity.ok(courseService.ActualizarCurso(id, course));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> EliminarCurso(@PathVariable Long id) {
        courseService.EliminarCurso(id);
        return ResponseEntity.noContent().build();
    }

}
