package az.edu.asiouconferenceportal.repository.contribution;

import az.edu.asiouconferenceportal.entity.contribution.Contribution;
import az.edu.asiouconferenceportal.entity.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributionRepository extends JpaRepository<Contribution, Long> {
	List<Contribution> findAllByUserOrderByCreatedAtDesc(User user);
}
