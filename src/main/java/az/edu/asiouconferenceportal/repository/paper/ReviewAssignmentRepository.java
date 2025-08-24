package az.edu.asiouconferenceportal.repository.paper;

import az.edu.asiouconferenceportal.entity.paper.ReviewAssignment;
import az.edu.asiouconferenceportal.entity.user.User;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewAssignmentRepository extends JpaRepository<ReviewAssignment, Long> {
    List<ReviewAssignment> findAllByReviewerOrderByDueAtAsc(User reviewer);
    List<ReviewAssignment> findAllByDueAtBeforeAndCompletedAtIsNull(Instant dueBefore);
    Optional<ReviewAssignment> findByIdAndReviewer(Long id, User reviewer);
    Optional<ReviewAssignment> findByPaper_IdAndReviewer(Long paperId, User reviewer);
}


