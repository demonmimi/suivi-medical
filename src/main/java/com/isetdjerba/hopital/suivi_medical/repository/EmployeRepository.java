package com.isetdjerba.hopital.suivi_medical.repository;

import com.isetdjerba.hopital.suivi_medical.model.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface EmployeRepository extends JpaRepository<Employe, Long> {
    List<Employe> findByNomContainingIgnoreCase(String nom);
    List<Employe> findByProjetsId(Long projetId);

    @Query("SELECT COUNT(p) FROM Employe e JOIN e.projets p WHERE e.id = :employeId")
    long countProjectsByEmployeId(@Param("employeId") Long employeId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM employe_projet WHERE employe_id = :employeId", nativeQuery = true)
    void removeEmployeFromAllProjects(@Param("employeId") Long employeId);
}
