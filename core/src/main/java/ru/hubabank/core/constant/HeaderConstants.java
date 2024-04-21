package ru.hubabank.core.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HeaderConstants {

    public static final String API_KEY_HEADER = "X-API-KEY";
    public static final String REQUEST_ID_HEADER = "requestId";
    public static final String IDENTITY_KEY_HEADER = "identityKey";
}
