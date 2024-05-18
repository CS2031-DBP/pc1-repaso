package org.demo.pc1_demo.student.application;

import org.demo.pc1_demo.course.domain.Course;
import org.demo.pc1_demo.student.domain.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping("/{id}")
    public ResponseEntity<List<Course>>  ListarCursosInscrito(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.ListarCursosInscrito(id));
    }
}
