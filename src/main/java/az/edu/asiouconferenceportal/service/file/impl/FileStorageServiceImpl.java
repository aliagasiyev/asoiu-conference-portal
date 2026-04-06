package az.edu.asiouconferenceportal.service.file.impl;

import az.edu.asiouconferenceportal.config.FileStorageProperties;
import az.edu.asiouconferenceportal.entity.file.StoredFile;
import az.edu.asiouconferenceportal.repository.file.StoredFileRepository;
import az.edu.asiouconferenceportal.service.file.FileStorageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    private final StoredFileRepository repository;
    private final FileStorageProperties properties;

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    private static final java.util.Map<String, String> ALLOWED_EXTENSIONS = java.util.Map.of(
            "pdf", "application/pdf",
            "doc", "application/msword",
            "docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    );

    @Override
    @Transactional
    public StoredFile store(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("File size exceeds 10MB limit");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.contains("..")) {
            throw new IllegalArgumentException("Invalid filename or potential path traversal");
        }

        // Robust extension extraction
        int lastDotIndex = originalFilename.lastIndexOf(".");
        if (lastDotIndex == -1 || lastDotIndex == originalFilename.length() - 1) {
            throw new IllegalArgumentException("File must have an extension");
        }

        String extension = originalFilename.substring(lastDotIndex + 1).toLowerCase();
        String expectedContentType = ALLOWED_EXTENSIONS.get(extension);

        if (expectedContentType == null) {
            throw new IllegalArgumentException("Invalid file extension. Allowed: PDF, DOC, DOCX");
        }

        // Content-Type validation (defense in depth)
        String actualContentType = file.getContentType();
        if (actualContentType == null || !actualContentType.equalsIgnoreCase(expectedContentType)) {
            // Some clients might send generic octet-stream, but for production, we should be strict
            // or use a library like Apache Tika for deep inspection. 
            // For now, we enforce the matching content-type.
            throw new IllegalArgumentException("Content-Type mismatch for extension ." + extension);
        }

        Path dir = Path.of(properties.getStorageDir()).toAbsolutePath().normalize();
        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
        }

        // Robust filename sanitization: UUID + timestamp + sanitized name
        String sanitizedName = originalFilename.substring(0, lastDotIndex)
                .replaceAll("[^a-zA-Z0-9_-]", "_");
        String uniqueName = UUID.randomUUID() + "_" + System.currentTimeMillis() + "_" + sanitizedName + "." + extension;
        
        Path target = dir.resolve(uniqueName).normalize();
        
        // Final security check: ensure target path is within the storage directory
        if (!target.startsWith(dir)) {
            throw new SecurityException("Target path is outside of storage directory");
        }

        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        StoredFile sf = new StoredFile();
        sf.setFilename(originalFilename);
        sf.setContentType(expectedContentType);
        sf.setSize(file.getSize());
        sf.setPath(target.toString());
        return repository.save(sf);
    }
}


