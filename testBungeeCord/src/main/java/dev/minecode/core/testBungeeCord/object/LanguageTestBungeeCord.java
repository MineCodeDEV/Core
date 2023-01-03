package dev.minecode.core.testBungeeCord.object;

public enum LanguageTestBungeeCord {

    languageCommandTest("language", "command", "test");

    private final String[] path;

    LanguageTestBungeeCord(String... path) {
        this.path = path;
    }

    public String[] getPath() {
        return path;
    }

}
