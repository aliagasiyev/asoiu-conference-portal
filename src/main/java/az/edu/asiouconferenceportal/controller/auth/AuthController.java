package az.edu.asiouconferenceportal.controller.auth;

import az.edu.asiouconferenceportal.dto.auth.JwtResponse;
import az.edu.asiouconferenceportal.dto.auth.LoginRequest;
import az.edu.asiouconferenceportal.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		UserDetails principal = (UserDetails) authentication.getPrincipal();
		String token = jwtService.generateToken(principal);
		JwtResponse response = new JwtResponse();
		response.setAccessToken(token);
		return ResponseEntity.ok(response);
	}
}
