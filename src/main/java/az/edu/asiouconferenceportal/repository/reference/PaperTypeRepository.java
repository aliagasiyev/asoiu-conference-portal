package az.edu.asiouconferenceportal.repository.reference;

import az.edu.asiouconferenceportal.entity.reference.PaperTypeEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaperTypeRepository extends JpaRepository<PaperTypeEntity, Long> {
	List<PaperTypeEntity> findAllByActiveTrueOrderByNameAsc();
}
