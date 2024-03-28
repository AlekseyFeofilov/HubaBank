
package ru.hubabank.core.configuration;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import ru.hubabank.core.versioning.ApiVersionRequestMappingHandlerMapping;

import static ru.hubabank.core.constant.PathConstants.PREFIX_PATH;

@Configuration
public class WebMvcRegistrationsConfiguration implements WebMvcRegistrations {

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new ApiVersionRequestMappingHandlerMapping(PREFIX_PATH);
    }
}
