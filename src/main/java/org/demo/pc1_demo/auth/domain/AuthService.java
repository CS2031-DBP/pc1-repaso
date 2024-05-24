package org.demo.pc1_demo.auth.domain;

import org.demo.pc1_demo.auth.dto.JwtAuthResponse;
import org.demo.pc1_demo.auth.dto.LoginReq;
import org.demo.pc1_demo.auth.dto.RegisterReq;
import org.demo.pc1_demo.auth.exceptions.UserAlreadyExistException;
import org.demo.pc1_demo.config.JwtService;
import org.demo.pc1_demo.student.domain.Student;
import org.demo.pc1_demo.teacher.domain.Teacher;
import org.demo.pc1_demo.user.domain.User;
import org.demo.pc1_demo.user.infrastructure.BaseUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.demo.pc1_demo.user.domain.Role;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class AuthService {

    private final BaseUserRepository<User> userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(BaseUserRepository<User> userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public JwtAuthResponse login(LoginReq req) {
        Optional<User> user;
        user = userRepository.findByEmail(req.getEmail());

        if (user.isEmpty()) throw new UsernameNotFoundException("Email is not registered");

        if (!passwordEncoder.matches(req.getPassword(), user.get().getPassword()))
            throw new IllegalArgumentException("Password is incorrect");

        JwtAuthResponse response = new JwtAuthResponse();

        response.setToken(jwtService.generateToken(user.get()));
        return response;
    }

    public JwtAuthResponse register(RegisterReq req){
        Optional<User> user = userRepository.findByEmail(req.getEmail());
        if (user.isPresent()) throw new UserAlreadyExistException("Email is already registered");

        if (req.getIsTeacher()) {
            Teacher teacher = new Teacher();
            teacher.setRole(Role.TEACHER);
            teacher.setName(req.getName());
            teacher.setEmail(req.getEmail());
            teacher.setPassword(passwordEncoder.encode(req.getPassword()));

            userRepository.save(teacher);

            JwtAuthResponse response = new JwtAuthResponse();
            response.setToken(jwtService.generateToken(teacher));
            return response;
        }
        else {
            Student student = new Student();
            student.setRole(Role.STUDENT);
            student.setName(req.getName());
            student.setEmail(req.getEmail());
            student.setPassword(passwordEncoder.encode(req.getPassword()));

            userRepository.save(student);

            JwtAuthResponse response = new JwtAuthResponse();
            response.setToken(jwtService.generateToken(student));
            return response;
        }

    }
}
