package com.isetdjerba.hopital.suivi_medical;

import com.isetdjerba.hopital.suivi_medical.model.*;
import com.isetdjerba.hopital.suivi_medical.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Component
public class DataSeeder implements CommandLineRunner {

    private final DepartementRepository departementRepository;
    private final EmployeRepository employeRepository;
    private final ProjetRepository projetRepository;
    private final PatientRepository patientRepository;
    private final DossierPatientRepository dossierPatientRepository;

    public DataSeeder(DepartementRepository departementRepository,
                      EmployeRepository employeRepository,
                      ProjetRepository projetRepository,
                      PatientRepository patientRepository,
                      DossierPatientRepository dossierPatientRepository) {
        this.departementRepository = departementRepository;
        this.employeRepository = employeRepository;
        this.projetRepository = projetRepository;
        this.patientRepository = patientRepository;
        this.dossierPatientRepository = dossierPatientRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists by counting departements
        long departementCount = departementRepository.count();
        System.out.println("Current departement count: " + departementCount);

        if (departementCount > 0) {
            System.out.println("✅ Database already contains data. Skipping seeding...");
            System.out.println("   - Departements: " + departementRepository.count());
            System.out.println("   - Employes: " + employeRepository.count());
            System.out.println("   - Patients: " + patientRepository.count());
            System.out.println("   - Projets: " + projetRepository.count());
            System.out.println("   - Dossiers: " + dossierPatientRepository.count());
            return;
        }

        System.out.println("🌱 Starting data seeding for MySQL database 'GestionHopital'...");

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

        // Create additional departments for a more comprehensive hospital
        Departement chirurgie = new Departement();
        chirurgie.setNom("Chirurgie");
        chirurgie.setDescription("Département de chirurgie générale et spécialisée");

        Departement radiologie = new Departement();
        radiologie.setNom("Radiologie");
        radiologie.setDescription("Département d'imagerie médicale et radiologie");

        Departement urgences = new Departement();
        urgences.setNom("Urgences");
        urgences.setDescription("Service des urgences médicales");

        departementRepository.saveAll(Arrays.asList(cardio, neuro, pediatrie, chirurgie, radiologie, urgences));
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

        Employe emp5 = new Employe();
        emp5.setNom("Garcia");
        emp5.setPrenom("Antonio");
        emp5.setPoste("Chirurgien");
        emp5.setEmail("antonio.garcia@hopital.com");
        emp5.setSalaire(6500.0);

        Employe emp6 = new Employe();
        emp6.setNom("Bernard");
        emp6.setPrenom("Lucie");
        emp6.setPoste("Radiologue");
        emp6.setEmail("lucie.bernard@hopital.com");
        emp6.setSalaire(4800.0);

        Employe emp7 = new Employe();
        emp7.setNom("Thomas");
        emp7.setPrenom("Michel");
        emp7.setPoste("Urgentiste");
        emp7.setEmail("michel.thomas@hopital.com");
        emp7.setSalaire(5200.0);

        Employe emp8 = new Employe();
        emp8.setNom("Petit");
        emp8.setPrenom("Emma");
        emp8.setPoste("Infirmière");
        emp8.setEmail("emma.petit@hopital.com");
        emp8.setSalaire(3200.0);

        Employe emp9 = new Employe();
        emp9.setNom("Robert");
        emp9.setPrenom("David");
        emp9.setPoste("Pharmacien");
        emp9.setEmail("david.robert@hopital.com");
        emp9.setSalaire(4500.0);

        Employe emp10 = new Employe();
        emp10.setNom("Richard");
        emp10.setPrenom("Sophie");
        emp10.setPoste("Administrateur");
        emp10.setEmail("sophie.richard@hopital.com");
        emp10.setSalaire(3800.0);

        employeRepository.saveAll(Arrays.asList(emp1, emp2, emp3, emp4, emp5, emp6, emp7, emp8, emp9, emp10));
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

        Projet projet4 = new Projet();
        projet4.setTitre("Chirurgie robotique");
        projet4.setDescription("Implémentation de la chirurgie assistée par robot dans le bloc opératoire");
        projet4.setDateDebut(new Date(System.currentTimeMillis() - 15L * 24 * 60 * 60 * 1000)); // 15 days ago
        projet4.setDateFin(new Date(System.currentTimeMillis() + 200L * 24 * 60 * 60 * 1000)); // 200 days from now
        projet4.setStatut("En cours");
        projet4.setDepartement(chirurgie);

        Projet projet5 = new Projet();
        projet5.setTitre("IRM 3D pour le diagnostic");
        projet5.setDescription("Développement de techniques d'IRM 3D pour un diagnostic plus précis");
        projet5.setDateDebut(new Date(System.currentTimeMillis() - 45L * 24 * 60 * 60 * 1000)); // 45 days ago
        projet5.setDateFin(new Date(System.currentTimeMillis() + 90L * 24 * 60 * 60 * 1000)); // 90 days from now
        projet5.setStatut("En cours");
        projet5.setDepartement(radiologie);

        Projet projet6 = new Projet();
        projet6.setTitre("Triage intelligent aux urgences");
        projet6.setDescription("Système de triage automatisé utilisant l'IA pour optimiser les soins d'urgence");
        projet6.setDateDebut(new Date());
        projet6.setDateFin(new Date(System.currentTimeMillis() + 150L * 24 * 60 * 60 * 1000)); // 150 days from now
        projet6.setStatut("Planifié");
        projet6.setDepartement(urgences);

        Projet projet7 = new Projet();
        projet7.setTitre("Programme de prévention cardiovasculaire");
        projet7.setDescription("Campagne de prévention des maladies cardiovasculaires dans la région");
        projet7.setDateDebut(new Date(System.currentTimeMillis() - 60L * 24 * 60 * 60 * 1000)); // 60 days ago
        projet7.setDateFin(new Date(System.currentTimeMillis() + 120L * 24 * 60 * 60 * 1000)); // 120 days from now
        projet7.setStatut("En cours");
        projet7.setDepartement(cardio);

        Projet projet8 = new Projet();
        projet8.setTitre("Suivi neurologique à distance");
        projet8.setDescription("Plateforme de télémédecine pour le suivi des patients neurologiques");
        projet8.setDateDebut(new Date(System.currentTimeMillis() - 20L * 24 * 60 * 60 * 1000)); // 20 days ago
        projet8.setDateFin(new Date(System.currentTimeMillis() + 300L * 24 * 60 * 60 * 1000)); // 300 days from now
        projet8.setStatut("En cours");
        projet8.setDepartement(neuro);

        projetRepository.saveAll(Arrays.asList(projet1, projet2, projet3, projet4, projet5, projet6, projet7, projet8));
        System.out.println("Saved " + projetRepository.count() + " projets");

        // Set relationships after projects are saved
        // Projet 1: Étude sur les maladies cardiaques - Cardiologie
        projet1.setEmployes(Arrays.asList(emp1, emp2, emp5)); // Médecin, Infirmière, Chirurgien
        emp1.setProjets(Arrays.asList(projet1, projet7));
        emp2.setProjets(Arrays.asList(projet1, projet3));
        emp5.setProjets(Arrays.asList(projet1, projet4));

        // Projet 2: Programme de neurologie pédiatrique - Neurologie
        projet2.setEmployes(Arrays.asList(emp3, emp6)); // Médecin, Radiologue
        emp3.setProjets(Arrays.asList(projet2, projet8));
        emp6.setProjets(Arrays.asList(projet2));

        // Projet 3: Vaccination pédiatrique - Pédiatrie
        projet3.setEmployes(Arrays.asList(emp2, emp4, emp8)); // Infirmière, Technicien, Infirmière
        emp4.setProjets(Arrays.asList(projet3));
        emp8.setProjets(Arrays.asList(projet3));

        // Projet 4: Chirurgie robotique - Chirurgie
        projet4.setEmployes(Arrays.asList(emp5, emp7)); // Chirurgien, Urgentiste
        emp7.setProjets(Arrays.asList(projet4, projet6));

        // Projet 5: IRM 3D pour le diagnostic - Radiologie
        projet5.setEmployes(Arrays.asList(emp6, emp9)); // Radiologue, Pharmacien
        emp9.setProjets(Arrays.asList(projet5));

        // Projet 6: Triage intelligent aux urgences - Urgences
        projet6.setEmployes(Arrays.asList(emp7, emp8, emp10)); // Urgentiste, Infirmière, Administrateur
        emp10.setProjets(Arrays.asList(projet6));

        // Projet 7: Programme de prévention cardiovasculaire - Cardiologie
        projet7.setEmployes(Arrays.asList(emp1, emp2, emp9)); // Médecin, Infirmière, Pharmacien

        // Projet 8: Suivi neurologique à distance - Neurologie
        projet8.setEmployes(Arrays.asList(emp3, emp6, emp10)); // Médecin, Radiologue, Administrateur

        // Save updated relationships
        employeRepository.saveAll(Arrays.asList(emp1, emp2, emp3, emp4, emp5, emp6, emp7, emp8, emp9, emp10));
        projetRepository.saveAll(Arrays.asList(projet1, projet2, projet3, projet4, projet5, projet6, projet7, projet8));
        System.out.println("Updated relationships and saved");

        // Create Patients
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        Patient patient1 = new Patient();
        patient1.setNom("Moreau");
        patient1.setPrenom("Alice");
        patient1.setDateNaissance(sdf.parse("1985-03-15"));
        patient1.setSexe("F");
        patient1.setTelephone("0612345678");
        patient1.setAdresse("15 Rue de la Paix, Paris");
        patient1.setEmail("alice.moreau@email.com");
        patient1.setNumeroSecuriteSociale("285033175012345");

        Patient patient2 = new Patient();
        patient2.setNom("Lambert");
        patient2.setPrenom("Marc");
        patient2.setDateNaissance(sdf.parse("1978-07-22"));
        patient2.setSexe("M");
        patient2.setTelephone("0623456789");
        patient2.setAdresse("28 Avenue des Champs, Lyon");
        patient2.setEmail("marc.lambert@email.com");
        patient2.setNumeroSecuriteSociale("178073175023456");

        Patient patient3 = new Patient();
        patient3.setNom("Girard");
        patient3.setPrenom("Julie");
        patient3.setDateNaissance(sdf.parse("1992-11-08"));
        patient3.setSexe("F");
        patient3.setTelephone("0634567890");
        patient3.setAdresse("42 Boulevard Victor Hugo, Marseille");
        patient3.setEmail("julie.girard@email.com");
        patient3.setNumeroSecuriteSociale("292113175034567");

        Patient patient4 = new Patient();
        patient4.setNom("Roux");
        patient4.setPrenom("Thomas");
        patient4.setDateNaissance(sdf.parse("2010-05-12"));
        patient4.setSexe("M");
        patient4.setTelephone("0645678901");
        patient4.setAdresse("8 Rue des Lilas, Toulouse");
        patient4.setEmail("thomas.roux@email.com");
        patient4.setNumeroSecuriteSociale("110053175045678");

        Patient patient5 = new Patient();
        patient5.setNom("Fontaine");
        patient5.setPrenom("Sophie");
        patient5.setDateNaissance(sdf.parse("1965-09-30"));
        patient5.setSexe("F");
        patient5.setTelephone("0656789012");
        patient5.setAdresse("33 Rue du Commerce, Nice");
        patient5.setEmail("sophie.fontaine@email.com");
        patient5.setNumeroSecuriteSociale("265093175056789");

        patientRepository.saveAll(Arrays.asList(patient1, patient2, patient3, patient4, patient5));
        System.out.println("Saved " + patientRepository.count() + " patients");

        // Create Dossiers Patients
        DossierPatient dossier1 = new DossierPatient();
        dossier1.setPatient(patient1);
        dossier1.setMedecin(emp1); // Dr. Dupont - Cardiologue
        dossier1.setDateConsultation(new Date(System.currentTimeMillis() - 7L * 24 * 60 * 60 * 1000)); // 7 days ago
        dossier1.setDiagnostic("Hypertension artérielle modérée");
        dossier1.setTraitement("Prescription d'inhibiteurs de l'ECA, régime pauvre en sel");
        dossier1.setObservations("Patiente répondant bien au traitement. Suivi mensuel recommandé.");
        dossier1.setSymptomes("Maux de tête fréquents, fatigue");
        dossier1.setTypeConsultation("Consultation de suivi");
        dossier1.setDateProchainRendezVous(new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000)); // 30 days

        DossierPatient dossier2 = new DossierPatient();
        dossier2.setPatient(patient2);
        dossier2.setMedecin(emp1); // Dr. Dupont - Cardiologue
        dossier2.setDateConsultation(new Date(System.currentTimeMillis() - 14L * 24 * 60 * 60 * 1000)); // 14 days ago
        dossier2.setDiagnostic("Insuffisance cardiaque légère");
        dossier2.setTraitement("Bêta-bloquants, diurétiques, restriction hydrique");
        dossier2.setObservations("Patient stable. Échocardiographie de contrôle dans 3 mois.");
        dossier2.setSymptomes("Essoufflement à l'effort, œdème des chevilles");
        dossier2.setTypeConsultation("Consultation initiale");
        dossier2.setDateProchainRendezVous(new Date(System.currentTimeMillis() + 90L * 24 * 60 * 60 * 1000)); // 90 days

        DossierPatient dossier3 = new DossierPatient();
        dossier3.setPatient(patient3);
        dossier3.setMedecin(emp3); // Dr. Dubois - Neurologue
        dossier3.setDateConsultation(new Date(System.currentTimeMillis() - 3L * 24 * 60 * 60 * 1000)); // 3 days ago
        dossier3.setDiagnostic("Migraine chronique");
        dossier3.setTraitement("Triptans en crise, prophylaxie par bêta-bloquants");
        dossier3.setObservations("Identification des facteurs déclenchants en cours. Tenue d'un journal des migraines conseillée.");
        dossier3.setSymptomes("Céphalées pulsatiles, photophobie, nausées");
        dossier3.setTypeConsultation("Consultation spécialisée");
        dossier3.setDateProchainRendezVous(new Date(System.currentTimeMillis() + 60L * 24 * 60 * 60 * 1000)); // 60 days

        DossierPatient dossier4 = new DossierPatient();
        dossier4.setPatient(patient4);
        dossier4.setMedecin(emp2); // Marie Martin - Infirmière pédiatrique
        dossier4.setDateConsultation(new Date(System.currentTimeMillis() - 1L * 24 * 60 * 60 * 1000)); // 1 day ago
        dossier4.setDiagnostic("Vaccination de routine - rappel DTP");
        dossier4.setTraitement("Administration du vaccin DTP");
        dossier4.setObservations("Vaccination effectuée sans complications. Enfant en bonne santé générale.");
        dossier4.setSymptomes("Aucun");
        dossier4.setTypeConsultation("Vaccination");
        dossier4.setDateProchainRendezVous(new Date(System.currentTimeMillis() + 365L * 24 * 60 * 60 * 1000)); // 1 year

        DossierPatient dossier5 = new DossierPatient();
        dossier5.setPatient(patient5);
        dossier5.setMedecin(emp5); // Dr. Garcia - Chirurgien
        dossier5.setDateConsultation(new Date(System.currentTimeMillis() - 21L * 24 * 60 * 60 * 1000)); // 21 days ago
        dossier5.setDiagnostic("Cholélithiase symptomatique");
        dossier5.setTraitement("Cholécystectomie laparoscopique programmée");
        dossier5.setObservations("Patiente candidate à la chirurgie. Bilan préopératoire complet effectué.");
        dossier5.setSymptomes("Douleurs abdominales post-prandiales, nausées");
        dossier5.setTypeConsultation("Consultation pré-opératoire");
        dossier5.setDateProchainRendezVous(new Date(System.currentTimeMillis() + 14L * 24 * 60 * 60 * 1000)); // 14 days

        dossierPatientRepository.saveAll(Arrays.asList(dossier1, dossier2, dossier3, dossier4, dossier5));
        System.out.println("Saved " + dossierPatientRepository.count() + " dossiers patients");

        // Link patients to projects
        patient1.setProjets(Arrays.asList(projet1, projet7)); // Cardio projects
        patient2.setProjets(Arrays.asList(projet1)); // Cardio project
        patient3.setProjets(Arrays.asList(projet2, projet8)); // Neuro projects
        patient4.setProjets(Arrays.asList(projet3)); // Pediatrie project
        patient5.setProjets(Arrays.asList(projet4)); // Chirurgie project

        projet1.setPatients(Arrays.asList(patient1, patient2));
        projet7.setPatients(Arrays.asList(patient1));
        projet2.setPatients(Arrays.asList(patient3));
        projet8.setPatients(Arrays.asList(patient3));
        projet3.setPatients(Arrays.asList(patient4));
        projet4.setPatients(Arrays.asList(patient5));

        // Set progression for projects
        projet1.setProgression(45.0);
        projet1.setPriorite("Haute");
        projet2.setProgression(20.0);
        projet2.setPriorite("Moyenne");
        projet3.setProgression(75.0);
        projet3.setPriorite("Haute");
        projet4.setProgression(30.0);
        projet4.setPriorite("Haute");
        projet5.setProgression(60.0);
        projet5.setPriorite("Moyenne");
        projet6.setProgression(10.0);
        projet6.setPriorite("Basse");
        projet7.setProgression(85.0);
        projet7.setPriorite("Haute");
        projet8.setProgression(40.0);
        projet8.setPriorite("Moyenne");

        patientRepository.saveAll(Arrays.asList(patient1, patient2, patient3, patient4, patient5));
        projetRepository.saveAll(Arrays.asList(projet1, projet2, projet3, projet4, projet5, projet6, projet7, projet8));
        System.out.println("Updated patient-project relationships and project progression");

        System.out.println("\n✅ Data seeding completed successfully for MySQL database 'GestionHopital'!");
        System.out.println("📊 Summary:");
        System.out.println("   - Departements: " + departementRepository.count());
        System.out.println("   - Employes: " + employeRepository.count());
        System.out.println("   - Patients: " + patientRepository.count());
        System.out.println("   - Projets: " + projetRepository.count());
        System.out.println("   - Dossiers Patients: " + dossierPatientRepository.count());
    }
}