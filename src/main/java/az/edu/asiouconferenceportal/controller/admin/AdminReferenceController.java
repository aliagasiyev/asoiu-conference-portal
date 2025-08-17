package az.edu.asiouconferenceportal.controller.admin;

import az.edu.asiouconferenceportal.dto.reference.*;
import az.edu.asiouconferenceportal.service.reference.ConferenceSettingsService;
import az.edu.asiouconferenceportal.service.reference.PaperTypeService;
import az.edu.asiouconferenceportal.service.reference.TopicService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/reference")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminReferenceController {

	private final TopicService topicService;
	private final PaperTypeService paperTypeService;
	private final ConferenceSettingsService settingsService;

	@GetMapping("/topics")
	public List<TopicResponse> listTopics() { return topicService.listActive(); }

	@PostMapping("/topics")
	@ResponseStatus(HttpStatus.CREATED)
	public TopicResponse createTopic(@Validated @RequestBody TopicRequest request) { return topicService.create(request); }

	@PutMapping("/topics/{id}")
	public TopicResponse updateTopic(@PathVariable Long id, @Validated @RequestBody TopicRequest request) { return topicService.update(id, request); }

	@DeleteMapping("/topics/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteTopic(@PathVariable Long id) { topicService.delete(id); }

	@GetMapping("/paper-types")
	public List<PaperTypeResponse> listTypes() { return paperTypeService.listActive(); }

	@PostMapping("/paper-types")
	@ResponseStatus(HttpStatus.CREATED)
	public PaperTypeResponse createType(@Validated @RequestBody PaperTypeRequest request) { return paperTypeService.create(request); }

	@PutMapping("/paper-types/{id}")
	public PaperTypeResponse updateType(@PathVariable Long id, @Validated @RequestBody PaperTypeRequest request) { return paperTypeService.update(id, request); }

	@DeleteMapping("/paper-types/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteType(@PathVariable Long id) { paperTypeService.delete(id); }

	@GetMapping("/settings")
	public ConferenceSettingsResponse getSettings() { return settingsService.get(); }

	@PutMapping("/settings")
	public ConferenceSettingsResponse updateSettings(@Validated @RequestBody ConferenceSettingsRequest request) { return settingsService.update(request); }
}
