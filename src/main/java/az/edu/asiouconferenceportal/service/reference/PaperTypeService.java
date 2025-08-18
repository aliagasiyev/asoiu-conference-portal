package az.edu.asiouconferenceportal.service.reference;

import az.edu.asiouconferenceportal.dto.reference.PaperTypeRequest;
import az.edu.asiouconferenceportal.dto.reference.PaperTypeResponse;

import java.util.List;

public interface PaperTypeService {
    List<PaperTypeResponse> listActive();

    PaperTypeResponse create(PaperTypeRequest request);

    PaperTypeResponse update(Long id, PaperTypeRequest request);

    void delete(Long id);
}
