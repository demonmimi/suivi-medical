package com.isetdjerba.hopital.suivi_medical.service;

import com.isetdjerba.hopital.suivi_medical.model.Patient;
import com.isetdjerba.hopital.suivi_medical.repository.PatientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient ajouterPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    public Patient getById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient non trouvé"));
    }

    public void supprimerPatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient non trouvé"));
        patientRepository.deleteById(id);
    }

    public Patient modifierPatient(Patient patient) {
        if (!patientRepository.existsById(patient.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient non trouvé");
        }
        return patientRepository.save(patient);
    }

    public List<Patient> rechercherPatient(String search) {
        return patientRepository.findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(search, search);
    }

    public List<Patient> getPatientsByProjet(Long projetId) {
        return patientRepository.findByProjetsId(projetId);
    }

    // Methods for examination workflow
    public Patient assignPatientToDoctor(Long patientId, Long doctorId, Long nurseId, java.util.Date dateExamen) {
        Patient patient = getById(patientId);
        
        if (doctorId != null) {
            com.isetdjerba.hopital.suivi_medical.model.Employe doctor = new com.isetdjerba.hopital.suivi_medical.model.Employe();
            doctor.setId(doctorId);
            patient.setAssignedDoctor(doctor);
        }
        
        if (nurseId != null) {
            com.isetdjerba.hopital.suivi_medical.model.Employe nurse = new com.isetdjerba.hopital.suivi_medical.model.Employe();
            nurse.setId(nurseId);
            patient.setAssignedNurse(nurse);
        }
        
        patient.setDateExamen(dateExamen);
        patient.setStatusExamen("EN_ATTENTE");
        
        return patientRepository.save(patient);
    }

    public List<Patient> getPatientsForDoctorToday(Long doctorId) {
        java.util.Date today = new java.util.Date();
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(today);
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cal.set(java.util.Calendar.MINUTE, 0);
        cal.set(java.util.Calendar.SECOND, 0);
        cal.set(java.util.Calendar.MILLISECOND, 0);
        
        return patientRepository.findByAssignedDoctorIdAndDateExamen(doctorId, cal.getTime());
    }

    public List<Patient> getAllPatientsToExamineToday() {
        java.util.Date today = new java.util.Date();
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(today);
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cal.set(java.util.Calendar.MINUTE, 0);
        cal.set(java.util.Calendar.SECOND, 0);
        cal.set(java.util.Calendar.MILLISECOND, 0);
        
        return patientRepository.findAllByDateExamenOrdered(cal.getTime());
    }

    public Patient updateExaminationStatus(Long patientId, String status) {
        Patient patient = getById(patientId);
        patient.setStatusExamen(status);
        return patientRepository.save(patient);
    }
}
