package ru.hubabank.core.integration.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.hubabank.core.exception.IntegrationException;
import ru.hubabank.core.integration.dto.UserFullDto;

@FeignClient("user")
public interface UserClient {

    @GetMapping("/users/my")
    UserFullDto getUserInfoAboutMe(@RequestHeader("Authorization") String token) throws IntegrationException;
}
