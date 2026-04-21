package az.edu.asiouconferenceportal.repository.paper;

import az.edu.asiouconferenceportal.entity.paper.PaperSubmission;
import az.edu.asiouconferenceportal.entity.user.User;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaperRepository extends JpaRepository<PaperSubmission, Long> {
    @EntityGraph(attributePaths = {"topics", "coAuthors", "paperType", "pdf", "cameraReadyPdf"})
    List<PaperSubmission> findAllByAuthorOrderByCreatedAtDesc(User author);

    @EntityGraph(attributePaths = {"topics", "coAuthors", "paperType", "pdf", "cameraReadyPdf"})
    Page<PaperSubmission> findAllByAuthorOrderByCreatedAtDesc(User author, Pageable pageable);

    @EntityGraph(attributePaths = {"topics", "coAuthors", "paperType", "pdf", "cameraReadyPdf"})
    List<PaperSubmission> findAll();

    Optional<PaperSubmission> findByPdf_Id(Long fileId);

    Optional<PaperSubmission> findByCameraReadyPdf_Id(Long fileId);
}
