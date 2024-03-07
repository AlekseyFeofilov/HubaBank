package ru.hubabank.core.controller.advice;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.hubabank.core.error.ErrorDto;
import ru.hubabank.core.exception.ServiceException;

@RestControllerAdvice
@ApiResponse(responseCode = "401", content = @Content(), description = "Не авторизован")
@ApiResponse(responseCode = "403", content = @Content(), description = "Недостаточно прав")

public class ControllerResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    @ApiResponse(responseCode = "400", description = "Бизнес ошибка или ошибка валидации")
    @ApiResponse(responseCode = "404", description = "Бизнес ошибка: что-то не найдено")
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервиса")
    @ApiResponse(responseCode = "502", description = "Ошибка взаимодействия с другими с сервисами")
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
