package ru.greenpix.hubabank.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ResponseUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T getContentAsObject(MvcResult mvcResult, Class<T> type) throws IOException {
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Assertions.assertThat(content).isNotEmpty();
        return objectMapper.readValue(content, type);
    }
}
