package com.isetdjerba.hopital.suivi_medical.service;

import com.isetdjerba.hopital.suivi_medical.model.Departement;
import com.isetdjerba.hopital.suivi_medical.model.Projet;
import com.isetdjerba.hopital.suivi_medical.repository.DepartementRepository;
import com.isetdjerba.hopital.suivi_medical.repository.ProjetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DepartementService {

    private final DepartementRepository departementRepository;
    private final ProjetRepository projetRepository;

    public DepartementService(DepartementRepository departementRepository, ProjetRepository projetRepository) {
        this.departementRepository = departementRepository;
        this.projetRepository = projetRepository;
    }

    // Ajouter un département
    public Departement ajouterDepartement(Departement d) {
        return departementRepository.save(d);
    }

    // Lister tous les départements
    public List<Departement> getAll() {
        return departementRepository.findAll();
    }

    // Chercher un département par ID
    public Departement getById(Long id) {
        return departementRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Département non trouvé"));
    }

    // Supprimer un département (avec vérification)
    public void supprimerDepartement(Long id) {
        Departement d = departementRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Département non trouvé"));

        List<Projet> projets = projetRepository.findByDepartementId(id);
        if (projets != null && !projets.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Impossible de supprimer : ce département contient encore des projets !");
        }

        departementRepository.deleteById(id);
    }
    // Modifier un département
    public Departement modifierDepartement(Long id, Departement d) {
        Departement existingDepartement = departementRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Département non trouvé"));

        existingDepartement.setNom(d.getNom());
    
        existingDepartement.setDescription(d.getDescription());
        return departementRepository.save(existingDepartement);
    }
    // Rechercher des départements par nom
    public List<Departement> rechercherParNom(String nom) {
        return departementRepository.findByNomContainingIgnoreCase(nom);
    }
}
