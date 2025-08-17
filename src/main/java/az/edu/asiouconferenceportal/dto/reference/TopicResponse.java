package az.edu.asiouconferenceportal.dto.reference;

import lombok.Data;

@Data
public class TopicResponse {
	private Long id;
	private String name;
	private boolean active;
	private Integer orderIndex;
}
