package ru.hubabank.core.controller.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.hubabank.core.error.ErrorDto;
import ru.hubabank.core.exception.ServiceException;

@ControllerAdvice
public class ControllerResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorDto> handleServiceException(
            ServiceException exception
    ) {
        return new ResponseEntity<>(
                ErrorDto.builder()
                        .code(exception.getCode())
                        .message(exception.getMessage())
                        .build(),
                new HttpHeaders(),
                exception.getStatus()
        );
    }
}
