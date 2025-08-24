package az.edu.asiouconferenceportal.repository.paper;

import az.edu.asiouconferenceportal.entity.paper.PaperReview;
import az.edu.asiouconferenceportal.entity.paper.ReviewAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface PaperReviewRepository extends JpaRepository<PaperReview, Long> {
    Optional<PaperReview> findByAssignment(ReviewAssignment assignment);
    List<PaperReview> findAllByAssignment_Paper_Id(Long paperId);
}


