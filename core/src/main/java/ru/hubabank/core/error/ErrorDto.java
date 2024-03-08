package ru.hubabank.core.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {

    @Schema(description = "Код ошибки. Рекомендуется использовать тип ошибки вместо кода.")
    private int code;

    @Schema(description = "Тип ошибки")
    private ErrorType type;

    @Schema(description = "Сообщение об ошибке", example = "Сообщение об ошибке")
    private String message;
}
