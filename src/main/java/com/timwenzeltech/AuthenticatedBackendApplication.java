package com.timwenzeltech;

import java.util.HashSet;
import java.util.Set;

import com.timwenzeltech.models.ApplicationUser;
import com.timwenzeltech.models.Role;
import com.timwenzeltech.repository.RoleRepository;
import com.timwenzeltech.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AuthenticatedBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticatedBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            if (roleRepository.findByAuthority("ADMIN").isPresent())
                return;
            Role adminRole = roleRepository.save(new Role("ADMIN"));
            roleRepository.save(adminRole);
            roleRepository.save(new Role("USER"));

            Set<Role> roles = new HashSet<>();

            roles.add(adminRole);

            ApplicationUser admin = new ApplicationUser(1, "admin", passwordEncoder.encode("password"), roles);

            userRepository.save(admin);
        };
    }

}
