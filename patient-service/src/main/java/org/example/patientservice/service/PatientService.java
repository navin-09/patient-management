package org.example.patientservice.service;

import org.example.patientservice.dto.PatientRequestDTO;
import org.example.patientservice.dto.PatientResponseDTO;
import org.example.patientservice.exception.EmailAlreadyExistException;
import org.example.patientservice.mapper.PatientMapper;
import org.example.patientservice.model.Patient;
import org.example.patientservice.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;
    PatientMapper patientMapper = new PatientMapper();

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(patientMapper::toDTO).toList();
    }

    public PatientResponseDTO addPatient(PatientRequestDTO patientRequestDTO) {
        if (patientRepository.existsPatientByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistException("Email already exists"+patientRequestDTO.getEmail());
        }
        Patient patient = patientRepository.save(patientMapper.toEntity(patientRequestDTO));
        return patientMapper.toDTO(patient);
    }
}
