package com.example.demo.business;

import com.example.demo.auth.AuthenticationRequest;
import com.example.demo.auth.AuthenticationResponse;
import com.example.demo.auth.RegisterRequest;
import com.example.demo.business.observer.Subject;
import com.example.demo.configuration.JwtService;
import com.example.demo.model.Role;
import com.example.demo.model.Student;
import com.example.demo.model.Admin;
import com.example.demo.persistance.StudentRepository;
import com.example.demo.persistance.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Component
public class AuthentificationService extends Subject {

    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService service;
    private final AuthenticationManager manager;

    //for admin - register and login
    public AuthenticationResponse registerAdmin(RegisterRequest request) {
        var admin = Admin.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .build();
        adminRepository.save(admin);
        var token = service.generateToken(admin);
        return AuthenticationResponse.builder().token(token).build();
    }

    public AuthenticationResponse loginAdmin(AuthenticationRequest request) {
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = adminRepository.findByUsername(request.getUsername()).orElseThrow();
        var token = service.generateToken(user);
        return AuthenticationResponse.builder().token(token).build();
    }

    //for student - register and login
    public AuthenticationResponse registerStudent(RegisterRequest request) {
        var student = Student.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.STUDENT)
                .build();
        try {
            notifyObservers(1, student.getUsername());
        } catch (IOException e) {
            System.out.println("Error");
        }
        studentRepository.save(student);
        var token = service.generateToken(student);
        return AuthenticationResponse.builder().token(token).build();
    }

    public AuthenticationResponse loginStudent(AuthenticationRequest request) {
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = studentRepository.findByUsername(request.getUsername()).orElseThrow();
        var token = service.generateToken(user);
        return AuthenticationResponse.builder().token(token).build();
    }
}
