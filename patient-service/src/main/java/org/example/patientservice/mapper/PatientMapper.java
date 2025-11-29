package org.example.patientservice.mapper;

import org.example.patientservice.dto.PatientRequestDTO;
import org.example.patientservice.dto.PatientResponseDTO;
import org.example.patientservice.model.Patient;

import java.time.LocalDate;

public class PatientMapper {
    public PatientResponseDTO toDTO(Patient patient) {
        PatientResponseDTO patientResponseDTO = new PatientResponseDTO();
        patientResponseDTO.setId(String.valueOf(patient.getId()));
        patientResponseDTO.setName(patient.getName());
        patientResponseDTO.setAddress(patient.getAddress());
        patientResponseDTO.setEmail(patient.getEmail());
        patientResponseDTO.setDateOfBirth(patient.getDate_of_birth().toString());
        return patientResponseDTO;

    }

    public Patient toEntity(PatientRequestDTO patientRequestDTO) {
        Patient patient = new Patient();
        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDate_of_birth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        patient.setRegistered_date(LocalDate.parse(patientRequestDTO.getRegisteredDate()));
        return patient;
    }
}
