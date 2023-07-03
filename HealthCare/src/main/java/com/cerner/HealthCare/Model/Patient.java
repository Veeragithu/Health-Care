package com.cerner.HealthCare.Model;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.persistence.CascadeType;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class Patient {
	@Id
	@Column(name = "patient_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Name is required")
	private String name;

	@NotBlank(message = "Date of Birth is required")
	private String dateOfBirth;

	@NotBlank(message = "Gender is required")
	private String gender;

	@ElementCollection
	@OneToMany(targetEntity = Address.class, cascade = CascadeType.ALL, orphanRemoval =false)
	@JoinColumn(referencedColumnName = "patient_id", name = "patient_id")
	private List<Address> addresses;

	@ElementCollection
	@OneToMany(targetEntity = Telephone.class, cascade = CascadeType.ALL, orphanRemoval = false)
	@JoinColumn(referencedColumnName = "patient_id", name = "patient_id")
	@Pattern(regexp = "\\d{10}", message = "Invalid Telephone number")
	private List<Telephone> telephoneNumbers;

	@JsonCreator
	public Patient(@JsonProperty("name") String name, @JsonProperty("dateOfBirth") String dateOfBirth,
			@JsonProperty("gender") String gender, @JsonProperty("addresses") List<Address> addresses,
			@JsonProperty("telephoneNumbers") List<Telephone> telephoneNumbers) {
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.addresses = addresses;
		this.telephoneNumbers = telephoneNumbers;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public List<Telephone> getTelephoneNumbers() {
		return telephoneNumbers;
	}

	public void setTelephoneNumbers(List<Telephone> telephoneNumbers) {
		this.telephoneNumbers = telephoneNumbers;
	}

	public Patient() {

	}

	public Patient(Long id, @NotBlank(message = "Name is required") String name,
			@NotBlank(message = "Date of Birth is required") String dateOfBirth,
			@NotBlank(message = "Gender is required") String gender, List<Address> addresses,
			@Pattern(regexp = "\\d{10}", message = "Invalid Telephone number") List<Telephone> telephoneNumbers) {
		super();
		this.id = id;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.addresses = addresses;
		this.telephoneNumbers = telephoneNumbers;
	}

	@Override
	public int hashCode() {
		return Objects.hash(addresses, dateOfBirth, gender, id, name, telephoneNumbers);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Patient other = (Patient) obj;
		return Objects.equals(addresses, other.addresses) && Objects.equals(dateOfBirth, other.dateOfBirth)
				&& Objects.equals(gender, other.gender) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(telephoneNumbers, other.telephoneNumbers);
	}

	@Override
	public String toString() {
		return "Patient [id=" + id + ", name=" + name + ", dateOfBirth=" + dateOfBirth + ", gender=" + gender
				+ ", addresses=" + addresses + ", telephoneNumbers=" + telephoneNumbers + "]";
	}

	// Constructors, getters, and setters

}
