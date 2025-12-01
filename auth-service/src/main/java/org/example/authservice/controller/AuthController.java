package org.example.authservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.authservice.dto.LoginRequestDTO;
import org.example.authservice.dto.LoginRespsoneDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
public class AuthController {
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
}
