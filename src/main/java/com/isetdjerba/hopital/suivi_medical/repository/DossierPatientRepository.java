package com.isetdjerba.hopital.suivi_medical.repository;

import com.isetdjerba.hopital.suivi_medical.model.DossierPatient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DossierPatientRepository extends JpaRepository<DossierPatient, Long> {
    List<DossierPatient> findByPatientId(Long patientId);
    List<DossierPatient> findByMedecinId(Long medecinId);
}
