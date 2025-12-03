package com.isetdjerba.hopital.suivi_medical.config;

import com.isetdjerba.hopital.suivi_medical.tokenisation.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfig(@Lazy JwtFilter jwtFilter, CorsConfigurationSource corsConfigurationSource) {
        this.jwtFilter = jwtFilter;
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Enable CORS with our custom configuration
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                // Disable CSRF for stateless JWT authentication
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers("/auth/**").permitAll()
                        
                        // All authenticated users can read these resources
                        .requestMatchers("/departements/**", "/employes/**").hasAnyRole("ADMIN", "RESPONSABLE_PROJET", "DOCTEUR", "INFIRMIER", "EMPLOYE")
                        
                        // Projects - all can read, ADMIN and RESPONSABLE_PROJET can modify
                        .requestMatchers("/projets/**").hasAnyRole("ADMIN", "RESPONSABLE_PROJET", "DOCTEUR", "INFIRMIER", "EMPLOYE")
                        
                        // Patients - all can read, ADMIN can modify
                        .requestMatchers("/patients/**").hasAnyRole("ADMIN", "RESPONSABLE_PROJET", "DOCTEUR", "INFIRMIER", "EMPLOYE")
                        
                        // Dossiers - medical staff can read, ADMIN/DOCTEUR/INFIRMIER can modify
                        .requestMatchers("/dossiers/**").hasAnyRole("ADMIN", "RESPONSABLE_PROJET", "DOCTEUR", "INFIRMIER")
                        
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
