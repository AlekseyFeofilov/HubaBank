package ru.hubabank.core.integration.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.hubabank.core.error.ErrorType;
import ru.hubabank.core.exception.IntegrationException;
import ru.hubabank.core.integration.client.UserClient;
import ru.hubabank.core.integration.dto.UserFullDto;
import ru.hubabank.core.integration.object.UserInfo;
import ru.hubabank.core.mapper.UserMapper;

import javax.security.sasl.AuthenticationException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserClient userClient;
    private final UserMapper userMapper;

    public @NotNull UserInfo fetchUserInfo(@NotNull String token) throws AuthenticationException {
        try {
            UserFullDto response = userClient.getUserInfoAboutMe(token);
            return userMapper.mapDtoToObject(response);
        } catch (IntegrationException e) {
            if (e.getStatus() == HttpStatus.UNAUTHORIZED) {
                throw new AuthenticationException();
            }
            throw ErrorType.BAD_GATEWAY.createException(e);
        }
    }
}
