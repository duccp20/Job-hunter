package vn.hoidanit.jobhunter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.hoidanit.jobhunter.domain.DTO.Response.User.ApiResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.file.UploadedFileResponse;
import vn.hoidanit.jobhunter.exception.AppException;
import vn.hoidanit.jobhunter.service.FileService;
import vn.hoidanit.jobhunter.util.Enum.ErrorCode;
import vn.hoidanit.jobhunter.util.Enum.SuccessCode;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.time.Instant;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @Value("${file.upload-file.base-uri}")
    private String baseURI;
    @PostMapping
    public ResponseEntity<ApiResponse<UploadedFileResponse>> handleUpload(
            @RequestParam(value = "file", required = false ) MultipartFile file,
            @RequestParam(value = "folder", required = false) String folder
    ) throws URISyntaxException {

        if (file == null || folder == null) {
            throw new AppException(ErrorCode.MISSING_REQUIRE_PARAM);
        }
//        validate
        fileService.validateFile(file);
//        create folder if not exist
        fileService.createUploadedFolder(baseURI+folder);
//        store
        String fileName = fileService.storeFile(file, folder);

        UploadedFileResponse uploadedFileResponse = UploadedFileResponse
                .builder()
                .fileName(fileName)
                .uploadedAt(Instant.now())
                .build();

        SuccessCode successCode = SuccessCode.UPLOADED_FILE;
        return ResponseEntity.status(successCode.getStatusCode()).body(
                ApiResponse.<UploadedFileResponse>builder()
                        .statusCode(successCode.getCode())
                        .data(uploadedFileResponse)
                        .message(successCode.getMessage())
                        .build()
        );
    }
//    download file
    @GetMapping
    public ResponseEntity<Resource> handleDownload(
            @RequestParam(value = "fileName", required = false) String fileName,
            @RequestParam(value = "folder", required = false) String folder
            ) throws URISyntaxException, FileNotFoundException {

        if(fileName == null || folder == null) {
            throw new AppException(ErrorCode.MISSING_REQUIRE_PARAM);
        }

        // check file exist (and not a directory) and get file length
        long fileLength = this.fileService.getFileLength(fileName, folder);
        if (fileLength == 0) {
            throw new AppException(ErrorCode.FIND_NOT_FOUND);
        }

        // download a file
        InputStreamResource resource = this.fileService.getResource(fileName, folder);

        return ResponseEntity.ok()
                //test postman co the down tu` file binary ve file trong may
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentLength(fileLength)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
