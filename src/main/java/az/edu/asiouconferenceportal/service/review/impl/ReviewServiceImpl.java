package az.edu.asiouconferenceportal.service.review.impl;

import az.edu.asiouconferenceportal.common.enums.PaperStatus;
import az.edu.asiouconferenceportal.dto.review.AssignReviewerRequest;
import az.edu.asiouconferenceportal.dto.review.AssignmentResponse;
import az.edu.asiouconferenceportal.dto.review.ReviewSubmitRequest;
import az.edu.asiouconferenceportal.common.enums.ActivityAction;
import az.edu.asiouconferenceportal.service.common.ActivityService;
import az.edu.asiouconferenceportal.entity.paper.PaperReview;
import az.edu.asiouconferenceportal.entity.paper.ReviewAssignment;
import az.edu.asiouconferenceportal.exception.NotFoundException;
import az.edu.asiouconferenceportal.repository.paper.PaperRepository;
import az.edu.asiouconferenceportal.repository.paper.PaperReviewRepository;
import az.edu.asiouconferenceportal.repository.paper.ReviewAssignmentRepository;
import az.edu.asiouconferenceportal.repository.user.UserRepository;
import az.edu.asiouconferenceportal.service.review.ReviewService;
import az.edu.asiouconferenceportal.dto.paper.PaperResponse;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final PaperRepository paperRepository;
    private final ReviewAssignmentRepository assignmentRepository;
    private final PaperReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ActivityService activityService;

    @Override
    @Transactional
    public AssignmentResponse assignReviewer(Long paperId, AssignReviewerRequest request) {
        var paper = paperRepository.findById(paperId).orElseThrow(() -> new NotFoundException("Paper not found"));
        var reviewer = userRepository.findByEmail(request.getReviewerEmail())
            .orElseThrow(() -> new NotFoundException("Reviewer not found for email: " + request.getReviewerEmail()));
        // ensure reviewer has REVIEWER role
        boolean isReviewer = reviewer.getRoles().stream().anyMatch(r -> "REVIEWER".equalsIgnoreCase(r.getName()));
        if (!isReviewer) {
            throw new NotFoundException("User is not a reviewer: " + request.getReviewerEmail());
        }
        var assignment = new ReviewAssignment();
        assignment.setPaper(paper);
        assignment.setReviewer(reviewer);
        assignment.setDueAt(request.getDueAt());
        assignment = assignmentRepository.save(assignment);
        paper.setStatus(PaperStatus.UNDER_REVIEW);
        paperRepository.save(paper);
        activityService.log(ActivityAction.REVIEW_ASSIGNED, "PAPER", paper.getId(), "Assigned to " + reviewer.getEmail());
        return toResponse(assignment);
    }

    @Override
    public List<AssignmentResponse> listAssignmentsForMe() {
        var current = currentUserEmail();
        var user = userRepository.findByEmail(current).orElseThrow(() -> new NotFoundException("User not found"));
        return assignmentRepository.findAllByReviewerOrderByDueAtAsc(user).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AssignmentResponse acceptAssignment(Long assignmentId) {
        var current = currentUserEmail();
        var user = userRepository.findByEmail(current).orElseThrow(() -> new NotFoundException("User not found"));
        var assignment = assignmentRepository.findByIdAndReviewer(assignmentId, user)
            .orElseThrow(() -> new NotFoundException("Assignment not found"));
        if (assignment.getAcceptedAt() == null) {
            assignment.setAcceptedAt(Instant.now());
            assignment = assignmentRepository.save(assignment);
        }
        activityService.log(ActivityAction.REVIEW_ACCEPTED, "ASSIGNMENT", assignment.getId(), null);
        return toResponse(assignment);
    }

    @Override
    @Transactional
    public void submitReview(Long assignmentId, ReviewSubmitRequest request) {
        var current = currentUserEmail();
        var user = userRepository.findByEmail(current).orElseThrow(() -> new NotFoundException("User not found"));
        var assignment = assignmentRepository.findByIdAndReviewer(assignmentId, user)
            .orElseThrow(() -> new NotFoundException("Assignment not found"));
        var review = reviewRepository.findByAssignment(assignment).orElseGet(PaperReview::new);
        review.setAssignment(assignment);
        review.setReviewer(user);
        review.setDecision(request.getDecision());
        review.setComments(request.getComments());
        reviewRepository.save(review);
        assignment.setCompletedAt(Instant.now());
        assignmentRepository.save(assignment);
        activityService.log(ActivityAction.REVIEW_SUBMITTED, "ASSIGNMENT", assignment.getId(), request.getDecision().name());
    }

    @Override
    public List<AssignmentResponse> listAssignmentsByPaper(Long paperId) {
        var paper = paperRepository.findById(paperId).orElseThrow(() -> new NotFoundException("Paper not found"));
        return paper.getId() == null ? List.of() : assignmentRepository.findAll().stream()
            .filter(a -> a.getPaper().getId().equals(paperId))
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    @Override
    public PaperResponse getAssignedPaper(Long assignmentId) {
        var current = currentUserEmail();
        var user = userRepository.findByEmail(current).orElseThrow(() -> new NotFoundException("User not found"));
        var assignment = assignmentRepository.findByIdAndReviewer(assignmentId, user)
            .orElseThrow(() -> new NotFoundException("Assignment not found"));
        return toPaperResponse(assignment.getPaper());
    }

    @Override
    public List<PaperResponse> listAssignedPapers() {
        var current = currentUserEmail();
        var user = userRepository.findByEmail(current).orElseThrow(() -> new NotFoundException("User not found"));
        return assignmentRepository.findAllByReviewerOrderByDueAtAsc(user).stream()
            .map(a -> toPaperResponse(a.getPaper()))
            .collect(Collectors.toList());
    }

    @Override
    public PaperResponse getAssignedPaperByPaperId(Long paperId) {
        var current = currentUserEmail();
        var user = userRepository.findByEmail(current).orElseThrow(() -> new NotFoundException("User not found"));
        var assignment = assignmentRepository.findByPaper_IdAndReviewer(paperId, user)
            .orElseThrow(() -> new NotFoundException("Assignment for paper not found"));
        return toPaperResponse(assignment.getPaper());
    }

    private AssignmentResponse toResponse(ReviewAssignment assignment) {
        var resp = new AssignmentResponse();
        resp.setId(assignment.getId());
        resp.setPaperId(assignment.getPaper().getId());
        resp.setPaperTitle(assignment.getPaper().getTitle());
        resp.setDueAt(assignment.getDueAt());
        resp.setAcceptedAt(assignment.getAcceptedAt());
        resp.setCompleted(assignment.getCompletedAt() != null);
        if (assignment.getCompletedAt() == null && assignment.getDueAt() != null) {
            var now = Instant.now();
            var twoDays = now.plus(java.time.Duration.ofDays(2));
            resp.setDueSoon(assignment.getDueAt().isBefore(twoDays));
        } else {
            resp.setDueSoon(false);
        }
        return resp;
    }

    private String currentUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    private PaperResponse toPaperResponse(az.edu.asiouconferenceportal.entity.paper.PaperSubmission p) {
        PaperResponse r = new PaperResponse();
        r.setId(p.getId());
        r.setTitle(p.getTitle());
        r.setKeywords(p.getKeywords());
        r.setPaperAbstract(p.getPaperAbstract());
        r.setStatus(p.getStatus());
        r.setPaperType(p.getPaperType() != null ? p.getPaperType().getName() : null);
        r.setTopics(p.getTopics().stream().map(t -> t.getName()).collect(Collectors.toList()));
        r.setCoAuthors(p.getCoAuthors().stream().map(ca -> {
            az.edu.asiouconferenceportal.dto.paper.CoAuthorRequest x = new az.edu.asiouconferenceportal.dto.paper.CoAuthorRequest();
            x.setFirstName(ca.getFirstName());
            x.setLastName(ca.getLastName());
            x.setEmail(ca.getEmail());
            x.setAffiliation(ca.getAffiliation());
            x.setPosition(ca.getPosition());
            x.setCountry(ca.getCountry());
            x.setCity(ca.getCity());
            return x;
        }).collect(Collectors.toList()));
        r.setFileId(p.getPdf() != null ? p.getPdf().getId() : null);
        r.setCameraReadyFileId(p.getCameraReadyPdf() != null ? p.getCameraReadyPdf().getId() : null);
        return r;
    }
}


