package com.lotdiz.memberservice.exceptionhandler;

import com.lotdiz.memberservice.dto.response.ResultDataResponse;
import com.lotdiz.memberservice.exception.common.DomainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class MemberRestControllerAdvice extends ResponseEntityExceptionHandler {

  private static final String UNIQUE_CONSTRAINT_EXCEPTION_MESSAGE = "유니크 제약조건 오류";
  private static final String DUPLICATE_KEY_EXCEPTION_MESSAGE = "중복 키 오류";
  private static final String DOMAIN_EXCEPTION_MESSAGE = "도메인 오류";
  private static final String METHOD_ARGUMENT_VALID_EXCEPTION_MESSAGE = "VALIDATION 오류";

  private static final String INTERNAL_SERVER_ERROR = "서버 내부 오류";

  @ExceptionHandler(DomainException.class)
  public ResponseEntity<ResultDataResponse<Object>> domainException(DomainException e) {
    int statusCode = e.getStatusCode();

    ResultDataResponse<Object> body =
        new ResultDataResponse<>(
            String.valueOf(statusCode), DOMAIN_EXCEPTION_MESSAGE, e.getMessage(), null);

    return ResponseEntity.status(statusCode).body(body);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ResultDataResponse<Object>> constraintViolationException(
      DataIntegrityViolationException e) {
    int statusCode = HttpStatus.BAD_REQUEST.value();

    ResultDataResponse<Object> body =
        new ResultDataResponse<>(
            String.valueOf(statusCode), UNIQUE_CONSTRAINT_EXCEPTION_MESSAGE, e.getMessage(), null);
    return ResponseEntity.status(statusCode).body(body);
  }

  @ExceptionHandler(DuplicateKeyException.class)
  public ResponseEntity<ResultDataResponse<Object>> duplicateKeyException(DuplicateKeyException e) {
    int statusCode = HttpStatus.BAD_REQUEST.value();

    ResultDataResponse<Object> body =
        new ResultDataResponse<>(
            String.valueOf(statusCode), DUPLICATE_KEY_EXCEPTION_MESSAGE, e.getMessage(), null);

    return ResponseEntity.status(statusCode).body(body);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ResultDataResponse<Object>> exception(Exception e) {
    int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();

    ResultDataResponse<Object> body =
        new ResultDataResponse<>(
            String.valueOf(statusCode), INTERNAL_SERVER_ERROR, e.getMessage(), null);

    return ResponseEntity.status(statusCode).body(body);
  }

  protected ResponseEntity<ResultDataResponse<Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
    log.error("{}", e.getMessage());

    ResultDataResponse<Object> body =
        new ResultDataResponse<>(
            String.valueOf(HttpStatus.BAD_REQUEST.value()),
            METHOD_ARGUMENT_VALID_EXCEPTION_MESSAGE,
            e.getBindingResult().getFieldError() == null
                ? e.getMessage()
                : e.getBindingResult().getFieldError().getDefaultMessage(),
            null);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(body);
  }
}
