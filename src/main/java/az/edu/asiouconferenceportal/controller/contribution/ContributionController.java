package az.edu.asiouconferenceportal.controller.contribution;

import az.edu.asiouconferenceportal.dto.contribution.*;
import az.edu.asiouconferenceportal.service.contribution.ContributionService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contributions")
@RequiredArgsConstructor
public class ContributionController {

	private final ContributionService contributionService;

	@GetMapping
	public List<ContributionResponse> list() { return contributionService.myContributions(); }

	@PostMapping
	public ResponseEntity<ContributionResponse> create(@Valid @RequestBody ContributionCreateRequest req) {
		return ResponseEntity.ok(contributionService.create(req));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ContributionResponse> update(@PathVariable Long id, @Valid @RequestBody ContributionUpdateRequest req) {
		return ResponseEntity.ok(contributionService.update(id, req));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		contributionService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
