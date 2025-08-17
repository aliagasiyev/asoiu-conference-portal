package az.edu.asiouconferenceportal.service.reference;

import az.edu.asiouconferenceportal.dto.reference.ConferenceSettingsRequest;
import az.edu.asiouconferenceportal.dto.reference.ConferenceSettingsResponse;

public interface ConferenceSettingsService {
	ConferenceSettingsResponse get();
	ConferenceSettingsResponse update(ConferenceSettingsRequest request);
}
