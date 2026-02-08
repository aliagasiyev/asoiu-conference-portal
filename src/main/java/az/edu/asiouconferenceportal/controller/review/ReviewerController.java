package az.edu.asiouconferenceportal.controller.review;

import az.edu.asiouconferenceportal.dto.review.AssignmentResponse;
import az.edu.asiouconferenceportal.dto.review.ReviewSubmitRequest;
import az.edu.asiouconferenceportal.dto.paper.PaperResponse;
import az.edu.asiouconferenceportal.service.review.ReviewService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviewer")
@RequiredArgsConstructor
@PreAuthorize("hasRole('REVIEWER')")
public class ReviewerController {

    private final ReviewService reviewService;

    @GetMapping("/assignments")
    public List<AssignmentResponse> myAssignments() {
        return reviewService.listAssignmentsForMe();
    }

    @PostMapping("/assignments/{assignmentId}/accept")
    public AssignmentResponse accept(@PathVariable Long assignmentId) {
        return reviewService.acceptAssignment(assignmentId);
    }

    @PostMapping("/assignments/{assignmentId}/review")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void submit(@PathVariable Long assignmentId, @Valid @RequestBody ReviewSubmitRequest request) {
        reviewService.submitReview(assignmentId, request);
    }

    @GetMapping("/assignments/{assignmentId}/paper")
    public PaperResponse paper(@PathVariable Long assignmentId) {
        return reviewService.getAssignedPaper(assignmentId);
    }

    @GetMapping("/papers")
    public java.util.List<PaperResponse> myAssignedPapers() {
        return reviewService.listAssignedPapers();
    }

    @GetMapping("/papers/{paperId}")
    public PaperResponse paperById(@PathVariable Long paperId) {
        return reviewService.getAssignedPaperByPaperId(paperId);
    }
}


