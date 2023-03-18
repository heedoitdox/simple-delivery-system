package heedoitdox.deliverysystem.domain;

import heedoitdox.deliverysystem.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DeliveryErrorCode implements ErrorCode {
    UNABLE_TO_UPDATE_ADDRESS(HttpStatus.CONFLICT, "요청중인 배달의 주소만 수정할 수 있어요.");

    private final HttpStatus httpStatus;
    private final String message;
}

