package az.edu.asiouconferenceportal.service.contribution;

import az.edu.asiouconferenceportal.dto.contribution.ContributionCreateRequest;
import az.edu.asiouconferenceportal.dto.contribution.ContributionResponse;
import az.edu.asiouconferenceportal.dto.contribution.ContributionUpdateRequest;

import java.util.List;

public interface ContributionService {
    List<ContributionResponse> myContributions();

    List<ContributionResponse> myContributions(int page, int size);

    ContributionResponse create(ContributionCreateRequest request);

    ContributionResponse update(Long id, ContributionUpdateRequest request);

    void delete(Long id);
}
