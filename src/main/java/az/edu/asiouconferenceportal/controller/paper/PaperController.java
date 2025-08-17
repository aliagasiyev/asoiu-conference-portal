package az.edu.asiouconferenceportal.controller.paper;

import az.edu.asiouconferenceportal.dto.paper.PaperCreateRequest;
import az.edu.asiouconferenceportal.dto.paper.PaperResponse;
import az.edu.asiouconferenceportal.dto.paper.PaperUpdateRequest;
import az.edu.asiouconferenceportal.service.paper.PaperService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/papers")
@RequiredArgsConstructor
public class PaperController {

	private final PaperService paperService;

	@GetMapping
	public List<PaperResponse> myPapers() { return paperService.myPapers(); }

	@PostMapping
	public ResponseEntity<PaperResponse> create(@Valid @RequestBody PaperCreateRequest req) {
		return ResponseEntity.ok(paperService.create(req));
	}

	@PutMapping("/{id}")
	public ResponseEntity<PaperResponse> update(@PathVariable Long id, @Valid @RequestBody PaperUpdateRequest req) {
		return ResponseEntity.ok(paperService.update(id, req));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		paperService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping(value = "/{id}/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<PaperResponse> upload(@PathVariable Long id, @RequestPart("file") MultipartFile file) {
		return ResponseEntity.ok(paperService.uploadPdf(id, file));
	}
}
