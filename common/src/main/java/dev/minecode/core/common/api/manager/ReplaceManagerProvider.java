package dev.minecode.core.common.api.manager;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.ReplaceManager;
import dev.minecode.core.api.object.CorePlayer;
import dev.minecode.core.api.object.Language;
import dev.minecode.core.api.object.LanguageAbstract;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;

public class ReplaceManagerProvider implements ReplaceManager {

    private String message;

    public ReplaceManagerProvider(String message) {
        this.message = message;
    }

    public ReplaceManagerProvider(BaseComponent[] message) {
        this.message = ComponentSerializer.toString(message);
    }

    public ReplaceManagerProvider(Language language, LanguageAbstract path) {
        this.message = CoreAPI.getInstance().getLanguageManager().getString(language, path);
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
        if (message.startsWith("text:{"))
            return TextComponent.toLegacyText(ComponentSerializer.parse(message));
        return message;
    }

    public BaseComponent[] getBaseMessage() {
        if (message.startsWith("text:{"))
            return ComponentSerializer.parse(message);
        return TextComponent.fromLegacyText(message);
    }
}
