package az.edu.asiouconferenceportal.controller.user;

import az.edu.asiouconferenceportal.repository.user.UserRepository;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/me")
@RequiredArgsConstructor
public class UserController {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@GetMapping
	public String me(@AuthenticationPrincipal UserDetails user) {
		return user != null ? user.getUsername() : "anonymous";
	}

	@PutMapping("/password")
	public ResponseEntity<Void> changePassword(@AuthenticationPrincipal UserDetails user,
			@RequestBody ChangePasswordBody body) {
		var u = userRepository.findByEmail(user.getUsername()).orElseThrow();
		u.setPassword(passwordEncoder.encode(body.newPassword()));
		return ResponseEntity.noContent().build();
	}

	public record ChangePasswordBody(@NotBlank String newPassword) {}
}
