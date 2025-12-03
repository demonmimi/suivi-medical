package com.isetdjerba.hopital.suivi_medical.controller;

import com.isetdjerba.hopital.suivi_medical.model.DossierPatient;
import com.isetdjerba.hopital.suivi_medical.service.DossierPatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dossiers")
public class DossierPatientController {

    private final DossierPatientService service;

    public DossierPatientController(DossierPatientService service) {
        this.service = service;
    }

    // DOCTEUR and INFIRMIER can create dossiers
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTEUR', 'INFIRMIER')")
    public DossierPatient create(@RequestBody DossierPatient dossier) {
        return service.creerDossier(dossier);
    }

    // All medical staff can consult dossiers
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RESPONSABLE_PROJET', 'DOCTEUR', 'INFIRMIER')")
    public List<DossierPatient> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RESPONSABLE_PROJET', 'DOCTEUR', 'INFIRMIER')")
    public ResponseEntity<DossierPatient> getById(@PathVariable Long id) {
        DossierPatient dossier = service.getById(id);
        return ResponseEntity.ok(dossier);
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RESPONSABLE_PROJET', 'DOCTEUR', 'INFIRMIER')")
    public List<DossierPatient> getDossiersByPatient(@PathVariable Long patientId) {
        return service.getDossiersByPatient(patientId);
    }

    @GetMapping("/medecin/{medecinId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RESPONSABLE_PROJET', 'DOCTEUR', 'INFIRMIER')")
    public List<DossierPatient> getDossiersByMedecin(@PathVariable Long medecinId) {
        return service.getDossiersByMedecin(medecinId);
    }

    // DOCTEUR and INFIRMIER can update dossiers
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTEUR', 'INFIRMIER')")
    public DossierPatient update(@PathVariable Long id, @RequestBody DossierPatient dossier) {
        dossier.setId(id);
        return service.mettreAJourDossier(dossier);
    }

    // Only ADMIN can delete dossiers
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.supprimerDossier(id);
        return ResponseEntity.ok().body("Dossier supprimé avec succès");
    }
}
