package ru.hubabank.core.versioning;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class ApiVersionRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    private final String prefix;

    @Override
    protected RequestMappingInfo getMappingForMethod(@NotNull Method method, @NotNull Class<?> handlerType) {
        RequestMappingInfo info = super.getMappingForMethod(method, handlerType);
        if (info == null) {
            return null;
        }

        ApiVersionRange annotation = findAnnotation(method, handlerType);
        if (annotation == null) {
            return info;
        }

        RequestCondition<?> condition = getCustomMethodCondition(method);
        info = createApiVersionInfo(annotation, condition).combine(info);

        return info;
    }

    private ApiVersionRange findAnnotation(Method method, Class<?> handlerType) {
        ApiVersionRange annotation = AnnotationUtils.findAnnotation(method, ApiVersionRange.class);
        if (annotation == null && handlerType != null) {
            annotation = AnnotationUtils.findAnnotation(handlerType, ApiVersionRange.class);
        }
        return annotation;
    }

    private String[] createPaths(ApiVersionRange annotation) {
        List<String> paths = ApiVersion.getVersionsBetween(annotation.min().getNumber(), annotation.max().getNumber())
                .stream()
                .flatMap(version -> Arrays.stream(version.getPath()))
                .map(path -> path.replace("{prefix}", prefix))
                .toList();
        return paths.toArray(String[]::new);
    }

    private RequestMappingInfo createApiVersionInfo(ApiVersionRange annotation, RequestCondition<?> customCondition) {
        String[] paths = createPaths(annotation);
        return RequestMappingInfo.paths(paths).customCondition(customCondition).build();
    }
}
