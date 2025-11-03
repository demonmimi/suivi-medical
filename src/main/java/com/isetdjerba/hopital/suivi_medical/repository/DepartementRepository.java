package com.isetdjerba.hopital.suivi_medical.repository;

import com.isetdjerba.hopital.suivi_medical.model.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartementRepository extends JpaRepository<Departement,Long> {    List<Departement> findByNomContainingIgnoreCase(String nom);
    Optional<Departement> findByNom(String nom);
}
