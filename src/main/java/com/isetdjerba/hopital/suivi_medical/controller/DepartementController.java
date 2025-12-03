package com.isetdjerba.hopital.suivi_medical.controller;

import com.isetdjerba.hopital.suivi_medical.model.Departement;
import com.isetdjerba.hopital.suivi_medical.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departements")
public class DepartementController {

    private final DepartementService service;

    public DepartementController(DepartementService service) {
        this.service = service;
    }

    // All authenticated roles can consult
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RESPONSABLE_PROJET', 'DOCTEUR', 'INFIRMIER', 'EMPLOYE')")
    public List<Departement> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RESPONSABLE_PROJET', 'DOCTEUR', 'INFIRMIER', 'EMPLOYE')")
    public ResponseEntity<Departement> getById(@PathVariable Long id) {
        Departement  departement = service.getById(id);
        if (departement == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(departement);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'RESPONSABLE_PROJET', 'DOCTEUR', 'INFIRMIER', 'EMPLOYE')")
    public List<Departement> searchByName(@RequestParam String nom) {
        return service.rechercherParNom(nom);
    }

    // Only ADMIN can modify
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Departement create(@RequestBody Departement departement) {
        return service.ajouterDepartement(departement);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Departement update(@PathVariable Long id, @RequestBody Departement departement) {
 
        return service.modifierDepartement(id,departement);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        service.supprimerDepartement(id);
    }
    
    @GetMapping("/test")
    public String test() {
        return "Controller is working!";
    }

}