package az.edu.asiouconferenceportal.dto.review;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import lombok.Data;

@Data
public class AssignReviewerRequest {
    @NotNull
    private Long reviewerId;

    @NotNull
    @Future
    private Instant dueAt;
}


