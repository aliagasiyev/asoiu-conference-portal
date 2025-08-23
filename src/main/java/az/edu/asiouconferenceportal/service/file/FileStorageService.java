package az.edu.asiouconferenceportal.service.file;

import az.edu.asiouconferenceportal.entity.file.StoredFile;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    StoredFile store(MultipartFile file) throws IOException;
}
