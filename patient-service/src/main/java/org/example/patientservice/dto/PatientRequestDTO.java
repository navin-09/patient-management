package org.example.patientservice.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class PatientRequestDTO {
    @NotNull(message = "name is required")
    @Size(min = 1, max = 255,message = "name should be minimum 1 and max 255 characters")
    private String name;

    @NotNull(message = "email is required")
    @Email
    private String email;

    @NotNull(message = "Address is required")
    private String address;

    @NotNull(message = "date of birth is required")
    private String dateOfBirth;

    @NotNull(message = "registered date is required")
    private String registeredDate;
}
