package HealthCare.Controller;

import com.cerner.HealthCare.Controller.PatientController;
import com.cerner.HealthCare.Exception.BadRequestException;
import com.cerner.HealthCare.Exception.ResourceNotFoundException;
import com.cerner.HealthCare.Model.Address;
import com.cerner.HealthCare.Model.Patient;
import com.cerner.HealthCare.Model.Telephone;
import com.cerner.HealthCare.Repository.PatientRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PatientControllerTest {

	@Mock
	private PatientRepository patientRepository;

	@Mock
	private ObjectMapper objectMapper;

	private PatientController patientController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		patientController = new PatientController(patientRepository, objectMapper);
	}

	@Test
	void createPatientTest() throws Exception {

		String jsonPayload = "{\"name\":\"Veerababu\",\"dateOfBirth\":\"1995-05-05\",\"gender\":\"male\",\"addresses\":[{\"street\":\"srNagar\",\"city\":\"hyderabad\",\"state\":\"AndraPradesh\",\"postalCode\":\"12345\"}],\"telephoneNumbers\":[{\"number\":\"1234567897\"}]}";
		Patient patient = new Patient();
		patient.setName("Veerababu");
		patient.setDateOfBirth("1995-05-5");
		patient.setGender("male");

		List<Address> adress = new ArrayList<Address>();

		Address adr = new Address();
		adress.add(adr);

		adr.setStreet("srNagar");
		adr.setCity("hyderabad");
		adr.setState("AndraPradesh");
		adr.setPostalCode("12345");

		List<Telephone> telphone = new ArrayList<Telephone>();
		Telephone tel = new Telephone();
		tel.setNumber(1234567897);
		patient.setAddresses(adress);

		when(objectMapper.readValue(jsonPayload, Patient.class)).thenReturn(patient);
		when(patientRepository.save(patient)).thenReturn(patient);

		ResponseEntity<Patient> response = patientController.createPatient(jsonPayload);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(patient, response.getBody());
		verify(patientRepository, times(1)).save(patient);
	}

	@Test
	void deletePatientNoContentStatusTest() {

		Long id = 1L;
		Patient patient = new Patient();
		when(patientRepository.findById(id)).thenReturn(Optional.of(patient));

		ResponseEntity<Void> response = patientController.deletePatient(id);

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		assertNull(response.getBody());
		verify(patientRepository, times(1)).findById(id);
		verify(patientRepository, times(1)).delete(patient);
	}
	
	
	 @Test
	    public void testDeletePatient_ResourceNotFound() {
	        Long patientId = 1L;

	        // Mock the findById method of the patientRepository to return an empty Optional
	        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

	        // Call the method being tested and assert that the expected exception is thrown
	        Exception exception = assertThrows(ResourceNotFoundException.class, () -> patientController.deletePatient(patientId));

	        // Verify that the findById method of the patientRepository was called with the correct patient ID
	        verify(patientRepository).findById(patientId);

	        // Verify that the delete method of the patientRepository was not called
	        verify(patientRepository, never()).delete(any());

	        // Verify the exception message
	        String expectedMessage = "Patient not found with id: " + patientId;
	        String actualMessage = exception.getMessage();
	        assertEquals(expectedMessage, actualMessage);
	    }
	 
	 
	 @Test
	    public void testGetPatientforInvalidId1() {
	        Long invalidId = -1L;

	        // Throw a ResourceNotFoundException when the service method is called with the invalid ID
	        when(patientRepository.findById(invalidId)).thenThrow(ResourceNotFoundException.class);

	        // Call the method being tested and assert that the specified exception is thrown
	       // assertThrows(ResourceNotFoundException.class, () -> patientRepository.findById(invalidId));
	    }

	 
	@Test
	void getAllPatientsTest() {

		List<Patient> patients = Arrays.asList(new Patient(), new Patient());
		when(patientRepository.findAll()).thenReturn(patients);

		List<Patient> result = patientController.getAllPatients();

		assertEquals(patients, result);
		verify(patientRepository, times(1)).findAll();
	}

	
	@Test
	void getPatientByIdTest() {

		Long id = 1L;
		Patient patient = new Patient();
		when(patientRepository.findById(id)).thenReturn(Optional.of(patient));

		ResponseEntity<Patient> response = patientController.getPatientById(id);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(patient, response.getBody());
		verify(patientRepository, times(1)).findById(id);
	}
	

	@Test
	void getPatientByIforNotFoundStatusTest() {
		Long id = 1L;
		when(patientRepository.findById(id)).thenReturn(Optional.empty());

		ResponseEntity<Patient> response = patientController.getPatientById(id);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNull(response.getBody());
		verify(patientRepository, times(1)).findById(id);
	}
	

	@Test
	void updatePatientTest() throws Exception {
		
		Long id = 1L;
		String jsonPayload = "{\"name\":\"Veerababu\",\"dateOfBirth\":\"1995-05-05\",\"gender\":\"male\",\"addresses\":[{\"street\":\"srNagar\",\"city\":\"hyderabad\",\"state\":\"AndraPradesh\",\"postalCode\":\"12345\"}],\"telephoneNumbers\":[{\"number\":\"1234567897\"}]}";
		Patient existingPatient = new Patient();
		existingPatient.setId(id);
		existingPatient.setName("Existing Patient");

		Patient updatedPatient = new Patient();
		updatedPatient.setId(id);
		updatedPatient.setName("John Doe");

		PatientRepository patientRepository = mock(PatientRepository.class);
		ObjectMapper objectMapper = mock(ObjectMapper.class);
		ObjectReader objectReader = mock(ObjectReader.class);

		when(patientRepository.findById(id)).thenReturn(Optional.of(existingPatient));
		when(objectMapper.readerForUpdating(existingPatient)).thenReturn(objectReader);
		when(objectReader.readValue(jsonPayload)).thenReturn(updatedPatient);
		when(patientRepository.save(updatedPatient)).thenReturn(updatedPatient);

		PatientController patientController = new PatientController(patientRepository, objectMapper);

		ResponseEntity<Patient> response = patientController.updatePatient(id, jsonPayload);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(updatedPatient, response.getBody());
		verify(patientRepository, times(1)).findById(id);
		verify(patientRepository, times(1)).save(updatedPatient);
	}

	
	@Test
	void updatePatientforValidJsonPayloadTest() throws Exception {
		
		Long id = 1L;
		String jsonPayload = "{\"name\":\"Veerababu\",\"dateOfBirth\":\"1995-05-05\",\"gender\":\"male\",\"addresses\":[{\"street\":\"srNagar\",\"city\":\"hyderabad\",\"state\":\"AndraPradesh\",\"postalCode\":\"12345\"}],\"telephoneNumbers\":[{\"number\":\"1234567897\"}]}";
		Patient existingPatient = new Patient();
		existingPatient.setId(id);
		existingPatient.setName("Existing Patient");

		Patient updatedPatient = new Patient();
		updatedPatient.setId(id);
		updatedPatient.setName("John Doe");

		PatientRepository patientRepository = mock(PatientRepository.class);
		ObjectMapper objectMapper = mock(ObjectMapper.class);
		ObjectReader objectReader = mock(ObjectReader.class);

		when(patientRepository.findById(id)).thenThrow(new ResourceNotFoundException());

		ResponseEntity<Patient> response = patientController.updatePatient(id, jsonPayload);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		
	}

	
	@Test
	void createPatientTestForBadRequest() throws BadRequestException, JsonMappingException, JsonProcessingException {

		String jsonPayload = "{\"name\":\"Veerababu\",\"dateOfBirth\":\"1995-05-05\",\"gender\":\"male\",\"addresses\":[{\"street\":\"srNagar\",\"city\":\"hyderabad\",\"state\":\"AndraPradesh\",\"postalCode\":\"12345\"}],\"telephoneNumbers\":[{\"number\":\"1234567897\"}]}";
		Patient patient = new Patient();
		patient.setName("Veerababu");
		patient.setDateOfBirth("1995-05-5");
		patient.setGender("male");

		List<Address> adress = new ArrayList<Address>();

		Address adr = new Address();
		adress.add(adr);

		adr.setStreet("srNagar");
		adr.setCity("hyderabad");
		adr.setState("AndraPradesh");
		adr.setPostalCode("12345");

		List<Telephone> telphone = new ArrayList<Telephone>();
		Telephone tel = new Telephone();
		tel.setNumber(1234567897);
		patient.setAddresses(adress);

		when(objectMapper.readValue(jsonPayload, Patient.class)).thenThrow(new BadRequestException());

		ResponseEntity<Patient> response = patientController.createPatient(jsonPayload);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertNull(response.getBody());
		verify(patientRepository, never()).save(any());

	}

}
