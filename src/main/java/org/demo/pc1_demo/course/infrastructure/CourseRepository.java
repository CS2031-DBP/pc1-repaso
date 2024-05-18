package org.demo.pc1_demo.course.infrastructure;

import org.demo.pc1_demo.course.domain.Course;
import org.demo.pc1_demo.student.domain.Student;
import org.demo.pc1_demo.teacher.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findCourseByStudentsContaining(Student student);
    List<Course> findCourseByTeacherEquals(Teacher teacher);
    List<Course> findCoursesByRemainingSpotsIsGreaterThan(int spots);
}
