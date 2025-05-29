package com.XCLONE.Budget_tracker.Controllers;

import com.XCLONE.Budget_tracker.Config.JwtUtil;
import com.XCLONE.Budget_tracker.Entity.Users;
import com.XCLONE.Budget_tracker.Repository.UsersRepository;
import lombok.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired private AuthenticationManager authManager;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private UsersRepository repo;
    @Autowired private PasswordEncoder encoder;

    @PostMapping("/signup")
    public String signup(@RequestBody Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
        return "User registered";
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        return jwtUtil.generateToken(request.getEmail());
    }
}

@Data
class AuthRequest {
    private String email;
    private String password;
}
