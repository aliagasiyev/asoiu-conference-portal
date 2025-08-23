package az.edu.asiouconferenceportal.service.auth;

import az.edu.asiouconferenceportal.dto.auth.JwtResponse;
import az.edu.asiouconferenceportal.dto.auth.LoginRequest;
import az.edu.asiouconferenceportal.dto.auth.RegisterRequest;

public interface AuthService {
    JwtResponse login(LoginRequest request);

    JwtResponse register(RegisterRequest request);
}


