package az.edu.asiouconferenceportal.controller.auth;

import az.edu.asiouconferenceportal.dto.auth.JwtResponse;
import az.edu.asiouconferenceportal.dto.auth.LoginRequest;
import az.edu.asiouconferenceportal.security.JwtService;
import az.edu.asiouconferenceportal.service.notification.EmailService;
import az.edu.asiouconferenceportal.dto.auth.RegisterRequest;
import az.edu.asiouconferenceportal.dto.auth.ForgotPasswordRequest;
import az.edu.asiouconferenceportal.dto.auth.ResetPasswordRequest;
import az.edu.asiouconferenceportal.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
    private final AuthService authService;
    private final EmailService emailService;

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
		return ResponseEntity.ok(authService.login(request));
	}

	@PostMapping("/register")
	public ResponseEntity<JwtResponse> register(@Valid @RequestBody RegisterRequest request) {
		return ResponseEntity.ok(authService.register(request));
	}

	@PostMapping("/forgot-password")
	public ResponseEntity<Void> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
		authService.forgotPassword(request);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/reset-password")
	public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
		authService.resetPassword(request);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/send-test-mail")
	public ResponseEntity<String> sendTest(@RequestBody String to) {
		emailService.sendEmail(to, "Test Mail", "This is a test email.");
		return ResponseEntity.ok("Test mail sent to " + to);
	}
}
