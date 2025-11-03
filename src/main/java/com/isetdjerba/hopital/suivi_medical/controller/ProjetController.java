package com.isetdjerba.hopital.suivi_medical.controller;

import com.isetdjerba.hopital.suivi_medical.model.Projet;
import com.isetdjerba.hopital.suivi_medical.service.ProjetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projets")
public class ProjetController {

    private final ProjetService service;

    public ProjetController(ProjetService service) {
        this.service = service;
    }

    @GetMapping
    public List<Projet> getAll() {
        return service.listerProjets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Projet> getById(@PathVariable Long id) {
        return service.trouverParId(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<Projet> searchByTitle(@RequestParam String titre) {
        return service.rechercherParTitre(titre);
    }

    @PostMapping
    public Projet create(@RequestBody Projet projet) {
        return service.ajouterProjet(projet);
    }

    @PutMapping("/{id}")
    public Projet update(@PathVariable Long id, @RequestBody Projet projet) {
        projet.setId(id);
        return service.modifierProjet(projet);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.supprimerProjet(id);
    }
}
