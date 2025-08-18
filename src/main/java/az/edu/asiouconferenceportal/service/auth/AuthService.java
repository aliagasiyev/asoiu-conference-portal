package az.edu.asiouconferenceportal.service.auth;

import az.edu.asiouconferenceportal.dto.auth.ForgotPasswordRequest;
import az.edu.asiouconferenceportal.dto.auth.JwtResponse;
import az.edu.asiouconferenceportal.dto.auth.LoginRequest;
import az.edu.asiouconferenceportal.dto.auth.RegisterRequest;
import az.edu.asiouconferenceportal.dto.auth.ResetPasswordRequest;

public interface AuthService {
	JwtResponse login(LoginRequest request);
	JwtResponse register(RegisterRequest request);
	void forgotPassword(ForgotPasswordRequest request);
	void resetPassword(ResetPasswordRequest request);
}


