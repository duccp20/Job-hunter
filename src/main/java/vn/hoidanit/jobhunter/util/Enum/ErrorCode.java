package vn.hoidanit.jobhunter.util.Enum;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {

    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Invalid key error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    INVALID_USERNAME(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    METHOD_NOT_ALLOWED(1009, "Your method http or URI not allowed", HttpStatus.METHOD_NOT_ALLOWED),
    METHOD_ARGUMENT_MISMATCH(1010, "Argument type not match", HttpStatus.BAD_REQUEST),
    RESOURCE_NOT_FOUND(1011, "Resource Not Found", HttpStatus.NOT_FOUND),
    Token_Not_Valid(1014, "Token Not Valid", HttpStatus.BAD_REQUEST),
    MISS_COOKIE(1015, "Missed Cookie" , HttpStatus.BAD_REQUEST),

    //    COMPANY
    COMPANY_EXISTED(1012, "Company Existed", HttpStatus.BAD_REQUEST),
    COMPANY_NOT_EXISTED(1013, "Company NOT Existed", HttpStatus.BAD_REQUEST),

    //Skill
    SKILL_EXISTED(1016, "Skill Existed", HttpStatus.BAD_REQUEST),
    SKILL_NOT_EXISTED(1017, "Skill Not Existed", HttpStatus.BAD_REQUEST ),
    JOB_EXISTED(1018, "Job Existed" , HttpStatus.BAD_REQUEST ),
    JOB_NOT_EXISTED(1019, "Job Not Existed" , HttpStatus.BAD_REQUEST );
    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }


}
