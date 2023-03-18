package heedoitdox.deliverysystem.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid parameter included"),
    INTERNAL_SERVER_ERROR(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "Unexpected internal server error"
    ),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Unauthorized"),
    UNPROCESSABLE_ENTITY(HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable Entity"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "Not found entity"),
    PERMISSION_DENIED(HttpStatus.FORBIDDEN, "Permission denied");

    private final HttpStatus httpStatus;
    private final String message;
}
