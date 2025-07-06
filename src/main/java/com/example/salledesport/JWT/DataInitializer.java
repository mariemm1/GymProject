package com.example.salledesport.JWT;

import com.example.salledesport.model.Admin;
import com.example.salledesport.model.ERole;
import com.example.salledesport.model.Role;
import com.example.salledesport.repositories.AdminRepository;
import com.example.salledesport.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initAdmin(
            AdminRepository adminRepository,
            RoleRepository roleRepository,
            PasswordEncoder encoder
    ) {
        return args -> {
            String email = "superadmin@superadmin.com";

            if (adminRepository.findByEmail(email).isEmpty()) {
                Admin superAdmin = new Admin();
                superAdmin.setFirst_name("Super");
                superAdmin.setLast_name("Admin");
                superAdmin.setUsername("superadmin");
                superAdmin.setEmail(email);
                superAdmin.setPassword(encoder.encode("superadmin123"));

                // Set ROLE_ADMIN
                Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role ADMIN not found."));
                Set<Role> roles = new HashSet<>();
                roles.add(adminRole);
                superAdmin.setRoles(roles);

                adminRepository.save(superAdmin);

                System.out.println("✅ SuperAdmin created successfully.");
            } else {
                System.out.println("ℹ️ SuperAdmin already exists, skipping creation.");
            }
        };
    }
}
