package heedoitdox.deliverysystem.exception;

import heedoitdox.deliverysystem.exception.ErrorResponse.ValidationError;
import heedoitdox.deliverysystem.security.AuthenticationFailedException;
import heedoitdox.deliverysystem.security.UnprocessableException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException e,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request
    ) {
        log.error("handleMethodArgumentNotValid: " + e.getMessage());
        ErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
        return handleExceptionInternal(e, errorCode);
    }

    @ExceptionHandler(RequestParamBindException.class)
    protected ResponseEntity<Object> handleRequestParamBindException(RequestParamBindException e) {
        log.error("handleRequestParamBindException: " + e.getErrorCode().getMessage());
        ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(e.getErrors(), errorCode);
    }

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<Object> handleCustomException(RestApiException e) {
        log.warn("handleMethodArgumentNotValid: " + e.getMessage());
        ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAllException(Exception ex) {
        log.warn("handleAllException: ", ex);
        ErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("handleIllegalArgumentException: ", e);
        ErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
        return handleExceptionInternal(errorCode, e.getMessage());
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(AuthenticationFailedException e) {
        log.warn("handleUnauthorizedException: ", e);
        ErrorCode errorCode = CommonErrorCode.UNAUTHORIZED;
        return handleExceptionInternal(errorCode, e.getMessage());
    }

    @ExceptionHandler(UnprocessableException.class)
    public ResponseEntity<Object> handleUnprocessableException(AuthenticationFailedException e) {
        log.warn("handleUnauthorizedException: ", e);
        ErrorCode errorCode = CommonErrorCode.UNPROCESSABLE_ENTITY;
        return handleExceptionInternal(errorCode, e.getMessage());
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
            .body(makeErrorResponse(errorCode));
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
        return ErrorResponse.builder()
            .code(errorCode.name())
            .message(errorCode.getMessage())
            .build();
    }

    private ResponseEntity<Object> handleExceptionInternal(BindException e, ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
            .body(makeErrorResponse(e, errorCode));
    }

    private ErrorResponse makeErrorResponse(BindException e, ErrorCode errorCode) {
        List<ValidationError> validationErrorList = e.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(ErrorResponse.ValidationError::of)
            .collect(Collectors.toList());

        return ErrorResponse.builder()
            .code(errorCode.name())
            .message(errorCode.getMessage())
            .errors(validationErrorList)
            .build();
    }

    private ResponseEntity<Object> handleExceptionInternal(List<FieldError> fieldErrors, ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
            .body(makeErrorResponse(fieldErrors, errorCode));
    }

    private ErrorResponse makeErrorResponse(List<FieldError> fieldErrors, ErrorCode errorCode) {
        List<ValidationError> validationErrorList = fieldErrors
            .stream()
            .map(ErrorResponse.ValidationError::of)
            .collect(Collectors.toList());

        return ErrorResponse.builder()
            .code(errorCode.name())
            .message(errorCode.getMessage())
            .errors(validationErrorList)
            .build();
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode, String message) {
        return ResponseEntity.status(errorCode.getHttpStatus())
            .body(makeErrorResponse(errorCode, message));
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode, String message) {
        return ErrorResponse.builder()
            .code(errorCode.name())
            .message(message)
            .build();
    }
}
