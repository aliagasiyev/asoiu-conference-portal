package az.edu.asiouconferenceportal.service.paper;

import az.edu.asiouconferenceportal.dto.paper.PaperCreateRequest;
import az.edu.asiouconferenceportal.dto.paper.PaperResponse;
import az.edu.asiouconferenceportal.dto.paper.PaperUpdateRequest;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface PaperService {
	List<PaperResponse> myPapers();
	PaperResponse create(PaperCreateRequest request);
	PaperResponse update(Long id, PaperUpdateRequest request);
	void delete(Long id);
	PaperResponse uploadPdf(Long id, MultipartFile file);
}
