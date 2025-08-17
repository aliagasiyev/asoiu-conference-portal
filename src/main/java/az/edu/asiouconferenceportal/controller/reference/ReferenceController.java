package az.edu.asiouconferenceportal.controller.reference;

import az.edu.asiouconferenceportal.dto.reference.PaperTypeResponse;
import az.edu.asiouconferenceportal.dto.reference.TopicResponse;
import az.edu.asiouconferenceportal.service.reference.PaperTypeService;
import az.edu.asiouconferenceportal.service.reference.TopicService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reference")
@RequiredArgsConstructor
public class ReferenceController {

	private final TopicService topicService;
	private final PaperTypeService paperTypeService;

	@GetMapping("/topics")
	public List<TopicResponse> topics() { return topicService.listActive(); }

	@GetMapping("/paper-types")
	public List<PaperTypeResponse> paperTypes() { return paperTypeService.listActive(); }
}
