package org.demo.pc1_demo.course.domain;

import org.demo.pc1_demo.auth.utils.AuthorizationUtils;
import org.demo.pc1_demo.course.infrastructure.CourseRepository;
import org.demo.pc1_demo.student.domain.Student;
import org.demo.pc1_demo.student.infrastructure.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.demo.pc1_demo.exceptions.UnauthorizeOperationException;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    AuthorizationUtils authorizationUtils;

    public List<Course> ListarCursosConVacantes(){
        String username = authorizationUtils.getCurrentUserEmail();
        if(username == null) throw new UnauthorizeOperationException("Anonymous User not allowed to access this resource");

        return courseRepository.findCoursesByRemainingSpotsIsGreaterThan(0);
    }

    public void InscribirAlumno(Long id, Long studentId){
        Course course = CheckRole(id, studentId);

        if(course.getRemainingSpots() == 0) throw new IllegalArgumentException("No spots available");

        Optional<Student> student = studentRepository.findById(studentId);
        if(student.isEmpty()) throw new IllegalArgumentException("Student not found");
        course.addStudent(student.get());

        courseRepository.save(course);
    }

    public void DesinscribirAlumno(Long id, Long studentId){
        Course course = CheckRole(id, studentId);
        Optional<Student> student = studentRepository.findById(studentId);
        if(student.isEmpty()) throw new IllegalArgumentException("Student not found");
        course.removeStudent(student.get());

        courseRepository.save(course);
    }

    private Course CheckRole(Long id, Long studentId) {
        String role = authorizationUtils.getCurrentUserRole();
        if(role.equals("ROLE_STUDENT")){
            if (!authorizationUtils.isAdminOrResourceOwner(studentId))
                throw new UnauthorizeOperationException("User has no permission to modify this resource");
        }

        String username = authorizationUtils.getCurrentUserEmail();
        if(username == null) throw new UnauthorizeOperationException("Anonymous User not allowed to access this resource");

        return courseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Course not found"));
    }

    public List<Student> ListarAlumnosInscritos(Long id){
        String role = authorizationUtils.getCurrentUserRole();
        if(role.equals("ROLE_TEACHER")){
            Course course = courseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Course not found"));
            return course.getStudents();
        }
        throw new UnauthorizeOperationException("User has no permission to modify this resource");
    }

    public Course CrearCurso(Course course){
        String role = authorizationUtils.getCurrentUserRole();
        if(role.equals("ROLE_TEACHER")){
            return courseRepository.save(course);
        }
        throw new UnauthorizeOperationException("User has no permission to modify this resource");
    }

    public Course ActualizarCurso(Long id, Course course){
        String role = authorizationUtils.getCurrentUserRole();
        if(role.equals("ROLE_TEACHER")){
            Course courseToUpdate = courseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Course not found"));
            courseToUpdate.setTitle(course.getTitle());
            return courseRepository.save(courseToUpdate);
        }
        throw new UnauthorizeOperationException("User has no permission to modify this resource");
    }

    public void EliminarCurso(Long id){
        String role = authorizationUtils.getCurrentUserRole();
        if(role.equals("ROLE_TEACHER")){
            Course course = courseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Course not found"));
            courseRepository.delete(course);
            return;
        }
        throw new UnauthorizeOperationException("User has no permission to modify this resource");
    }



}
