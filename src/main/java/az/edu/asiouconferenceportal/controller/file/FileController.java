package az.edu.asiouconferenceportal.controller.file;

import az.edu.asiouconferenceportal.entity.file.StoredFile;
import az.edu.asiouconferenceportal.repository.file.StoredFileRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

	private final StoredFileRepository storedFileRepository;

	@GetMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Resource> download(@PathVariable Long id) throws IOException {
		StoredFile sf = storedFileRepository.findById(id).orElseThrow();
		Path path = Path.of(sf.getPath());
		byte[] bytes = Files.readAllBytes(path);
		var resource = new ByteArrayResource(bytes);
		return ResponseEntity.ok()
			.contentType(MediaType.parseMediaType(sf.getContentType()))
			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + sf.getFilename() + "\"")
			.body(resource);
	}
}
