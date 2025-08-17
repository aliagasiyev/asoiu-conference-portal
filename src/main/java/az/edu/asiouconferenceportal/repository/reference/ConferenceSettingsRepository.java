package az.edu.asiouconferenceportal.repository.reference;

import az.edu.asiouconferenceportal.entity.reference.ConferenceSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConferenceSettingsRepository extends JpaRepository<ConferenceSettings, Long> { }
