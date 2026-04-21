package az.edu.asiouconferenceportal.dto.review;

import az.edu.asiouconferenceportal.common.enums.ReviewDecision;
import lombok.Data;

@Data
public class ReviewViewResponse {
    private Long id;
    private ReviewDecision decision;
    private String comments;
    private String adminFeedback;
    private java.time.Instant adminFeedbackAt;
}


