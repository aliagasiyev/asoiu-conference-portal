package az.edu.asiouconferenceportal.service.reference;

import az.edu.asiouconferenceportal.dto.reference.TopicRequest;
import az.edu.asiouconferenceportal.dto.reference.TopicResponse;
import java.util.List;

public interface TopicService {
	List<TopicResponse> listActive();
	TopicResponse create(TopicRequest request);
	TopicResponse update(Long id, TopicRequest request);
	void delete(Long id);
}
