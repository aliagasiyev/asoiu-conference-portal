package az.edu.asiouconferenceportal.config;

import az.edu.asiouconferenceportal.entity.user.Role;
import az.edu.asiouconferenceportal.entity.user.User;
import az.edu.asiouconferenceportal.repository.user.RoleRepository;
import az.edu.asiouconferenceportal.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.bootstrap.admin.enabled:false}")
    private boolean bootstrapEnabled;

    @Value("${app.bootstrap.admin.email:}")
    private String adminEmail;

    @Value("${app.bootstrap.admin.password:}")
    private String adminPassword;

    @Value("${app.bootstrap.admin.firstName:System}")
    private String adminFirstName;

    @Value("${app.bootstrap.admin.lastName:Admin}")
    private String adminLastName;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        log.info("Starting data initialization process. Bootstrap enabled: {}", bootstrapEnabled);

        Role adminRole = ensureRole("ADMIN");
        ensureRole("USER");
        ensureRole("REVIEWER");

        if (!bootstrapEnabled) {
            log.info("Bootstrap admin is disabled. Skipping admin seeding.");
            return;
        }

        validateBootstrapConfiguration();
        bootstrapAdmin(adminRole);

        log.info("Data initialization process finished.");
    }

    private void validateBootstrapConfiguration() {
        if (adminEmail == null || adminEmail.isBlank()) {
            throw new IllegalStateException("BOOTSTRAP_ADMIN_EMAIL must be provided when bootstrap admin is enabled.");
        }

        if (adminPassword == null || adminPassword.isBlank()) {
            throw new IllegalStateException(
                    "BOOTSTRAP_ADMIN_PASSWORD must be provided when bootstrap admin is enabled.");
        }
    }

    private void bootstrapAdmin(Role adminRole) {
        User admin = userRepository.findByEmail(adminEmail).orElse(null);

        if (admin == null) {
            log.info("Admin user not found. Creating bootstrap admin with email: {}", adminEmail);

            admin = new User();
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setFirstName(adminFirstName);
            admin.setLastName(adminLastName);
            admin.getRoles().add(adminRole);

            userRepository.save(admin);
            log.info("Bootstrap admin created successfully.");
            return;
        }

        log.info("Admin user already exists. Ensuring ADMIN role is present.");

        if (!admin.getRoles().contains(adminRole)) {
            admin.getRoles().add(adminRole);
            userRepository.save(admin);
            log.info("ADMIN role added to existing user: {}", adminEmail);
        } else {
            log.info("Existing admin already has ADMIN role. No password overwrite performed.");
        }
    }

    private Role ensureRole(String name) {
        return roleRepository.findByName(name).orElseGet(() -> {
            log.info("Role {} not found. Creating it.", name);
            Role role = new Role();
            role.setName(name);
            return roleRepository.save(role);
        });
    }
}