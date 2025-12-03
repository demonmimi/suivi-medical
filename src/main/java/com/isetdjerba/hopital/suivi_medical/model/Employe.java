package com.isetdjerba.hopital.suivi_medical.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private String prenom;
    private String poste;
    private String email;
    private Double salaire;
    @ManyToOne
    @JoinColumn(name = "departement_id")
    private Departement departement;

    // owning side of ManyToMany
    @ManyToMany
    @JoinTable(
            name = "employe_projet",
            joinColumns = @JoinColumn(name = "employe_id"),
            inverseJoinColumns = @JoinColumn(name = "projet_id")
    )
    @JsonIgnore
    private List<Projet> projets;
}
