package org.demo.pc1_demo.student.domain;

import org.demo.pc1_demo.auth.utils.AuthorizationUtils;
import org.demo.pc1_demo.course.domain.Course;
import org.demo.pc1_demo.course.infrastructure.CourseRepository;
import org.demo.pc1_demo.exceptions.UnauthorizeOperationException;
import org.demo.pc1_demo.student.infrastructure.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    
    @Autowired
    CourseRepository courseRepository;
    
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    AuthorizationUtils authorizationUtils;

    public List<Course> ListarCursosInscrito(Long id){
        if (!authorizationUtils.isAdminOrResourceOwner(id))
            throw new UnauthorizeOperationException("User has no permission to modify this resource");

        Optional<Student> student = studentRepository.findById(id);
        return student.map(value -> courseRepository.findCourseByStudentsContaining(value)).orElse(null);
    }

    public Student ActualizarEstudiante(Long id, Student student){
        String role = authorizationUtils.getCurrentUserRole();

        if(!role.equals("ROLE_TEACHER"))
            throw new UnauthorizeOperationException("User has no permission to modify this resource");

        Optional<Student> studentOptional = studentRepository.findById(id);
        if(studentOptional.isEmpty()) throw new IllegalArgumentException("Student not found");

        Student studentToUpdate = studentOptional.get();
        studentToUpdate.setName(student.getName());

        return studentRepository.save(studentToUpdate);
    }

    public void EliminarEstudiante(Long id){
        String role = authorizationUtils.getCurrentUserRole();

        if(!role.equals("ROLE_TEACHER"))
            throw new UnauthorizeOperationException("User has no permission to modify this resource");

        Optional<Student> student = studentRepository.findById(id);
        if(student.isEmpty()) throw new IllegalArgumentException("Student not found");

        studentRepository.delete(student.get());
    }

}
