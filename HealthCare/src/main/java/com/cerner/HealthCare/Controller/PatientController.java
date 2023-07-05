package com.cerner.HealthCare.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cerner.HealthCare.Exception.ResourceNotFoundException;
import com.cerner.HealthCare.Model.Patient;
import com.cerner.HealthCare.Repository.PatientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The controller class for handling patient-related operations.
 */
@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientRepository patientRepository;
    private final ObjectMapper objectMapper;

    /**
     * Constructor for the PatientController class.
     * 
     * @param patientRepository The repository for accessing patient data.
     * @param objectMapper     The object mapper for JSON serialization/deserialization.
     */
    public PatientController(PatientRepository patientRepository, ObjectMapper objectMapper) {
        this.patientRepository = patientRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * Create a new patient.
     * 
     * @param jsonPayload The JSON payload containing the patient data.
     * @return The created patient as a ResponseEntity with HTTP status 201 (Created),
     *         or a ResponseEntity with HTTP status 400 (Bad Request) if there was an error in the request or payload.
     */
    @PostMapping("/save")
    public ResponseEntity<Patient> createPatient(@RequestBody String jsonPayload) {
        try {
            Patient patient = objectMapper.readValue(jsonPayload, Patient.class);
            Patient createdPatient = patientRepository.save(patient);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Get all patients.
     * 
     * @return A list of all patients.
     */
    @GetMapping("/getPatients")
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    /**
     * Get a patient by ID.
     * 
     * @param id The ID of the patient.
     * @return The patient with the specified ID as a ResponseEntity with HTTP status 200 (OK),
     *         or a ResponseEntity with HTTP status 404 (Not Found) if the patient was not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        Optional<Patient> patient = patientRepository.findById(id);

        if (patient.isPresent()) {
            return new ResponseEntity<>(patient.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Update a patient.
     * 
     * @param id           The ID of the patient to update.
     * @param jsonPayload  The JSON payload containing the updated patient data.
     * @return The updated patient as a ResponseEntity with HTTP status 200 (OK),
     *         or a ResponseEntity with HTTP status 400 (Bad Request) if there was an error in the request or payload,
     *         or a ResponseEntity with HTTP status 404 (Not Found) if the patient was not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody String jsonPayload) {
        try {
            Patient existingPatient = patientRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));

            Patient updatedPatient = objectMapper.readerForUpdating(existingPatient).readValue(jsonPayload);

            patientRepository.save(updatedPatient);

            return ResponseEntity.ok(updatedPatient);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Delete a patient.
     * 
     * @param id The ID of the patient to delete.
     * @return A ResponseEntity with HTTP status 204 (No Content) if the patient was successfully deleted,
     *         or a ResponseEntity with HTTP status 404 (Not Found) if the patient was not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));

        patientRepository.delete(patient);
        return ResponseEntity.noContent().build();
    }
}
