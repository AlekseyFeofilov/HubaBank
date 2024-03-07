package ru.hubabank.core.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto {

    private int code;
    @Schema(example = "Сообщение об ошибке")
    private String message;
}
