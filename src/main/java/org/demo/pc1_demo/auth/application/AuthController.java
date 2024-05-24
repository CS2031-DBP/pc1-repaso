package org.demo.pc1_demo.auth.application;

import org.demo.pc1_demo.auth.domain.AuthService;
import org.demo.pc1_demo.auth.dto.JwtAuthResponse;
import org.demo.pc1_demo.auth.dto.LoginReq;
import org.demo.pc1_demo.auth.dto.RegisterReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/hello")
    public String hello() {
        return "La nube funciona!";
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginReq req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @PostMapping("/register")
    public ResponseEntity<JwtAuthResponse> register(@RequestBody RegisterReq req) {
        return ResponseEntity.ok(authService.register(req));
    }
}
