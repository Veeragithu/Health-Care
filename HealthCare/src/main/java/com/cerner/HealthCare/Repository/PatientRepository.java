package com.cerner.HealthCare.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cerner.HealthCare.Model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}
