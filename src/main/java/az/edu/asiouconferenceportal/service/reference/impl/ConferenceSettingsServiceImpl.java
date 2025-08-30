package az.edu.asiouconferenceportal.service.reference.impl;

import az.edu.asiouconferenceportal.dto.reference.ConferenceSettingsRequest;
import az.edu.asiouconferenceportal.dto.reference.ConferenceSettingsResponse;
import az.edu.asiouconferenceportal.entity.reference.ConferenceSettings;
import az.edu.asiouconferenceportal.repository.reference.ConferenceSettingsRepository;
import az.edu.asiouconferenceportal.service.reference.ConferenceSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConferenceSettingsServiceImpl implements ConferenceSettingsService {

	private final ConferenceSettingsRepository repository;

	@Override
	@Transactional
	public ConferenceSettingsResponse get() {
		ConferenceSettings s = repository.findAll().stream().findFirst().orElse(null);
		if (s == null) {
			s = repository.save(new ConferenceSettings());
		}
		return toResponse(s);
	}

	@Override
	@Transactional
	public ConferenceSettingsResponse update(ConferenceSettingsRequest request) {
		ConferenceSettings s = repository.findAll().stream().findFirst().orElseGet(() -> repository.save(new ConferenceSettings()));
		if (request.getConferenceName() != null) s.setConferenceName(request.getConferenceName());
		if (request.getSubmissionsOpen() != null) s.setSubmissionsOpen(request.getSubmissionsOpen());
		if (request.getCameraReadyOpen() != null) s.setCameraReadyOpen(request.getCameraReadyOpen());
		return toResponse(s);
	}

	private ConferenceSettingsResponse toResponse(ConferenceSettings s) {
		ConferenceSettingsResponse r = new ConferenceSettingsResponse();
		r.setId(s.getId());
		r.setConferenceName(s.getConferenceName());
		r.setSubmissionsOpen(s.isSubmissionsOpen());
		r.setCameraReadyOpen(s.isCameraReadyOpen());
		return r;
	}

    @Scheduled(cron = "0 0 * * * *")
    public void hourlyTick() {
    }
}


