package com.isetdjerba.hopital.suivi_medical.repository;

import com.isetdjerba.hopital.suivi_medical.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Date;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(String nom, String prenom);
    List<Patient> findByProjetsId(Long projetId);
    
    // Queries for examination workflow
    List<Patient> findByAssignedDoctorIdAndDateExamen(Long doctorId, Date dateExamen);
    
    List<Patient> findByDateExamen(Date dateExamen);
    
    @Query("SELECT p FROM Patient p WHERE p.dateExamen = :date ORDER BY p.assignedDoctor.nom, p.assignedNurse.nom")
    List<Patient> findAllByDateExamenOrdered(@Param("date") Date date);
}
