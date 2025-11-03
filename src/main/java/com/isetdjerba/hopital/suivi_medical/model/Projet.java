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
public class Projet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    private String description;

    private Date dateDebut;
    private Date dateFin;
    private String statut;

    // Many projects -> one departement (project owns FK)
    @ManyToOne
    @JoinColumn(name = "departement_id")
    private Departement departement;

    // inverse side of many-to-many, Employe owns the join table
    @ManyToMany(mappedBy = "projets")
    @JsonIgnore
    private List<Employe> employes;
}
