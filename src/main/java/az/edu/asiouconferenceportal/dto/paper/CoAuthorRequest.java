package az.edu.asiouconferenceportal.dto.paper;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CoAuthorRequest {
	@NotBlank
	@jakarta.validation.constraints.Size(max = 100)
	private String firstName;
	@NotBlank
	@jakarta.validation.constraints.Size(max = 100)
	private String lastName;
	@Email
	@NotBlank
	@jakarta.validation.constraints.Size(max = 128)
	private String email;
	@jakarta.validation.constraints.Size(max = 255)
	private String affiliation;
	@jakarta.validation.constraints.Size(max = 100)
	private String position;
	@jakarta.validation.constraints.Size(max = 100)
	private String country;
	@jakarta.validation.constraints.Size(max = 100)
	private String city;
}
