package com.isetdjerba.hopital.suivi_medical;

import com.isetdjerba.hopital.suivi_medical.model.Departement;
import com.isetdjerba.hopital.suivi_medical.model.Employe;
import com.isetdjerba.hopital.suivi_medical.model.Projet;
import com.isetdjerba.hopital.suivi_medical.repository.DepartementRepository;
import com.isetdjerba.hopital.suivi_medical.repository.EmployeRepository;
import com.isetdjerba.hopital.suivi_medical.repository.ProjetRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

@Component
public class DataSeeder implements CommandLineRunner {

    private final DepartementRepository departementRepository;
    private final EmployeRepository employeRepository;
    private final ProjetRepository projetRepository;

    public DataSeeder(DepartementRepository departementRepository,
                      EmployeRepository employeRepository,
                      ProjetRepository projetRepository) {
        this.departementRepository = departementRepository;
        this.employeRepository = employeRepository;
        this.projetRepository = projetRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists by looking for a specific departement
        boolean dataExists = departementRepository.findByNom("Cardiologie").isPresent();
        System.out.println("Data exists check: " + dataExists);

        if (dataExists) {
            System.out.println("Data already seeded. Skipping...");
            return;
        }

        System.out.println("Seeding data...");

        // Create Departements
        Departement cardio = new Departement();
        cardio.setNom("Cardiologie");
        cardio.setDescription("Département spécialisé dans les maladies cardiovasculaires");

        Departement neuro = new Departement();
        neuro.setNom("Neurologie");
        neuro.setDescription("Département spécialisé dans les maladies du système nerveux");

        Departement pediatrie = new Departement();
        pediatrie.setNom("Pédiatrie");
        pediatrie.setDescription("Département spécialisé dans les soins des enfants");

        departementRepository.saveAll(Arrays.asList(cardio, neuro, pediatrie));
        System.out.println("Saved " + departementRepository.count() + " departements");

        // Create Employes
        Employe emp1 = new Employe();
        emp1.setNom("Dupont");
        emp1.setPrenom("Jean");
        emp1.setPoste("Médecin");
        emp1.setEmail("jean.dupont@hopital.com");
        emp1.setSalaire(5000.0);

        Employe emp2 = new Employe();
        emp2.setNom("Martin");
        emp2.setPrenom("Marie");
        emp2.setPoste("Infirmière");
        emp2.setEmail("marie.martin@hopital.com");
        emp2.setSalaire(3000.0);

        Employe emp3 = new Employe();
        emp3.setNom("Dubois");
        emp3.setPrenom("Pierre");
        emp3.setPoste("Médecin");
        emp3.setEmail("pierre.dubois@hopital.com");
        emp3.setSalaire(5500.0);

        Employe emp4 = new Employe();
        emp4.setNom("Leroy");
        emp4.setPrenom("Sophie");
        emp4.setPoste("Technicien");
        emp4.setEmail("sophie.leroy@hopital.com");
        emp4.setSalaire(2500.0);

        employeRepository.saveAll(Arrays.asList(emp1, emp2, emp3, emp4));
        System.out.println("Saved " + employeRepository.count() + " employes");

        // Create Projets
        Projet projet1 = new Projet();
        projet1.setTitre("Étude sur les maladies cardiaques");
        projet1.setDescription("Recherche sur les nouvelles méthodes de traitement des maladies cardiaques");
        projet1.setDateDebut(new Date());
        projet1.setDateFin(new Date(System.currentTimeMillis() + 365L * 24 * 60 * 60 * 1000)); // 1 year later
        projet1.setStatut("En cours");
        projet1.setDepartement(cardio);

        Projet projet2 = new Projet();
        projet2.setTitre("Programme de neurologie pédiatrique");
        projet2.setDescription("Développement de programmes de soin pour les enfants atteints de maladies neurologiques");
        projet2.setDateDebut(new Date());
        projet2.setDateFin(new Date(System.currentTimeMillis() + 180L * 24 * 60 * 60 * 1000)); // 6 months later
        projet2.setStatut("Planifié");
        projet2.setDepartement(neuro);

        Projet projet3 = new Projet();
        projet3.setTitre("Vaccination pédiatrique");
        projet3.setDescription("Campagne de vaccination pour les enfants de 0 à 5 ans");
        projet3.setDateDebut(new Date(System.currentTimeMillis() - 30L * 24 * 60 * 60 * 1000)); // 30 days ago
        projet3.setDateFin(new Date(System.currentTimeMillis() + 60L * 24 * 60 * 60 * 1000)); // 60 days from now
        projet3.setStatut("En cours");
        projet3.setDepartement(pediatrie);

        projetRepository.saveAll(Arrays.asList(projet1, projet2, projet3));
        System.out.println("Saved " + projetRepository.count() + " projets");

        // Set relationships after projects are saved
        projet1.setEmployes(Arrays.asList(emp1, emp2));
        emp1.setProjets(Arrays.asList(projet1));
        emp2.setProjets(Arrays.asList(projet1, projet3));

        projet2.setEmployes(Arrays.asList(emp3));
        emp3.setProjets(Arrays.asList(projet2));

        projet3.setEmployes(Arrays.asList(emp2, emp4));
        emp4.setProjets(Arrays.asList(projet3));

        // Save updated relationships
        employeRepository.saveAll(Arrays.asList(emp1, emp2, emp3, emp4));
        projetRepository.saveAll(Arrays.asList(projet1, projet2, projet3));
        System.out.println("Updated relationships and saved");

        System.out.println("Data seeding completed!");
    }
}