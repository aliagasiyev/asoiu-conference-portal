package az.edu.asiouconferenceportal.repository.contribution;

import az.edu.asiouconferenceportal.entity.contribution.Contribution;
import az.edu.asiouconferenceportal.entity.user.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributionRepository extends JpaRepository<Contribution, Long> {
	List<Contribution> findAllByUserOrderByCreatedAtDesc(User user);

	Page<Contribution> findAllByUserOrderByCreatedAtDesc(User user, Pageable pageable);
}
