package com.isetdjerba.hopital.suivi_medical.service;

import com.isetdjerba.hopital.suivi_medical.model.Employe;
import com.isetdjerba.hopital.suivi_medical.model.Projet;
import com.isetdjerba.hopital.suivi_medical.repository.EmployeRepository;
import com.isetdjerba.hopital.suivi_medical.repository.ProjetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class EmployeProjetService {

    private final EmployeRepository employeRepo;
    private final ProjetRepository projetRepo;

    public EmployeProjetService(EmployeRepository employeRepo, ProjetRepository projetRepo) {
        this.employeRepo = employeRepo;
        this.projetRepo = projetRepo;
    }

    @Transactional
    public void assignEmployeToProjet(Long employeId, Long projetId) {
        Employe e = employeRepo.findById(employeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employe non trouvé"));
        Projet p = projetRepo.findById(projetId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projet non trouvé"));

        if (e.getProjets() == null) {
            e.setProjets(new java.util.ArrayList<>());
        }
        
        if (e.getProjets().stream().anyMatch(proj -> proj.getId().equals(projetId))) {
            return; // déjà assigné
        }
        
        e.getProjets().add(p);
        employeRepo.save(e);
    }

    @Transactional
    public void removeEmployeFromProjet(Long employeId, Long projetId) {
        Employe e = employeRepo.findById(employeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employe non trouvé"));
        
        // Verify project exists
        projetRepo.findById(projetId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projet non trouvé"));

        if (e.getProjets() != null && e.getProjets().removeIf(pr -> pr.getId().equals(projetId))) {
            employeRepo.save(e);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L'employé n'est pas affecté au projet");
        }
    }

    public List<Projet> getProjetsByEmploye(Long employeId) {
        Employe e = employeRepo.findById(employeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employe non trouvé"));
        return e.getProjets();
    }

    public List<Employe> getEmployesByProjet(Long projetId) {
        return employeRepo.findByProjetsId(projetId);
    }
}
