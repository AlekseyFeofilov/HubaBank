package ru.hubabank.core.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.hubabank.core.service.PropertyService;

import static ru.hubabank.core.constant.PropertyConstants.DELAY_SIMULATION_ENABLED;
import static ru.hubabank.core.constant.PropertyConstants.SIMULATED_DELAY_TIME;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class DelaySimulationAspect {

    private final PropertyService propertyService;

    @Pointcut("@annotation(ru.hubabank.core.versioning.ApiVersionRange)")
    public void callApiMethod() {}

    @Before("callApiMethod()")
    public void beforeCallApiMethod(JoinPoint joinPoint) {
        if (propertyService.getBoolean(DELAY_SIMULATION_ENABLED)) {
            try {
                Thread.sleep(propertyService.getInt(SIMULATED_DELAY_TIME));
            } catch (InterruptedException e) {
                log.warn("Interrupted!", e);
                Thread.currentThread().interrupt();
            }
        }
    }
}
