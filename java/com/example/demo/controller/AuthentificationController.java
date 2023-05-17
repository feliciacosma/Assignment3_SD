package com.example.demo.controller;

import com.example.demo.auth.AuthenticationRequest;
import com.example.demo.auth.AuthenticationResponse;
import com.example.demo.auth.RegisterRequest;
import com.example.demo.business.AuthentificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@EnableMethodSecurity
public class AuthentificationController {

    private final AuthentificationService service;

    //register admin
    @PostMapping("/api/v1/auth/registerAdmin")
    public ResponseEntity<AuthenticationResponse> registerA(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.registerAdmin(request));
    }

    //register student
    @PostMapping("/api/v1/auth/registerStudent")
    public ResponseEntity<AuthenticationResponse> registerS(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.registerStudent(request));
    }

    //login admin
    @PostMapping("/loginAdmin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AuthenticationResponse> loginA(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.loginAdmin(request));
    }

    //login student
    @PostMapping("/loginStudent")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<AuthenticationResponse> loginS(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.loginStudent(request));
    }

}