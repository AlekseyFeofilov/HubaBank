package ru.hubabank.core.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import ru.hubabank.core.error.ErrorType;
import ru.hubabank.core.service.PropertyService;
import ru.hubabank.core.versioning.ApiVersion;
import ru.hubabank.core.versioning.ApiVersionRange;

import java.lang.reflect.Method;
import java.time.ZonedDateTime;
import java.util.concurrent.ThreadLocalRandom;

import static ru.hubabank.core.constant.PropertyConstants.*;

@Aspect
@Component
@RequiredArgsConstructor
public class ErrorSimulationAspect {

    private final PropertyService propertyService;

    @Pointcut("@annotation(ru.hubabank.core.versioning.ApiVersionRange)")
    public void callApiMethod() {}

    @Before("callApiMethod()")
    public void beforeCallApiMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ApiVersionRange annotation = method.getAnnotation(ApiVersionRange.class);

        if (ApiVersion.VERSION_3.getNumber() >= annotation.min().getNumber()
                && ApiVersion.VERSION_3.getNumber() <= annotation.max().getNumber()
                && propertyService.getBoolean(ERROR_SIMULATION_ENABLED)
                && (ThreadLocalRandom.current().nextDouble() < getErrorChange())) {
            throw ErrorType.UNKNOWN.createException();
        }
    }

    private double getErrorChange() {
        if (ZonedDateTime.now().getMinute() % 2 == 0) {
            return propertyService.getDouble(ERROR_SIMULATION_INCREASED_CHANGE);
        } else {
            return propertyService.getDouble(ERROR_SIMULATION_DEFAULT_CHANGE);
        }
    }
}
