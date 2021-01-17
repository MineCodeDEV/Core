package dev.minecode.core.common.api.manager;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.ReplaceManager;
import dev.minecode.core.api.object.CorePlayer;
import dev.minecode.core.api.object.Language;

public class ReplaceManagerProvider implements ReplaceManager {

    private String message;

    public ReplaceManagerProvider(String message) {
        this.message = message;
    }

    public ReplaceManagerProvider(String iso_code, String... path) {
        this.message = CoreAPI.getInstance().getLanguageManager().getString(iso_code, path);
    }

    public ReplaceManager replaceAll(String toReplace, String replaceWith) {
        if (message != null)
            this.message = this.message.replaceAll(toReplace, replaceWith);
        return this;
    }

    public ReplaceManager chatcolorColor() {
        return replaceAll("&1", "§1")
                .replaceAll("&2", "§2")
                .replaceAll("&3", "§3")
                .replaceAll("&4", "§4")
                .replaceAll("&5", "§5")
                .replaceAll("&6", "§6")
                .replaceAll("&7", "§7")
                .replaceAll("&8", "§8")
                .replaceAll("&9", "§9")
                .replaceAll("&a", "§a")
                .replaceAll("&b", "§b")
                .replaceAll("&c", "§c")
                .replaceAll("&d", "§d")
                .replaceAll("&e", "§e")
                .replaceAll("&f", "§f");
    }

    public ReplaceManager chatcolorFormat() {
        return replaceAll("&l", "§l")
                .replaceAll("&m", "§m")
                .replaceAll("&n", "§n")
                .replaceAll("&o", "§o")
                .replaceAll("&r", "§r");
    }

    public ReplaceManager chatcolorMagic() {
        return replaceAll("&k", "§k");
    }

    public ReplaceManager chatcolorAll() {
        return chatcolorColor()
                .chatcolorFormat()
                .chatcolorMagic();
    }

    public ReplaceManager corePlayer(CorePlayer corePlayer, String replacement) {
        return replaceAll("%" + replacement + "UUID%", String.valueOf(corePlayer.getUuid()))
                .replaceAll("%" + replacement + "Name%", corePlayer.getName());
    }

    public ReplaceManager language(Language language, String replacement) {
        return replaceAll("%" + replacement + "IsoCode%", language.getIso_code())
                .replaceAll("%" + replacement + "Name%", language.getName())
                .replaceAll("%" + replacement + "DisplayName%", language.getDisplayname())
                .replaceAll("%" + replacement + "Slot%", String.valueOf(language.getSlot()))
                .replaceAll("%" + replacement + "Texture%", language.getTexture());
    }

    public String getMessage() {
        return message;
    }
}
