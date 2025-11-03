package com.isetdjerba.hopital.suivi_medical.controller;

import com.isetdjerba.hopital.suivi_medical.model.Employe;
import com.isetdjerba.hopital.suivi_medical.service.EmployeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employes")
public class EmployeController {

    private final EmployeService service;

    public EmployeController(EmployeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Employe> getAll() {
        return service.listerEmployes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employe> getById(@PathVariable Long id) {
        return service.trouverParId(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<Employe> searchByName(@RequestParam String nom) {
        return service.rechercherParNom(nom);
    }

    @PostMapping
    public Employe create(@RequestBody Employe employe) {
        return service.ajouterEmploye(employe);
    }

    @PutMapping("/{id}")
    public Employe update(@PathVariable Long id, @RequestBody Employe employe) {
        employe.setId(id);
        return service.modifierEmploye(employe);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.supprimerEmploye(id);
    }
}
