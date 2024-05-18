package org.demo.pc1_demo.student.infrastructure;

import org.demo.pc1_demo.course.domain.Course;
import org.demo.pc1_demo.student.domain.Student;
import org.demo.pc1_demo.teacher.domain.Teacher;
import org.demo.pc1_demo.user.infrastructure.BaseUserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends BaseUserRepository<Student> {
    List<Student> findByCoursesContaining(Course course);
}
