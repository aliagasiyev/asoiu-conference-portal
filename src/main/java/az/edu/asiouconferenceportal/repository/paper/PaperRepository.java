package az.edu.asiouconferenceportal.repository.paper;

import az.edu.asiouconferenceportal.entity.paper.PaperSubmission;
import az.edu.asiouconferenceportal.entity.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaperRepository extends JpaRepository<PaperSubmission, Long> {
	List<PaperSubmission> findAllByAuthorOrderByCreatedAtDesc(User author);
}
