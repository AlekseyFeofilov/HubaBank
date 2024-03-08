package ru.hubabank.core.integration.object;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class UserInfo {

    private final UUID id;
    private final String phone;
    private final List<String> privileges;
    private final List<String> roles;
    private final boolean blocked;
}
