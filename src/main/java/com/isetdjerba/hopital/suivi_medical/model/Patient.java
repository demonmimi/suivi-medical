package com.isetdjerba.hopital.suivi_medical.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private String prenom;
    
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;
    
    private String sexe;
    private String telephone;
    private String adresse;
    private String email;
    private String numeroSecuriteSociale;

    // Fields for examination workflow
    @ManyToOne
    @JoinColumn(name = "assigned_doctor_id")
    private Employe assignedDoctor;

    @ManyToOne
    @JoinColumn(name = "assigned_nurse_id")
    private Employe assignedNurse;

    @Temporal(TemporalType.DATE)
    private Date dateExamen;

    @Column(length = 50)
    private String statusExamen; // "EN_ATTENTE", "EXAMINE", "ANNULE"

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<DossierPatient> dossiers;

    @ManyToMany(mappedBy = "patients")
    @JsonIgnore
    private List<Projet> projets;
}
