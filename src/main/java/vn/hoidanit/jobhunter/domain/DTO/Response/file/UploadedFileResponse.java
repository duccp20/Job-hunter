package vn.hoidanit.jobhunter.domain.DTO.Response.file;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UploadedFileResponse {

    String fileName;
    Instant uploadedAt;
}
