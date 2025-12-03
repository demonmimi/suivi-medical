package com.isetdjerba.hopital.suivi_medical.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DossierPatient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "medecin_id")
    private Employe medecin;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dateConsultation;

    @Column(length = 2000)
    private String diagnostic;

    @Column(length = 2000)
    private String traitement;

    @Column(length = 3000)
    private String observations;

    @Column(length = 1000)
    private String symptomes;

    private String typeConsultation; // Consultation, Urgence, Suivi, etc.

    @Temporal(TemporalType.DATE)
    private Date dateProchainRendezVous;
}
