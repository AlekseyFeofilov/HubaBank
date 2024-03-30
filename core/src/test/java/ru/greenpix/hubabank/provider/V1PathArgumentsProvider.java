package ru.greenpix.hubabank.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import ru.hubabank.core.versioning.ApiVersion;

import java.util.Arrays;
import java.util.stream.Stream;

import static ru.hubabank.core.constant.PathConstants.PREFIX_PATH;

public class V1PathArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        return Stream.of(ApiVersion.VERSION_1)
                .flatMap(version -> Arrays.stream(version.getPath()))
                .map(version -> version.replace("{prefix}", "/" + PREFIX_PATH))
                .map(Arguments::of);
    }
}
