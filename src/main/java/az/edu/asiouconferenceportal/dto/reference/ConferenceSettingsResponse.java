package az.edu.asiouconferenceportal.dto.reference;

import lombok.Data;

@Data
public class ConferenceSettingsResponse {
	private Long id;
	private String conferenceName;
	private boolean submissionsOpen;
	private boolean cameraReadyOpen;
}
