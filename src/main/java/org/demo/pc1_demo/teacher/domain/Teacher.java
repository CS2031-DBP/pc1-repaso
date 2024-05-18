package org.demo.pc1_demo.teacher.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import org.demo.pc1_demo.course.domain.Course;
import org.demo.pc1_demo.user.domain.User;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Teacher extends User {

    @OneToMany(mappedBy = "teacher")
    List<Course> courses = new ArrayList<>();
}
