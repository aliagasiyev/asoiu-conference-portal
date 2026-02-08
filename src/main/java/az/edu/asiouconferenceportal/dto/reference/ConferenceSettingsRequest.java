package az.edu.asiouconferenceportal.dto.reference;

import lombok.Data;

@Data
public class ConferenceSettingsRequest {
	private String conferenceName;
	private Boolean submissionsOpen;
	private Boolean cameraReadyOpen;
}
