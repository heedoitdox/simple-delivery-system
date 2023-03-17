package heedoitdox.deliverysystem.domain;

import heedoitdox.deliverysystem.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, "중복된 아이디에요."),
    INVALID_PASSWORD_FORMAT(
        HttpStatus.BAD_REQUEST,
        "비밀번호는 영어 대문자, 영어 소문자, 숫자, 특수문자 중 3종류 이상이고 "
            + "12자리 이상, 25자리 이하인 문자열로 생성해야 합니다."),;

    private final HttpStatus httpStatus;
    private final String message;
}
