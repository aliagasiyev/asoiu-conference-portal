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

    @Override
    @Transactional
    public StoredFile store(MultipartFile file) throws IOException {
        Path dir = Path.of(properties.getStorageDir());
        Files.createDirectories(dir);
        String unique = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path target = dir.resolve(unique);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        StoredFile sf = new StoredFile();
        sf.setFilename(file.getOriginalFilename());
        sf.setContentType(file.getContentType());
        sf.setSize(file.getSize());
        sf.setPath(target.toString());
        return repository.save(sf);
    }
}


