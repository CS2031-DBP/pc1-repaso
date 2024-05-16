package org.demo.pc1_demo.course.infrastructure;

import org.demo.pc1_demo.course.domain.Course;
import org.demo.pc1_demo.student.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByName(String name);
    List<Course> findByDescription(String desc);
    List<Course> findByNameAndCode(String name, String code);
    List<Course> findByIdContaining(Student student);
}
