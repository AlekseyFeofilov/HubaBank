package ru.hubabank.core.versioning;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum ApiVersion {
    VERSION_1(1, "", "{prefix}/v1"),
    VERSION_2(2),
    VERSION_3(3),
    MIN(VERSION_1),
    MAX(VERSION_3);

    private final int number;
    private final String[] path;

    ApiVersion(ApiVersion apiVersion) {
        this(apiVersion.number, apiVersion.path);
    }

    ApiVersion(int number) {
        this(number, String.format("{prefix}/v%d", number));
    }

    ApiVersion(int number, String... path) {
        this.number = number;
        this.path = path;
    }

    public static List<ApiVersion> getVersions() {
        return Arrays.stream(values())
                .filter(e -> e != MIN && e != MAX)
                .toList();
    }

    public static List<ApiVersion> getVersionsBetween(int min, int max) {
        return getVersions().stream()
                .filter(e -> e.getNumber() >= min && e.getNumber() <= max)
                .toList();
    }
}
