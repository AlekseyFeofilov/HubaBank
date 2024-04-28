package org.huba.users.service;

import org.huba.users.dto.ErrorServiceSettingDto;
import org.huba.users.exception.ExternalServiceErrorException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;

@Service
public class ErrorService {
    private boolean timeErrors = true;
    private int oddProcent = 50;
    private int notOddProcent = 90;
    private final Random random = new Random();

    public void generateError() {
        if(timeErrors) {
            LocalTime localTime = LocalTime.now();
            int errorPercent = localTime.getMinute() % 2 == 0 ? oddProcent : notOddProcent;
            int real = random.nextInt() % 100;
            if(real > errorPercent) {
                return;
            } else {
                throw new ExternalServiceErrorException();
            }
        }
    }
    public void setErrors(ErrorServiceSettingDto errors) {
        timeErrors = errors.isTimeErrors();
        oddProcent = errors.getOddVar();
        notOddProcent = errors.getNotOddVar();
    }

    public ErrorServiceSettingDto getErrors() {
        ErrorServiceSettingDto errorServiceSettingDto = new ErrorServiceSettingDto();
        errorServiceSettingDto.setTimeErrors(timeErrors);
        errorServiceSettingDto.setOddVar(oddProcent);
        errorServiceSettingDto.setNotOddVar(notOddProcent);
        return errorServiceSettingDto;
    }
}
