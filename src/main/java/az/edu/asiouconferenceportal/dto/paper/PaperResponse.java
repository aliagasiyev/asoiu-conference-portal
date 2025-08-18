package az.edu.asiouconferenceportal.dto.paper;

import az.edu.asiouconferenceportal.common.enums.PaperStatus;
import java.util.List;
import lombok.Data;

@Data
public class PaperResponse {
	private Long id;
	private String title;
	private String keywords;
	private String paperAbstract;
	private PaperStatus status;
	private String paperType;
	private List<String> topics;
	private List<CoAuthorRequest> coAuthors;
	private Long fileId;
	private Long cameraReadyFileId;
}
