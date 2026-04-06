package az.edu.asiouconferenceportal.config;

import az.edu.asiouconferenceportal.entity.user.Role;
import az.edu.asiouconferenceportal.entity.user.User;
import az.edu.asiouconferenceportal.repository.user.RoleRepository;
import az.edu.asiouconferenceportal.repository.user.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.bootstrap.admin.enabled:false}")
    private boolean bootstrapEnabled;

    @Value("${app.bootstrap.admin.email}")
    private String adminEmail;

    @Value("${app.bootstrap.admin.password}")
    private String adminPassword;

    @Value("${app.bootstrap.admin.firstName}")
    private String adminFirstName;

    @Value("${app.bootstrap.admin.lastName}")
    private String adminLastName;

    @PostConstruct
    public void validate() {
        log.info("Checking bootstrap configuration. Enabled: {}", bootstrapEnabled);
        if (bootstrapEnabled) {
            if (adminEmail == null || adminEmail.isBlank() || adminEmail.contains("placeholder")) {
                throw new IllegalStateException("Bootstrap admin email (BOOTSTRAP_ADMIN_EMAIL) must be provided when enabled and cannot be the placeholder");
            }
            if (adminPassword == null || adminPassword.isBlank() || adminPassword.contains("placeholder")) {
                throw new IllegalStateException("Bootstrap admin password (BOOTSTRAP_ADMIN_PASSWORD) must be provided when enabled and cannot be the placeholder");
            }
        }
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        log.info("Starting data initialization process (Bootstrap Enabled: {})", bootstrapEnabled);
        
        // Ensure core roles exist
        Role adminRole = ensureRole("ADMIN");
        ensureRole("USER");
        ensureRole("REVIEWER");

        if (bootstrapEnabled) {
            log.info("Triggering admin bootstrap with email: {}", adminEmail);
            bootstrapAdmin(adminRole);
        } else {
            log.info("Bootstrap skipped because enabled flag is false.");
        }
        
        log.info("Data initialization process finished.");
    }

    private void bootstrapAdmin(Role adminRole) {
        User admin = userRepository.findByEmail(adminEmail).orElseGet(() -> {
            log.info("Admin user not found. Creating new admin: {}", adminEmail);
            User u = new User();
            u.setEmail(adminEmail);
            return u;
        });

        log.info("Updating/Setting admin password and roles for: {}", adminEmail);
        admin.setPassword(passwordEncoder.encode(adminPassword));
        admin.setFirstName(adminFirstName);
        admin.setLastName(adminLastName);
        admin.getRoles().add(adminRole);
        userRepository.save(admin);
        log.info("Admin user successfully bootstrapped/updated.");
    }

    private Role ensureRole(String name) {
        return roleRepository.findByName(name).orElseGet(() -> {
            log.info("Role {} not found. Creating...", name);
            Role role = new Role();
            role.setName(name);
            return roleRepository.save(role);
        });
    }
}
