package com.isetdjerba.hopital.suivi_medical.controller;

import com.isetdjerba.hopital.suivi_medical.model.Projet;
import com.isetdjerba.hopital.suivi_medical.service.ProjetService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/projets")
public class ProjetController {

    private final ProjetService service;

    public ProjetController(ProjetService service) {
        this.service = service;
    }

    // All authenticated roles can consult
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RESPONSABLE_PROJET', 'DOCTEUR', 'INFIRMIER', 'EMPLOYE')")
    public List<Projet> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RESPONSABLE_PROJET', 'DOCTEUR', 'INFIRMIER', 'EMPLOYE')")
    public ResponseEntity<Projet> getById(@PathVariable Long id) {
       Projet projet = service.getById(id);
        return projet != null ? ResponseEntity.ok(projet) : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'RESPONSABLE_PROJET', 'DOCTEUR', 'INFIRMIER', 'EMPLOYE')")
    public List<Projet> searchByTitle(@RequestParam String titre) {
        return service.rechercherParTitre(titre);
    }

    // ADMIN and RESPONSABLE_PROJET can create/modify
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RESPONSABLE_PROJET')")
    public Projet create(@RequestBody Projet projet) {
        return service.ajouterProjet(projet);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RESPONSABLE_PROJET')")
    public Projet update(@PathVariable Long id, @RequestBody Projet projet) {
        projet.setId(id);
        return service.modifierProjet(projet);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RESPONSABLE_PROJET')")
    public void delete(@PathVariable Long id) {
        service.supprimerProjet(id);
    }

    // RESPONSABLE_PROJET can track project progress
    @PutMapping("/{id}/suivre")
    @PreAuthorize("hasAnyRole('ADMIN', 'RESPONSABLE_PROJET')")
    public Projet suivreProjet(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        String statut = (String) updates.get("statut");
        Double progression = updates.get("progression") != null ? 
            ((Number) updates.get("progression")).doubleValue() : null;
        return service.suivreProjet(id, statut, progression);
    }

    @GetMapping("/{id}/details")
    @PreAuthorize("hasAnyRole('ADMIN', 'RESPONSABLE_PROJET', 'DOCTEUR', 'INFIRMIER', 'EMPLOYE')")
    public ResponseEntity<Projet> getProjetDetails(@PathVariable Long id) {
        Projet projet = service.getProjetDetails(id);
        return ResponseEntity.ok(projet);
    }
}
