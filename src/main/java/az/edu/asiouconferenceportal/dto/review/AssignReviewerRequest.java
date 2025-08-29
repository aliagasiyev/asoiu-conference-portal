package az.edu.asiouconferenceportal.dto.review;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import lombok.Data;

@Data
public class AssignReviewerRequest {
    @NotBlank
    @Email
    private String reviewerEmail;

    @NotNull
    @Future
    private Instant dueAt;
}


