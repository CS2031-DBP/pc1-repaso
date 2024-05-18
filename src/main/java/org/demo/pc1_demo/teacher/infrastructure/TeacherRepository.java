package org.demo.pc1_demo.teacher.infrastructure;

import jakarta.transaction.Transactional;
import org.demo.pc1_demo.teacher.domain.Teacher;
import org.demo.pc1_demo.user.infrastructure.BaseUserRepository;
import org.springframework.stereotype.Repository;


@Transactional
@Repository
public interface TeacherRepository extends BaseUserRepository<Teacher> {
}