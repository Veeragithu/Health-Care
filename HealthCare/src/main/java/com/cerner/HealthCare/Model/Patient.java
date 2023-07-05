package com.cerner.HealthCare.Model;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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

/**
 * Represents a patient.
 */
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
    @OneToMany(targetEntity = Address.class, cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(referencedColumnName = "patient_id", name = "patient_id")
    private List<Address> addresses;

    @ElementCollection
    @OneToMany(targetEntity = Telephone.class, cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(referencedColumnName = "patient_id", name = "patient_id")
    @Pattern(regexp = "\\d{10}", message = "Invalid Telephone number")
    private List<Telephone> telephoneNumbers;

    /**
     * Default constructor for the Patient class.
     */
    public Patient() {
        super();
    }

    /**
     * Get the ID of the patient.
     *
     * @return The patient ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the ID of the patient.
     *
     * @param id The patient ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the name of the patient.
     *
     * @return The patient name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the patient.
     *
     * @param name The patient name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the date of birth of the patient.
     *
     * @return The patient's date of birth.
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Set the date of birth of the patient.
     *
     * @param dateOfBirth The patient's date of birth to set.
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Get the gender of the patient.
     *
     * @return The patient's gender.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Set the gender of the patient.
     *
     * @param gender The patient's gender to set.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Get the addresses of the patient.
     *
     * @return A list of addresses associated with the patient.
     */
    public List<Address> getAddresses() {
        return addresses;
    }

    /**
     * Set the addresses of the patient.
     *
     * @param addresses A list of addresses to set for the patient.
     */
    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    /**
     * Get the telephone numbers of the patient.
     *
     * @return A list of telephone numbers associated with the patient.
     */
    public List<Telephone> getTelephoneNumbers() {
        return telephoneNumbers;
    }

    /**
     * Set the telephone numbers of the patient.
     *
     * @param telephoneNumbers A list of telephone numbers to set for the patient.
     */
    public void setTelephoneNumbers(List<Telephone> telephoneNumbers) {
        this.telephoneNumbers = telephoneNumbers;
    }

    
}
