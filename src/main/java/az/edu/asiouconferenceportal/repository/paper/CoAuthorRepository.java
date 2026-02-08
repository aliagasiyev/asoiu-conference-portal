package az.edu.asiouconferenceportal.repository.paper;

import az.edu.asiouconferenceportal.entity.paper.CoAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoAuthorRepository extends JpaRepository<CoAuthor, Long> {
}
