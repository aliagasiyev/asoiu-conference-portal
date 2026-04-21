package az.edu.asiouconferenceportal.repository.paper;

import az.edu.asiouconferenceportal.entity.paper.ReviewAssignment;
import az.edu.asiouconferenceportal.entity.user.User;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewAssignmentRepository extends JpaRepository<ReviewAssignment, Long> {
    @EntityGraph(attributePaths = {"paper", "reviewer"})
    List<ReviewAssignment> findAllByReviewerOrderByDueAtAsc(User reviewer);

    @EntityGraph(attributePaths = {"paper", "reviewer"})
    List<ReviewAssignment> findAllByDueAtBeforeAndCompletedAtIsNull(Instant dueBefore);

    @EntityGraph(attributePaths = {"paper", "reviewer"})
    List<ReviewAssignment> findAllByPaper_Id(Long paperId);

    @EntityGraph(attributePaths = {"paper", "reviewer"})
    Optional<ReviewAssignment> findByIdAndReviewer(Long id, User reviewer);

    @EntityGraph(attributePaths = {"paper", "reviewer"})
    Optional<ReviewAssignment> findByPaper_IdAndReviewer(Long paperId, User reviewer);

    boolean existsByPaper_Id(Long paperId);
}


