package vn.hoidanit.jobhunter.util.Enum;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum SuccessCode {


    OK(200, "Login Successfully!", HttpStatus.OK ),
    CREATED(201, "Created Successfully!", HttpStatus.CREATED),
    DELETED (200, "Deleted Successfully!", HttpStatus.OK),
    UPDATED(200, "Updated Successfully", HttpStatus.OK),
    GET_SUCCESS(200, "Get Successfully" , HttpStatus.OK),
    UPLOADED_FILE(200, "Uploaded File Successfully", HttpStatus.OK);
    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    SuccessCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

}
