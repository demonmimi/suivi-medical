package com.isetdjerba.hopital.suivi_medical.service;

import com.isetdjerba.hopital.suivi_medical.model.Projet;
import com.isetdjerba.hopital.suivi_medical.repository.ProjetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

    import java.util.List;

@Service
public class ProjetService {

    private final ProjetRepository projetRepository;

    public ProjetService(ProjetRepository projetRepository) {
        this.projetRepository = projetRepository;
    }

    // Ajouter un projet
    public Projet ajouterProjet(Projet p) {
        return projetRepository.save(p);
        }

    // Lister tous les projets
    public List<Projet> getAll() {
        return projetRepository.findAll();
    }

    // Chercher un projet par ID
    public Projet getById(Long id) {
        return projetRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projet non trouvé"));
    }

    // Supprimer un projet (avec vérification des employés affectés)
    public void supprimerProjet(Long id) {
        Projet p = projetRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projet non trouvé"));

        if (p.getEmployes() != null && !p.getEmployes().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Impossible de supprimer : des employés sont encore affectés à ce projet !");
        }
        projetRepository.deleteById(id);
    }
    // Modifier un projet
    public Projet modifierProjet(Projet p) {
        Projet existingProjet = projetRepository.findById(p.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projet non trouvé"));

        existingProjet.setTitre(p.getTitre());
        existingProjet.setDescription(p.getDescription());
        existingProjet.setDepartement(p.getDepartement());
        existingProjet.setDateDebut(p.getDateDebut());
        existingProjet.setDateFin(p.getDateFin());
        existingProjet.setStatut(p.getStatut());
        return projetRepository.save(existingProjet);
    }

    // Suivre projet médical - Update status and progression
    public Projet suivreProjet(Long id, String statut, Double progression) {
        Projet projet = projetRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projet non trouvé"));

        if (statut != null) {
            projet.setStatut(statut);
        }
        if (progression != null) {
            if (progression < 0 || progression > 100) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La progression doit être entre 0 et 100");
            }
            projet.setProgression(progression);
        }
        return projetRepository.save(projet);
    }

    // Get detailed project info for tracking
    public Projet getProjetDetails(Long id) {
        return projetRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projet non trouvé"));
    }

    // Rechercher des projets par titre
    public List<Projet> rechercherParTitre(String titre) {
        return projetRepository.findByTitreContainingIgnoreCase(titre);
    }
}
