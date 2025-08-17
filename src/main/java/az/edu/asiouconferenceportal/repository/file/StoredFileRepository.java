package az.edu.asiouconferenceportal.repository.file;

import az.edu.asiouconferenceportal.entity.file.StoredFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoredFileRepository extends JpaRepository<StoredFile, Long> { }
