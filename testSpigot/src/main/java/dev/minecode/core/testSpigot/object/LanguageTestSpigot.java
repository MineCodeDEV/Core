package dev.minecode.core.testSpigot.object;

import dev.minecode.core.api.object.LanguageAbstract;

public enum LanguageTestSpigot implements LanguageAbstract {
    languageCommandTest("language", "command", "test");

    private final String[] path;

    LanguageTestSpigot(String... path) {
        this.path = path;
    }

    public String[] getPath() {
        return path;
    }
}
