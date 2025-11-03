package com.isetdjerba.hopital.suivi_medical.controller;

import com.isetdjerba.hopital.suivi_medical.model.Projet;
import com.isetdjerba.hopital.suivi_medical.model.Employe;
import com.isetdjerba.hopital.suivi_medical.service.EmployeProjetService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/employe-projet")
public class EmployeProjetController {

    private final EmployeProjetService service;

    public EmployeProjetController(EmployeProjetService service) {
        this.service = service;
    }

    @PostMapping("/assign")
    public void assign(@RequestParam Long employeId, @RequestParam Long projetId) {
        service.assignEmployeToProjet(employeId, projetId);
    }

    @PostMapping("/remove")
    public void remove(@RequestParam Long employeId, @RequestParam Long projetId) {
        service.removeEmployeFromProjet(employeId, projetId);
    }

    @GetMapping("/employe/{id}/projets")
    public List<Projet> getProjetsByEmploye(@PathVariable Long id) {
        return service.getProjetsByEmploye(id);
    }

    @GetMapping("/projet/{id}/employes")
    public List<Employe> getEmployesByProjet(@PathVariable Long id) {
        return service.getEmployesByProjet(id);
    }
}
