package az.edu.asiouconferenceportal.controller.admin;

import az.edu.asiouconferenceportal.dto.review.AssignReviewerRequest;
import az.edu.asiouconferenceportal.dto.review.AssignmentResponse;
import az.edu.asiouconferenceportal.service.review.ReviewService;
import az.edu.asiouconferenceportal.repository.paper.PaperReviewRepository;
import az.edu.asiouconferenceportal.entity.paper.PaperReview;
import az.edu.asiouconferenceportal.dto.review.ReviewViewResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/reviews")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminReviewController {

    private final ReviewService reviewService;
    private final PaperReviewRepository paperReviewRepository;

    @PostMapping("/papers/{paperId}/assign")
    @ResponseStatus(HttpStatus.CREATED)
    public AssignmentResponse assign(@PathVariable Long paperId, @Valid @RequestBody AssignReviewerRequest request) {
        return reviewService.assignReviewer(paperId, request);
    }

    @GetMapping("/papers/{paperId}/assignments")
    public List<AssignmentResponse> listByPaper(@PathVariable Long paperId) {
        return reviewService.listAssignmentsByPaper(paperId);
    }

    @GetMapping("/papers/{paperId}/reviews")
    public List<ReviewViewResponse> listReviews(@PathVariable Long paperId) {
        return paperReviewRepository.findAllByAssignment_Paper_Id(paperId).stream().map(r -> {
            ReviewViewResponse x = new ReviewViewResponse();
            x.setId(r.getId());
            x.setDecision(r.getDecision());
            x.setComments(r.getComments());
            return x;
        }).toList();
    }
}


