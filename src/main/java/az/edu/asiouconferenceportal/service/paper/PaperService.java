package az.edu.asiouconferenceportal.service.paper;

import az.edu.asiouconferenceportal.dto.paper.PaperCreateRequest;
import az.edu.asiouconferenceportal.dto.paper.PaperResponse;
import az.edu.asiouconferenceportal.dto.paper.PaperUpdateRequest;
import az.edu.asiouconferenceportal.dto.paper.CoAuthorRequest;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface PaperService {
    List<PaperResponse> myPapers();

    List<PaperResponse> myPapers(int page, int size);

    PaperResponse getById(Long id);

    PaperResponse create(PaperCreateRequest request);

    PaperResponse update(Long id, PaperUpdateRequest request);

    void delete(Long id);

    PaperResponse uploadPdf(Long id, MultipartFile file);

    PaperResponse uploadCameraReady(Long id, MultipartFile file);

    PaperResponse withdraw(Long id);

    PaperResponse addCoAuthor(Long paperId, CoAuthorRequest request);

    PaperResponse updateCoAuthor(Long paperId, Long coAuthorId, CoAuthorRequest request);

    void deleteCoAuthor(Long paperId, Long coAuthorId);

    PaperResponse submit(Long id);

    PaperResponse submitCameraReady(Long id);
}
