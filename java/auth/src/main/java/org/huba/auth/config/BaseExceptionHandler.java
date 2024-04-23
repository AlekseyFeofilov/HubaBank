package org.huba.auth.config;

import jakarta.validation.ValidationException;
import org.huba.users.exception.CustomException;
import org.huba.users.exception.MessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class BaseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    private ResponseEntity<MessageDto> handleIllegalArgument(CustomException ex) throws IOException {
        MessageDto messageDto = new MessageDto(ex.getMessage());
        return new ResponseEntity<MessageDto>(messageDto, ex.getStatus());
    }


    @ExceptionHandler(ValidationException.class)
    private ResponseEntity<MessageDto> handleValidationException(ValidationException ex) throws IOException {
        MessageDto messageDto = new MessageDto("bad request");
        return new ResponseEntity<MessageDto>(messageDto, HttpStatus.BAD_REQUEST);
    }
}