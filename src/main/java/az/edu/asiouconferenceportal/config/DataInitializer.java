package az.edu.asiouconferenceportal.config;

import az.edu.asiouconferenceportal.entity.user.Role;
import az.edu.asiouconferenceportal.entity.user.User;
import az.edu.asiouconferenceportal.repository.user.RoleRepository;
import az.edu.asiouconferenceportal.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void run(ApplicationArguments args) {
		Role adminRole = roleRepository.findByName("ADMIN").orElseGet(() -> {
			Role r = new Role();
			r.setName("ADMIN");
			return roleRepository.save(r);
		});

		if (!userRepository.existsByEmail("admin@asiou.az")) {
			User u = new User();
			u.setEmail("admin@asiou.az");
			u.setPassword(passwordEncoder.encode("admin123"));
			u.setFirstName("System");
			u.setLastName("Admin");
			u.getRoles().add(adminRole);
			userRepository.save(u);
		}
	}
}


