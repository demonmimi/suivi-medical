package com.isetdjerba.hopital.suivi_medical.controller;

import com.isetdjerba.hopital.suivi_medical.model.Patient;
import com.isetdjerba.hopital.suivi_medical.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    // ADMIN and INFIRMIER can create patients
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'INFIRMIER')")
    public Patient create(@RequestBody Patient patient) {
        return service.ajouterPatient(patient);
    }

    // All authenticated roles can consult
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RESPONSABLE_PROJET', 'DOCTEUR', 'INFIRMIER', 'EMPLOYE')")
    public List<Patient> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RESPONSABLE_PROJET', 'DOCTEUR', 'INFIRMIER', 'EMPLOYE')")
    public ResponseEntity<Patient> getById(@PathVariable Long id) {
        Patient patient = service.getById(id);
        return ResponseEntity.ok(patient);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'RESPONSABLE_PROJET', 'DOCTEUR', 'INFIRMIER', 'EMPLOYE')")
    public List<Patient> search(@RequestParam String query) {
        return service.rechercherPatient(query);
    }

    @GetMapping("/projet/{projetId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RESPONSABLE_PROJET', 'DOCTEUR', 'INFIRMIER', 'EMPLOYE')")
    public List<Patient> getPatientsByProjet(@PathVariable Long projetId) {
        return service.getPatientsByProjet(projetId);
    }

    // ADMIN only - Update
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Patient update(@PathVariable Long id, @RequestBody Patient patient) {
        patient.setId(id);
        return service.modifierPatient(patient);
    }

    // ADMIN only - Delete
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.supprimerPatient(id);
        return ResponseEntity.ok().body("Patient supprimé avec succès");
    }

    // === EXAMINATION WORKFLOW ENDPOINTS ===

    // INFIRMIER and ADMIN can assign patients to doctors
    @PostMapping("/{patientId}/assign")
    @PreAuthorize("hasAnyRole('ADMIN', 'INFIRMIER')")
    public ResponseEntity<Patient> assignToDoctor(
            @PathVariable Long patientId,
            @RequestBody AssignmentRequest request) {
        
        Patient updated = service.assignPatientToDoctor(
            patientId, 
            request.getDoctorId(), 
            request.getNurseId(), 
            request.getDateExamen()
        );
        return ResponseEntity.ok(updated);
    }

    // DOCTEUR can get their patients to examine today
    @GetMapping("/my-patients-today")
    @PreAuthorize("hasRole('DOCTEUR')")
    public ResponseEntity<List<Patient>> getMyPatientsToday(@RequestParam Long doctorId) {
        List<Patient> patients = service.getPatientsForDoctorToday(doctorId);
        return ResponseEntity.ok(patients);
    }

    // ADMIN can get all patients to examine today
    @GetMapping("/all-patients-today")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Patient>> getAllPatientsToday() {
        List<Patient> patients = service.getAllPatientsToExamineToday();
        return ResponseEntity.ok(patients);
    }

    // DOCTEUR and ADMIN can update examination status
    @PutMapping("/{patientId}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTEUR')")
    public ResponseEntity<Patient> updateStatus(
            @PathVariable Long patientId,
            @RequestBody StatusUpdateRequest request) {
        Patient updated = service.updateExaminationStatus(patientId, request.getStatus());
        return ResponseEntity.ok(updated);
    }

    // Inner classes for request bodies
    public static class AssignmentRequest {
        private Long doctorId;
        private Long nurseId;
        private java.util.Date dateExamen;

        public Long getDoctorId() { return doctorId; }
        public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }
        public Long getNurseId() { return nurseId; }
        public void setNurseId(Long nurseId) { this.nurseId = nurseId; }
        public java.util.Date getDateExamen() { return dateExamen; }
        public void setDateExamen(java.util.Date dateExamen) { this.dateExamen = dateExamen; }
    }

    public static class StatusUpdateRequest {
        private String status;

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }
}
