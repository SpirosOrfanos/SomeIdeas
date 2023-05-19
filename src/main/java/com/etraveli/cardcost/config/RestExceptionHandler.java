package com.etraveli.cardcost.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.etraveli.cardcost.domain.dto.ApiError;
import com.etraveli.cardcost.domain.dto.ApiErrorEnum;
import jakarta.validation.ConstraintViolationException;



@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({ConstraintViolationException.class})
  protected ResponseEntity<ApiError> handleConstraintViolationException(
      ConstraintViolationException ex) {
    ApiError apiError = ApiErrorEnum.BAD_REQUEST_EXCEPTION.create();
    apiError.setDesc(ex.getMessage());
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(ConversionFailedException.class)
  public ResponseEntity<ApiError> handleConflict(RuntimeException ex) {
    return buildResponseEntity(ApiErrorEnum.CONVERSION_FAILED_ERROR.create());
  }


  private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
    return ResponseEntity.status(apiError.getHttpCode()).body(apiError);
  }

}
