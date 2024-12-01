package org.chefcorner.chefcorner.services.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageServiceInterface {
    String storeFile(MultipartFile file) throws IOException;
}
