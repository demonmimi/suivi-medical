package com.isetdjerba.hopital.suivi_medical.controller;

import com.isetdjerba.hopital.suivi_medical.security.AppRole;
import com.isetdjerba.hopital.suivi_medical.security.AppUser;
import com.isetdjerba.hopital.suivi_medical.security.AppUserService;
import com.isetdjerba.hopital.suivi_medical.tokenisation.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AppUserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager,
                         AppUserService userService,
                         JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String password = request.get("password");
            String roleStr = request.get("role");

            AppRole role = AppRole.valueOf(roleStr.toUpperCase());
            AppUser user = userService.registerUser(username, password, role);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "User registered successfully");
            response.put("username", user.getUsername());
            response.put("role", user.getRole());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );

            UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
            String jwt = jwtUtil.generateToken(userDetails);

            Map<String, Object> response = new HashMap<>();
            response.put("token", jwt);
            response.put("username", userDetails.getUsername());
            response.put("roles", userDetails.getAuthorities());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Authentication failed: " + e.getMessage());
        }
    }
}
