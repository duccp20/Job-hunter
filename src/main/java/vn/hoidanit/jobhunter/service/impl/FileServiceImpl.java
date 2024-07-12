package vn.hoidanit.jobhunter.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.hoidanit.jobhunter.exception.AppException;
import vn.hoidanit.jobhunter.service.FileService;
import vn.hoidanit.jobhunter.util.Enum.ErrorCode;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    @Value("${file.upload-file.base-uri}")
    private String baseURI;

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxSize;
    @Override
    public void createUploadedFolder(String folder) throws URISyntaxException {
        URI uri = new URI(folder);
        Path path = Paths.get(uri);
        File tmpDir = new File(path.toString());
        if (!tmpDir.isDirectory()) {
            try {
                Files.createDirectory(tmpDir.toPath());
                System.out.println(">>> CREATE NEW DIRECTORY SUCCESSFUL, PATH = " + folder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(">>> SKIP MAKING DIRECTORY, ALREADY EXISTS");
        }
    }

    @Override
    public String storeFile(MultipartFile file, String folder) throws URISyntaxException {
        // create unique filename
        String finalName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
        URI uri = new URI( baseURI+ folder + "/" + finalName);
        Path path = Paths.get(uri);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, path,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return finalName;
    }

    @Override
    public void validateFile(MultipartFile file) {

//        File is empty

        if (file == null || file.isEmpty()) {
            throw new AppException(ErrorCode.MISSING_REQUIRE_PARAM);
        }

        String fileName = file.getOriginalFilename();
        List<String> allowedExtensions = Arrays.asList("pdf", "jpg", "jpeg", "png", "doc", "docx");
        List<String> allowedMimeTypes = Arrays.asList(
                "application/pdf",
                "image/jpeg",
                "image/png",
                "application/msword",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
        );

        // Validate extension
        boolean isValidExtension = allowedExtensions.stream().anyMatch(ext ->
                fileName.toLowerCase().endsWith("." + ext));
        if (!isValidExtension) {
            throw new AppException(ErrorCode.INVALID_FILE_EXTENSION);
        }

        // Validate MIME type
        String contentType = file.getContentType();
        if (!allowedMimeTypes.contains(contentType)) {
            throw new AppException(ErrorCode.INVALID_FILE_MIME_TYPE);
        }

        // Check file size
        long maxSize = 5 * 1024 * 1024 * 10; // 50 MB in bytes

        if (file.getSize() > maxSize) {
            throw new AppException(ErrorCode.INVALID_FILE_MAX_SIZE);
        }

    }

    @Override
    public long getFileLength(String fileName, String folder) throws URISyntaxException {
        URI uri = new URI(baseURI + folder + "/" + fileName);
        Path path = Paths.get(uri);

        File tmpDir = new File(path.toString());

        // file không tồn tại, hoặc file là 1 directory => return 0
        if (!tmpDir.exists() || tmpDir.isDirectory())
            return 0;
        return tmpDir.length();
    }
    @Override
    public InputStreamResource getResource(String fileName, String folder) throws URISyntaxException, FileNotFoundException {
        URI uri = new URI(baseURI + folder + "/" + fileName);
        Path path = Paths.get(uri);

        File file = new File(path.toString());
        return new InputStreamResource(new FileInputStream(file));
    }
}
