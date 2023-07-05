package com.cerner.HealthCare.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Represents a telephone number.
 */
@Entity
@Table(name = "telephone")
public class Telephone {

    @Id
    @Column(name = "telephone_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number")
    private Integer number;

    /**
     * Default constructor for the Telephone class.
     */
    public Telephone() {

    }

    /**
     * Get the ID of the telephone number.
     *
     * @return The telephone number ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the ID of the telephone number.
     *
     * @param id The telephone number ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the number of the telephone number.
     *
     * @return The telephone number.
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * Set the number of the telephone number.
     *
     * @param number The telephone number to set.
     */
    public void setNumber(Integer number) {
        this.number = number;
    }

   
}
