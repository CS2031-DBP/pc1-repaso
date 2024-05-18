package org.demo.pc1_demo.teacher.domain;

import org.demo.pc1_demo.auth.utils.AuthorizationUtils;
import org.demo.pc1_demo.course.domain.Course;
import org.demo.pc1_demo.course.infrastructure.CourseRepository;
import org.demo.pc1_demo.exceptions.UnauthorizeOperationException;
import org.demo.pc1_demo.student.domain.Student;
import org.demo.pc1_demo.teacher.infrastructure.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    @Autowired
    AuthorizationUtils authorizationUtils;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    CourseRepository courseRepository;

    public List<Course> ListarCursosDictados(Long id){
        if (!authorizationUtils.isAdminOrResourceOwner(id))
            throw new UnauthorizeOperationException("User has no permission to modify this resource");

        Optional<Teacher> teacher = teacherRepository.findById(id);
        return teacher.map(value -> courseRepository.findCourseByTeacherEquals(value)).orElse(null);
    }
}
