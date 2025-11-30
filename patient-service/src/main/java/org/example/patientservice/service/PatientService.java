package org.example.patientservice.service;

import org.example.patientservice.dto.PatientRequestDTO;
import org.example.patientservice.dto.PatientResponseDTO;
import org.example.patientservice.exception.EmailAlreadyExistException;
import org.example.patientservice.exception.PatientNotFoundException;
import org.example.patientservice.grpc.BilingServiceGrpcClient;
import org.example.patientservice.kafka.KafkaProducer;
import org.example.patientservice.mapper.PatientMapper;
import org.example.patientservice.model.Patient;
import org.example.patientservice.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private  final BilingServiceGrpcClient bilingServiceGrpcClient;
    @Autowired
    private KafkaProducer kafkaProducer;

    PatientMapper patientMapper = new PatientMapper();

    public PatientService(BilingServiceGrpcClient bilingServiceGrpcClient) {
        this.bilingServiceGrpcClient = bilingServiceGrpcClient;
    }

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(patientMapper::toDTO).toList();
    }

    public PatientResponseDTO addPatient(PatientRequestDTO patientRequestDTO) {
        if (patientRepository.existsPatientByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistException("Email already exists"+patientRequestDTO.getEmail());
        }
        Patient patient = patientRepository.save(patientMapper.toEntity(patientRequestDTO));
        bilingServiceGrpcClient.createBillingAccount(patient.getId().toString(),patient.getEmail(),patient.getName());
        kafkaProducer.sendEvent(patient);
        return patientMapper.toDTO(patient);
    }

    public PatientResponseDTO updatePatient(UUID id,PatientRequestDTO patientRequestDTO) {
        Patient patient =  patientRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException("Patient not found with ID:" + id)
        );
        if (patientRepository.existsPatientByEmailAndIdNot(patientRequestDTO.getEmail(), id)) {
            throw new EmailAlreadyExistException("Email already exists"+patientRequestDTO.getEmail());
        }
        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDate_of_birth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        patientRepository.save(patient);
        return patientMapper.toDTO(patient);

    }

    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }
}
