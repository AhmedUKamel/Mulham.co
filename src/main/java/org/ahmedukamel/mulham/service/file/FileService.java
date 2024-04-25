package org.ahmedukamel.mulham.service.file;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class FileService implements IFileService {

    @Override
    public String save(MultipartFile file, String path) throws IOException {
        while (true) {
            try {
                return save(file, UUID.randomUUID().toString(), path);
            } catch (FileAlreadyExistsException ignore) {
            }
        }
    }

    @Override
    public String save(MultipartFile file, String name, String path) throws IOException {
        Path directoryPath = Path.of(path);

        if (!Files.exists(directoryPath)) {
            Files.createDirectory(directoryPath);
        }

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String fileName = "%s.%s".formatted(name, extension);

        Path filePath = directoryPath.resolve(fileName);
        if (Files.exists(filePath)) {
            throw new FileAlreadyExistsException(fileName);
        }

        Files.copy(file.getInputStream(), filePath);
        return fileName;
    }

    @Override
    public byte[] get(String fileName, String path) throws IOException {
        Path filePath = Path.of(path).resolve(fileName);
        return Files.readAllBytes(filePath);
    }

    @Override
    public void delete(String fileName, String path) throws IOException {
        Path filePath = Path.of(path).resolve(fileName);

        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }
    }
}