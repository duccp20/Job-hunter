package vn.hoidanit.jobhunter.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public interface FileService {
    void createUploadedFolder(String folder) throws URISyntaxException;

    String storeFile(MultipartFile file, String folder) throws URISyntaxException;

    void validateFile(MultipartFile file);

    long getFileLength(String fileName, String folder) throws URISyntaxException;

    InputStreamResource getResource(String fileName, String folder) throws URISyntaxException, FileNotFoundException;
}
