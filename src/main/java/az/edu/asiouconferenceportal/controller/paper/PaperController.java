package az.edu.asiouconferenceportal.controller.paper;

import az.edu.asiouconferenceportal.dto.paper.PaperCreateRequest;
import az.edu.asiouconferenceportal.dto.paper.CoAuthorRequest;
import az.edu.asiouconferenceportal.dto.paper.PaperResponse;
import az.edu.asiouconferenceportal.dto.paper.PaperUpdateRequest;
import az.edu.asiouconferenceportal.service.paper.PaperService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/papers")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class PaperController {

	private final PaperService paperService;


	@GetMapping
	public List<PaperResponse> myPapers(@RequestParam(defaultValue = "0") int page,
	                                   @RequestParam(defaultValue = "20") int size) {
		return page >= 0 ? paperService.myPapers(page, size) : paperService.myPapers();
	}

	@PostMapping
	public ResponseEntity<PaperResponse> create(@Valid @RequestBody PaperCreateRequest req) {
		return ResponseEntity.ok(paperService.create(req));
	}

	@PutMapping("/{id}")
	public ResponseEntity<PaperResponse> update(@PathVariable Long id, @Valid @RequestBody PaperUpdateRequest req) {
		return ResponseEntity.ok(paperService.update(id, req));
	}

	@GetMapping("/{id}")
	public ResponseEntity<PaperResponse> get(@PathVariable Long id) {
		return ResponseEntity.ok(paperService.getById(id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		paperService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{id}/withdraw")
	public ResponseEntity<PaperResponse> withdraw(@PathVariable Long id) {
		return ResponseEntity.ok(paperService.withdraw(id));
	}

	@PostMapping("/{id}/submit")
	public ResponseEntity<PaperResponse> submit(@PathVariable Long id) {
		return ResponseEntity.ok(paperService.submit(id));
	}

	@PostMapping("/{id}/submit-camera-ready")
	public ResponseEntity<PaperResponse> submitCameraReady(@PathVariable Long id) {
		return ResponseEntity.ok(paperService.submitCameraReady(id));
	}

	@PostMapping(value = "/{id}/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<PaperResponse> upload(@PathVariable Long id, @RequestPart("file") MultipartFile file) {
		return ResponseEntity.ok(paperService.uploadPdf(id, file));
	}

	@PostMapping(value = "/{id}/camera-ready", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<PaperResponse> uploadCameraReady(@PathVariable Long id, @RequestPart("file") MultipartFile file) {
		return ResponseEntity.ok(paperService.uploadCameraReady(id, file));
	}

	@PostMapping("/{id}/co-authors")
	public ResponseEntity<PaperResponse> addCoAuthor(@PathVariable Long id, @RequestBody CoAuthorRequest req) {
		return ResponseEntity.ok(paperService.addCoAuthor(id, req));
	}

	@PutMapping("/{id}/co-authors/{coAuthorId}")
	public ResponseEntity<PaperResponse> updateCoAuthor(@PathVariable Long id, @PathVariable Long coAuthorId,
														  @RequestBody CoAuthorRequest req) {
		return ResponseEntity.ok(paperService.updateCoAuthor(id, coAuthorId, req));
	}

	@DeleteMapping("/{id}/co-authors/{coAuthorId}")
	public ResponseEntity<Void> deleteCoAuthor(@PathVariable Long id, @PathVariable Long coAuthorId) {
		paperService.deleteCoAuthor(id, coAuthorId);
		return ResponseEntity.noContent().build();
	}
}
