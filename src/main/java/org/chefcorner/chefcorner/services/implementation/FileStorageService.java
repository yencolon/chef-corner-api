package org.chefcorner.chefcorner.services.implementation;

import org.chefcorner.chefcorner.services.interfaces.FileStorageServiceInterface;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService implements FileStorageServiceInterface {
    private final Path rootLocation = Paths.get("uploads");

    public FileStorageService() throws IOException {
        if (!Files.exists(rootLocation)) {
            Files.createDirectories(rootLocation);
        }
    }

    @Override
    public String storeFile(MultipartFile file) throws IOException {
        String uniqueFilename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path destinationFile = rootLocation.resolve(uniqueFilename).normalize();
        Files.copy(file.getInputStream(), destinationFile);
        return destinationFile.toString();
    }
}
