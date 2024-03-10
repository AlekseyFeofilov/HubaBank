package org.huba.users.model;

import lombok.Getter;
import org.huba.users.utils.MyConstants;

public enum TokenType {
    PASSWORD_CHANGE(true, 20, MyConstants.ONE_DAY_MILLIS),
    REFRESH(false, 8, MyConstants.ONE_DAY_MILLIS),
    CONFIRM(true, 15, MyConstants.ONE_DAY_MILLIS);

    @Getter
    private final boolean alphabet;
    @Getter
    private final int length;
    @Getter
    private final long timeToExp;

    TokenType(boolean alphabet, int length, long timeToExp) {
        this.alphabet = alphabet;
        this.length = length;
        this.timeToExp = timeToExp;
    }
}
