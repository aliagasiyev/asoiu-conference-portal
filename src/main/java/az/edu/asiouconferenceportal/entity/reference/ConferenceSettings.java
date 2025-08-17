package az.edu.asiouconferenceportal.entity.reference;

import az.edu.asiouconferenceportal.entity.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "conference_settings")
@Data
@EqualsAndHashCode(callSuper = true)
public class ConferenceSettings extends BaseEntity {

	@Column(nullable = false)
	private String conferenceName = "Conference";

	@Column(nullable = false)
	private boolean submissionsOpen = true;

	@Column(nullable = false)
	private boolean cameraReadyOpen = false;
}
