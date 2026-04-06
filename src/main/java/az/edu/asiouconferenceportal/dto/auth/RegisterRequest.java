package az.edu.asiouconferenceportal.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
	@NotBlank 
	@Email
	@jakarta.validation.constraints.Size(max = 128)
	private String email;

	@NotBlank
	@jakarta.validation.constraints.Size(min = 8, max = 64)
	@jakarta.validation.constraints.Pattern(
			regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
			message = "Password must contain at least one digit, one lowercase, one uppercase, one special character, and no whitespace"
	)
	private String password;

	@NotBlank
	@jakarta.validation.constraints.Size(max = 100)
	private String firstName;

	@NotBlank
	@jakarta.validation.constraints.Size(max = 100)
	private String lastName;
}


