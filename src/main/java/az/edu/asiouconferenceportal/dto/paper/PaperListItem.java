package az.edu.asiouconferenceportal.dto.paper;

import az.edu.asiouconferenceportal.common.enums.PaperStatus;
import lombok.Data;

@Data
public class PaperListItem {
	private Long id;
	private String title;
	private PaperStatus status;
	private String paperType;
}
