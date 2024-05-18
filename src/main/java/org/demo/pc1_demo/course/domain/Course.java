package org.demo.pc1_demo.course.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import org.demo.pc1_demo.student.domain.Student;
import org.demo.pc1_demo.teacher.domain.Teacher;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;

    private Integer remainingSpots = 10;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "courses")
    private List<Student> students =  new ArrayList<>();

    @ManyToOne
    private Teacher teacher;

    public void addStudent(Student student) {
        this.students.add(student);
        student.getCourses().add(this);
        this.remainingSpots--;
    }

    public void removeStudent(Student student) {
        this.students.remove(student);
        student.getCourses().remove(this);
        this.remainingSpots++;
    }

}
