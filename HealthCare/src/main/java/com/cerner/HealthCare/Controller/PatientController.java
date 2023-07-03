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

@RestController
@RequestMapping("/api/patients")
public class PatientController {

	private final PatientRepository patientRepository;
	private final ObjectMapper objectMapper;

	public PatientController(PatientRepository patientRepository, ObjectMapper objectMapper) {
		this.patientRepository = patientRepository;
		this.objectMapper = objectMapper;
	}

	@PostMapping("/save")
	public ResponseEntity<Patient> createPatient(@RequestBody String jsonPayload) {
		try {
			Patient patient = objectMapper.readValue(jsonPayload, Patient.class);
			Patient createdPatient = patientRepository.save(patient);
			return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("/getPatients")
	public List<Patient> getAllPatients() {
		return patientRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {

		Optional<Patient> patient = patientRepository.findById(id);
		
		if(patient.isPresent()) {
			
			return new ResponseEntity<>(patient.get(),HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
				
	}

	@PutMapping("/{id}")
	public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody String jsonPayload) {
		try {
			Patient existingPatient = patientRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));

			Patient updatedPatient = objectMapper.readerForUpdating(existingPatient).readValue(jsonPayload);

			patientRepository.save(updatedPatient);

			return ResponseEntity.ok(updatedPatient);
		}

		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePatient(@PathVariable Long id) {

		Patient patient = patientRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));

		patientRepository.delete(patient);
		return ResponseEntity.noContent().build();
	}

}
