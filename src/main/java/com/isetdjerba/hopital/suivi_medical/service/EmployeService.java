package com.isetdjerba.hopital.suivi_medical.service;

import aj.org.objectweb.asm.commons.Remapper;
import com.isetdjerba.hopital.suivi_medical.model.Employe;
import com.isetdjerba.hopital.suivi_medical.repository.EmployeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EmployeService {

    private final EmployeRepository employeRepository;

    public EmployeService(EmployeRepository employeRepository) {
        this.employeRepository = employeRepository;
    }

    // Ajouter un employé
    public Employe ajouterEmploye(Employe e) {
        return employeRepository.save(e);
    }

    // Lister tous les employés
    public List<Employe> getAll() {
        return employeRepository.findAll();
    }

    // Chercher un employé par ID
    public Employe getById(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employé non trouvé"));
    }

    // Supprimer un employé (avec suppression automatique des projets)
    public void supprimerEmploye(Long id) {
        Employe e = employeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employé non trouvé"));

        // Automatically remove employee from all projects before deletion
        employeRepository.removeEmployeFromAllProjects(id);
        System.out.println("Removed employee " + id + " from all projects");

        employeRepository.deleteById(id);
        System.out.println("Deleted employee " + id);
    }
    // Modifier un employé
    public Employe modifierEmploye(Employe e) {
        if (!employeRepository.existsById(e.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employé non trouvé");
        }
        e.setNom(e.getNom());
        e.setId(e.getId());
        e.setProjets(e.getProjets());
        e.setDepartement(e.getDepartement());
        e.setPoste(e.getPoste());
        e.setEmail(e.getEmail());
        e.setSalaire(e.getSalaire());
        e.setPrenom(e.getPrenom());

        return employeRepository.save(e);
    }
    // Rechercher des employés par nom
    public List<Employe> rechercherParNom(String nom) {
        return employeRepository.findByNomContainingIgnoreCase(nom);
    }
}
