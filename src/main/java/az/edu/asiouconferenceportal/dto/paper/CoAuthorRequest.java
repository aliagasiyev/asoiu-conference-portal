package az.edu.asiouconferenceportal.dto.paper;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CoAuthorRequest {
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	@Email
	@NotBlank
	private String email;
	private String affiliation;
	private String position;
	private String country;
	private String city;
}
