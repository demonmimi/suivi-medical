package com.isetdjerba.hopital.suivi_medical.service;

import com.isetdjerba.hopital.suivi_medical.model.DossierPatient;
import com.isetdjerba.hopital.suivi_medical.repository.DossierPatientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
public class DossierPatientService {

    private final DossierPatientRepository dossierRepository;

    public DossierPatientService(DossierPatientRepository dossierRepository) {
        this.dossierRepository = dossierRepository;
    }

    public DossierPatient creerDossier(DossierPatient dossier) {
        if (dossier.getDateConsultation() == null) {
            dossier.setDateConsultation(new Date());
        }
        return dossierRepository.save(dossier);
    }

    public List<DossierPatient> getAll() {
        return dossierRepository.findAll();
    }

    public DossierPatient getById(Long id) {
        return dossierRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dossier non trouvé"));
    }

    public List<DossierPatient> getDossiersByPatient(Long patientId) {
        return dossierRepository.findByPatientId(patientId);
    }

    public List<DossierPatient> getDossiersByMedecin(Long medecinId) {
        return dossierRepository.findByMedecinId(medecinId);
    }

    public DossierPatient mettreAJourDossier(DossierPatient dossier) {
        if (!dossierRepository.existsById(dossier.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dossier non trouvé");
        }
        return dossierRepository.save(dossier);
    }

    public void supprimerDossier(Long id) {
        if (!dossierRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dossier non trouvé");
        }
        dossierRepository.deleteById(id);
    }
}
