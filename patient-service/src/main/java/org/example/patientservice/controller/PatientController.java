package org.example.patientservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.example.patientservice.dto.PatientRequestDTO;
import org.example.patientservice.dto.PatientResponseDTO;
import org.example.patientservice.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient", description = "documentation for patient api")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @GetMapping
    @Operation(summary = "Get Patients")
    private ResponseEntity<List<PatientResponseDTO>> getPatients() {
        List<PatientResponseDTO> patients = patientService.getPatients();
        return ResponseEntity.ok().body(patients);
    }

    @PostMapping
    @Operation(summary = "Create a Patient")
    private ResponseEntity<PatientResponseDTO> savePatient(@Valid  @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patientResponseDTO = patientService.addPatient(patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Update a Patient")
    private ResponseEntity<PatientResponseDTO> updatePatient(@Valid @PathVariable UUID id, @Valid  @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patientResponseDTO = patientService.updatePatient(id, patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Patient")
    private ResponseEntity<String> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok().body("Patient has been deleted");
    }


}
