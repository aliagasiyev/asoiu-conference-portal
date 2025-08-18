package az.edu.asiouconferenceportal.controller.contribution;

import az.edu.asiouconferenceportal.dto.contribution.*;
import az.edu.asiouconferenceportal.service.contribution.ContributionService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/contributions")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class ContributionController {

	private final ContributionService contributionService;


	@GetMapping
	public List<ContributionResponse> list(@RequestParam(defaultValue = "0") int page,
	                                      @RequestParam(defaultValue = "20") int size) {
		return page >= 0 ? contributionService.myContributions(page, size) : contributionService.myContributions();
	}

	@PostMapping
	public ResponseEntity<ContributionResponse> create(@Valid @RequestBody ContributionCreateRequest req) {
		return ResponseEntity.ok(contributionService.create(req));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ContributionResponse> update(@PathVariable Long id, @Valid @RequestBody ContributionUpdateRequest req) {
		return ResponseEntity.ok(contributionService.update(id, req));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ContributionResponse> get(@PathVariable Long id) {
		// simple pass-through using existing service
		return ResponseEntity.ok(
			contributionService.myContributions().stream().filter(c -> c.getId().equals(id)).findFirst()
				.orElseThrow(() -> new RuntimeException("Not found"))
		);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		contributionService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
