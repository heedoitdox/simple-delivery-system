package heedoitdox.deliverysystem.domain;

import heedoitdox.deliverysystem.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, "중복된 아이디에요.");

    private final HttpStatus httpStatus;
    private final String message;
}
