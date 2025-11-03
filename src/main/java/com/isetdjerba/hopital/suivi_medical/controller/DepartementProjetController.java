package com.isetdjerba.hopital.suivi_medical.controller;

import com.isetdjerba.hopital.suivi_medical.model.Projet;
import com.isetdjerba.hopital.suivi_medical.service.DepartementProjetService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/departement-projet")
public class DepartementProjetController {

    private final DepartementProjetService service;

    public DepartementProjetController(DepartementProjetService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Projet create(@RequestParam Long departementId, @RequestBody Projet projet) {
        return service.createProjetUnderDepartement(departementId, projet);
    }

    @PutMapping("/move")
    public Projet move(@RequestParam Long projetId, @RequestParam Long newDepartementId) {
        return service.moveProjetToDepartement(projetId, newDepartementId);
    }

    @GetMapping("/departement/{id}/projets")
    public List<Projet> listByDepartement(@PathVariable Long id) {
        return service.listProjetsByDepartement(id);
    }
}
