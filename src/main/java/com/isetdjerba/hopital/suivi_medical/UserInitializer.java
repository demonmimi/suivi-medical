package com.isetdjerba.hopital.suivi_medical;

import com.isetdjerba.hopital.suivi_medical.security.AppRole;
import com.isetdjerba.hopital.suivi_medical.security.AppUser;
import com.isetdjerba.hopital.suivi_medical.security.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserInitializer {

    @Bean
    CommandLineRunner initUsers(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Check if users already exist
            if (userRepository.count() > 0) {
                System.out.println("Users already initialized. Current users:");
                userRepository.findAll().forEach(user -> 
                    System.out.println("  - " + user.getUsername() + " (Role: " + user.getRole() + ")")
                );
                return;
            }

            System.out.println("Initializing test users...");

            // Create ADMIN user
            AppUser admin = new AppUser();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(AppRole.ADMIN);
            userRepository.save(admin);

            // Create RESPONSABLE_PROJET user
            AppUser responsable = new AppUser();
            responsable.setUsername("responsable");
            responsable.setPassword(passwordEncoder.encode("resp123"));
            responsable.setRole(AppRole.RESPONSABLE_PROJET);
            userRepository.save(responsable);

            // Create DOCTEUR user
            AppUser docteur = new AppUser();
            docteur.setUsername("docteur");
            docteur.setPassword(passwordEncoder.encode("doc123"));
            docteur.setRole(AppRole.DOCTEUR);
            userRepository.save(docteur);

            // Create INFIRMIER user
            AppUser infirmier = new AppUser();
            infirmier.setUsername("infirmier");
            infirmier.setPassword(passwordEncoder.encode("inf123"));
            infirmier.setRole(AppRole.INFIRMIER);
            userRepository.save(infirmier);

            System.out.println("\n=== TEST USERS CREATED ===");
            System.out.println("Username: admin      | Password: admin123 | Role: ADMIN");
            System.out.println("Username: responsable| Password: resp123  | Role: RESPONSABLE_PROJET");
            System.out.println("Username: docteur    | Password: doc123   | Role: DOCTEUR");
            System.out.println("Username: infirmier  | Password: inf123   | Role: INFIRMIER");
            System.out.println("==========================\n");
        };
    }
}
