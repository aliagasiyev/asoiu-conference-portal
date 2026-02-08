package az.edu.asiouconferenceportal.dto.review;

import java.time.Instant;
import lombok.Data;

@Data
public class AssignmentResponse {
    private Long id;
    private Long paperId;
    private String paperTitle;
    private Instant dueAt;
    private Instant acceptedAt;
    private Boolean completed;
    private Boolean dueSoon;
}


