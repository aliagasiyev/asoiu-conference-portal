package az.edu.asiouconferenceportal.repository.reference;

import az.edu.asiouconferenceportal.entity.reference.Topic;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
	List<Topic> findAllByActiveTrueOrderByOrderIndexAscNameAsc();
}
