package org.example.authservice.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRespsoneDTO {
    @Column(nullable = false)
    private String token;
}
