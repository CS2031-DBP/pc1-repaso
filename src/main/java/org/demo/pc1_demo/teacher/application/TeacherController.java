package org.demo.pc1_demo.teacher.application;

import org.demo.pc1_demo.course.domain.Course;
import org.demo.pc1_demo.student.domain.StudentService;
import org.demo.pc1_demo.teacher.domain.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @GetMapping("/{id}")
    public ResponseEntity<List<Course>>  ListarCursosDictados(@PathVariable Long id) {
        return ResponseEntity.ok(teacherService.ListarCursosDictados(id));
    }


}
