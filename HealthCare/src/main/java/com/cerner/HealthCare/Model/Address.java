package com.cerner.HealthCare.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Represents an address.
 */
@Entity
@Table(name = "address")
public class Address {

    @Id
    @Column(name = "address_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    private String city;
    private String state;
    private String postalCode;

    /**
     * Default constructor for the Address class.
     */
    public Address() {

    }

    /**
     * Get the ID of the address.
     *
     * @return The address ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the ID of the address.
     *
     * @param id The address ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the street of the address.
     *
     * @return The street of the address.
     */
    public String getStreet() {
        return street;
    }

    /**
     * Set the street of the address.
     *
     * @param street The street of the address to set.
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Get the city of the address.
     *
     * @return The city of the address.
     */
    public String getCity() {
        return city;
    }

    /**
     * Set the city of the address.
     *
     * @param city The city of the address to set.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Get the state of the address.
     *
     * @return The state of the address.
     */
    public String getState() {
        return state;
    }

    /**
     * Set the state of the address.
     *
     * @param state The state of the address to set.
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Get the postal code of the address.
     *
     * @return The postal code of the address.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Set the postal code of the address.
     *
     * @param postalCode The postal code of the address to set.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    
}
