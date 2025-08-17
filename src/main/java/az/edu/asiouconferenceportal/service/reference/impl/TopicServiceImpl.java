package az.edu.asiouconferenceportal.service.reference.impl;

import az.edu.asiouconferenceportal.dto.reference.TopicRequest;
import az.edu.asiouconferenceportal.dto.reference.TopicResponse;
import az.edu.asiouconferenceportal.entity.reference.Topic;
import az.edu.asiouconferenceportal.exception.NotFoundException;
import az.edu.asiouconferenceportal.repository.reference.TopicRepository;
import az.edu.asiouconferenceportal.service.reference.TopicService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

	private final TopicRepository topicRepository;

	@Override
	@Transactional(readOnly = true)
	public List<TopicResponse> listActive() {
		return topicRepository.findAllByActiveTrueOrderByOrderIndexAscNameAsc()
			.stream().map(this::toResponse).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public TopicResponse create(TopicRequest request) {
		Topic t = new Topic();
		t.setName(request.getName());
		t.setActive(request.getActive() == null || request.getActive());
		t.setOrderIndex(request.getOrderIndex());
		return toResponse(topicRepository.save(t));
	}

	@Override
	@Transactional
	public TopicResponse update(Long id, TopicRequest request) {
		Topic t = topicRepository.findById(id).orElseThrow(() -> new NotFoundException("Topic not found"));
		t.setName(request.getName());
		if (request.getActive() != null) t.setActive(request.getActive());
		t.setOrderIndex(request.getOrderIndex());
		return toResponse(t);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		topicRepository.deleteById(id);
	}

	private TopicResponse toResponse(Topic topic) {
		TopicResponse r = new TopicResponse();
		r.setId(topic.getId());
		r.setName(topic.getName());
		r.setActive(topic.isActive());
		r.setOrderIndex(topic.getOrderIndex());
		return r;
	}
}


