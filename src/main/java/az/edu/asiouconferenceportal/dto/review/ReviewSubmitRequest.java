package az.edu.asiouconferenceportal.dto.review;

import az.edu.asiouconferenceportal.common.enums.ReviewDecision;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewSubmitRequest {
    @NotNull
    private ReviewDecision decision;

    @NotBlank
    private String comments;
}


