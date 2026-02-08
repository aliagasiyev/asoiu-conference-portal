package az.edu.asiouconferenceportal.service.review;

import az.edu.asiouconferenceportal.dto.review.AssignReviewerRequest;
import az.edu.asiouconferenceportal.dto.review.AssignmentResponse;
import az.edu.asiouconferenceportal.dto.review.ReviewSubmitRequest;
import az.edu.asiouconferenceportal.dto.paper.PaperResponse;
import java.util.List;

public interface ReviewService {
    AssignmentResponse assignReviewer(Long paperId, AssignReviewerRequest request);
    List<AssignmentResponse> listAssignmentsForMe();
    AssignmentResponse acceptAssignment(Long assignmentId);
    void submitReview(Long assignmentId, ReviewSubmitRequest request);
    List<AssignmentResponse> listAssignmentsByPaper(Long paperId);
    PaperResponse getAssignedPaper(Long assignmentId);
    List<PaperResponse> listAssignedPapers();
    PaperResponse getAssignedPaperByPaperId(Long paperId);
}


