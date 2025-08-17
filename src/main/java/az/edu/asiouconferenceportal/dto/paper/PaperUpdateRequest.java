package az.edu.asiouconferenceportal.dto.paper;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;

@Data
public class PaperUpdateRequest {
	@NotBlank
	private String title;
	@NotBlank
	private String keywords;
	@NotBlank
	private String paperAbstract;
	@NotNull
	private Long paperTypeId;
	@NotNull
	private List<Long> topicIds;
	private List<CoAuthorRequest> coAuthors;
}
