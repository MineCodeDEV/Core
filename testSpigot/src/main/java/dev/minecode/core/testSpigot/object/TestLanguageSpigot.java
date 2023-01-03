package dev.minecode.core.testSpigot.object;

import dev.minecode.core.api.object.LanguageAbstract;

public enum TestLanguageSpigot implements LanguageAbstract {
    languageCommandTest("language", "command", "test");

    private final String[] path;

    TestLanguageSpigot(String... path) {
        this.path = path;
    }

    public String[] getPath() {
        return path;
    }
}
