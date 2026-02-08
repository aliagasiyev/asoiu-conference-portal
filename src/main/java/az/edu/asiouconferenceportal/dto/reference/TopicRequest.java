package az.edu.asiouconferenceportal.dto.reference;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TopicRequest {
	@NotBlank
	private String name;
	private Boolean active;
	private Integer orderIndex;
}
