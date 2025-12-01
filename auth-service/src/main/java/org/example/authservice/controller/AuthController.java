package org.example.authservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.authservice.dto.LoginRequestDTO;
import org.example.authservice.dto.LoginRespsoneDTO;
import org.example.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
public class AuthController {
    @Autowired
    private AuthService authService;


    @Operation(summary = "Generate Token on user login")
    @PostMapping("/login")
    public ResponseEntity<LoginRespsoneDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        Optional<String> tokenOptional = authService.authenticate(loginRequestDTO);
        if (tokenOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = tokenOptional.get();
        LoginRespsoneDTO loginRespsoneDTO = new LoginRespsoneDTO();
        loginRespsoneDTO.setToken(token);
        return ResponseEntity.ok(loginRespsoneDTO);

    }

    @Operation(summary = "validating jwt auth Token")
    @GetMapping("/validate")
    public ResponseEntity<Void> validateJwtAuthToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return authService.validateToken(authHeader.substring(7))
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();


    }
}
