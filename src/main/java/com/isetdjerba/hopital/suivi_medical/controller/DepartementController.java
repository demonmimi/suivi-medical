package com.isetdjerba.hopital.suivi_medical.controller;

import com.isetdjerba.hopital.suivi_medical.model.Departement;
import com.isetdjerba.hopital.suivi_medical.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departements")
public class DepartementController {

    private final DepartementService service;

    public DepartementController(DepartementService service) {
        this.service = service;
    }

    @GetMapping
    public List<Departement> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Departement> getById(@PathVariable Long id) {
        Departement  departement = service.getById(id);
        if (departement == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(departement);
    }

    @GetMapping("/search")
    public List<Departement> searchByName(@RequestParam String nom) {
        return service.rechercherParNom(nom);
    }

    @PostMapping
    public Departement create(@RequestBody Departement departement) {
        return service.ajouterDepartement(departement);
    }

    @PutMapping("/{id}")
    public Departement update(@PathVariable Long id, @RequestBody Departement departement) {
        departement.setId(id);
        return service.modifierDepartement(departement);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.supprimerDepartement(id);
    }
    @GetMapping("/test")
    public String test() {
        return "Controller is working!";
    }

}