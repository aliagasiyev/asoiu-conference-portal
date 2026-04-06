package az.edu.asiouconferenceportal.dto.paper;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

import lombok.Data;

@Data
public class PaperUpdateRequest {
    @NotBlank
    @jakarta.validation.constraints.Size(max = 255)
    private String title;
    @NotBlank
    @jakarta.validation.constraints.Size(max = 255)
    private String keywords;
    @NotBlank
    @jakarta.validation.constraints.Size(max = 4000)
    private String paperAbstract;
    @NotNull
    private Long paperTypeId;
    @NotNull
    @jakarta.validation.constraints.NotEmpty
    private List<Long> topicIds;
    @jakarta.validation.Valid
    private List<CoAuthorRequest> coAuthors;
}
