package org.example.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {
    @NotBlank(message = "Email is required")
    @Email(message = "Please provide valid Email")
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 8, message = "Passord must be atleast 8 chars")
    private String password;


}
