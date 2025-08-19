package az.edu.asiouconferenceportal.service.auth.impl;

import az.edu.asiouconferenceportal.dto.auth.*;
import az.edu.asiouconferenceportal.entity.user.Role;
import az.edu.asiouconferenceportal.entity.user.User;
import az.edu.asiouconferenceportal.repository.user.RoleRepository;
import az.edu.asiouconferenceportal.repository.user.UserRepository;
import az.edu.asiouconferenceportal.security.JwtService;
import az.edu.asiouconferenceportal.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public JwtResponse login(LoginRequest request) {
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		UserDetails principal = (UserDetails) authentication.getPrincipal();
		String token = jwtService.generateToken(principal);
		JwtResponse response = new JwtResponse();
		response.setAccessToken(token);
		return response;
	}

	@Override
	public JwtResponse register(RegisterRequest request) {
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new IllegalArgumentException("Email already in use");
		}
		Role userRole = roleRepository.findByName("USER").orElseGet(() -> {
			Role r = new Role();
			r.setName("USER");
			return roleRepository.save(r);
		});
		User u = new User();
		u.setEmail(request.getEmail());
		u.setPassword(passwordEncoder.encode(request.getPassword()));
		u.setFirstName(request.getFirstName());
		u.setLastName(request.getLastName());
		u.getRoles().add(userRole);
		userRepository.save(u);
		var principal = org.springframework.security.core.userdetails.User
			.withUsername(u.getEmail()).password(u.getPassword()).roles("USER").build();
		String token = jwtService.generateToken(principal);
		JwtResponse response = new JwtResponse();
		response.setAccessToken(token);
		return response;
	}
}


