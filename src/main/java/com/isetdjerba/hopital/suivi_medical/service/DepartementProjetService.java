package com.isetdjerba.hopital.suivi_medical.service;

import com.isetdjerba.hopital.suivi_medical.model.Departement;
import com.isetdjerba.hopital.suivi_medical.model.Projet;
import com.isetdjerba.hopital.suivi_medical.repository.DepartementRepository;
import com.isetdjerba.hopital.suivi_medical.repository.ProjetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class DepartementProjetService {

    private final DepartementRepository departementRepo;
    private final ProjetRepository projetRepo;

    public DepartementProjetService(DepartementRepository departementRepo, ProjetRepository projetRepo) {
        this.departementRepo = departementRepo;
        this.projetRepo = projetRepo;
    }

    @Transactional
    public Projet createProjetUnderDepartement(Long departementId, Projet projet) {
        Departement d = departementRepo.findById(departementId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Departement non trouvé"));
        projet.setDepartement(d);
        return projetRepo.save(projet);
    }

    @Transactional
    public Projet moveProjetToDepartement(Long projetId, Long newDepartementId) {
        Projet p = projetRepo.findById(projetId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projet non trouvé"));
        Departement d = departementRepo.findById(newDepartementId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Departement non trouvé"));
        p.setDepartement(d);
        return projetRepo.save(p);
    }

    public List<Projet> listProjetsByDepartement(Long departementId) {
        departementRepo.findById(departementId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Departement non trouvé"));
        return projetRepo.findByDepartementId(departementId);
    }
}
