package org.demo.pc1_demo.course.infrastructure;

import org.demo.pc1_demo.course.domain.Course;
import org.demo.pc1_demo.student.domain.Student;
import org.demo.pc1_demo.teacher.domain.Teacher;
import org.demo.pc1_demo.user.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Student student;
    private Teacher teacher;

    @BeforeEach
    public void setUp() {
        createStudentandTeacher();
        setupAndPersistCourses();
    }

    private void createStudentandTeacher() {
        student = createStudent();
        teacher = createTeacher();
    }

    private Student createStudent() {
        Student student = new Student();
        student.setName("Jane");
        student.setPassword("password");
        student.setEmail("jane@gmail.com");
        student.setRole(Role.STUDENT);

        return entityManager.persist(student);
    }

    private Teacher createTeacher() {
        Teacher teacher = new Teacher();
        teacher.setName("John");
        teacher.setPassword("password");
        teacher.setEmail("john@gmail.com");
        teacher.setRole(Role.TEACHER);

        return entityManager.persist(teacher);
    }

    private void setupAndPersistCourses() {
        Course course1 = new Course();
        course1.setTitle("Math");
        course1.setTeacher(teacher);
        course1.addStudent(student);
        entityManager.persist(course1);

        Course course2 = new Course();
        course2.setTitle("Physics");
        course2.setTeacher(teacher);
        entityManager.persist(course2);
    }

    // Tests
    // List<Course> findCourseByStudentsContaining(Student student);

    @Test
    public void findCourseByStudentsContainingTest() {
        List<Course> courses = courseRepository.findCourseByStudentsContaining(student);
        assertEquals(1, courses.size());
        assertEquals("Math", courses.get(0).getTitle());
    }

    // List<Course> findCourseByTeacherEquals(Teacher teacher);

    @Test
    public void findCourseByTeacherEqualsTest() {
        List<Course> courses = courseRepository.findCourseByTeacherEquals(teacher);
        assertEquals(2, courses.size());
    }

    // List<Course> findCoursesByRemainingSpotsIsGreaterThan(int spots);

    @Test
    public void findCoursesByRemainingSpotsIsGreaterThanTest() {
        List<Course> courses = courseRepository.findCoursesByRemainingSpotsIsGreaterThan(0);
        assertEquals(2, courses.size());
        assertEquals("Math", courses.get(0).getTitle());
    }

}
