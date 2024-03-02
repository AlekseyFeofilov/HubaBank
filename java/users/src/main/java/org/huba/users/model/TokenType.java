package org.huba.users.model;

public enum TokenType {
    REFRESH("REFRESH");

    TokenType(String value) {
        this.value = value;
    }

    private final String value;
}
