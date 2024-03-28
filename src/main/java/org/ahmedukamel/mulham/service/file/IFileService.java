package org.ahmedukamel.mulham.service.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileService {
    String save(MultipartFile file, String path) throws IOException;

    String save(MultipartFile file, String name, String path) throws IOException;

    byte[] get(String fileName, String path) throws IOException;

    void delete(String fileName, String path) throws IOException;
}