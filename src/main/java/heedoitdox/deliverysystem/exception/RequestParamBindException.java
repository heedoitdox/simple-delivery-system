package heedoitdox.deliverysystem.exception;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;

@Getter
@RequiredArgsConstructor
public class RequestParamBindException extends RuntimeException {
    private final ErrorCode errorCode;
    private final List<FieldError> errors;
}
