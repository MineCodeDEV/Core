package dev.minecode.core.common.api.manager;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.ReplaceManager;
import dev.minecode.core.api.object.CorePlayer;
import dev.minecode.core.api.object.Language;
import dev.minecode.core.api.object.LanguageAbstract;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.jetbrains.annotations.NotNull;

public class ReplaceManagerProvider implements ReplaceManager {

    private String message;

    public ReplaceManagerProvider(String message) {
        this.message = message;
    }

    public ReplaceManagerProvider(BaseComponent[] message) {
        this.message = ComponentSerializer.toString(message);
    }

    public ReplaceManagerProvider(Language language, String... path) {
        this.message = CoreAPI.getInstance().getLanguageManager().getString(language, path);
    }

    public ReplaceManagerProvider(Language language, LanguageAbstract path) {
        this.message = CoreAPI.getInstance().getLanguageManager().getString(language, path);
    }

    @Override
    public @NotNull ReplaceManager replaceAll(@NotNull String toReplace, @NotNull String replaceWith) {
        if (message != null)
            this.message = this.message.replaceAll(toReplace, replaceWith);
        return this;
    }

    @Override
    public @NotNull ReplaceManager args(@NotNull String command, String[] args, @NotNull String replacement) {
        replaceAll("%" + replacement + "-" + 0 + "%", command);
        for (int i = 0; i < args.length; i++) {
            replaceAll("%" + replacement + "-" + (i + 1) + "%", args[i]);
        }
        return this;
    }

    @Override
    public @NotNull ReplaceManager chatcolorColor() {
        return replaceAll("&0", ChatColor.COLOR_CHAR + "0")
                .replaceAll("&1", ChatColor.COLOR_CHAR + "1")
                .replaceAll("&2", ChatColor.COLOR_CHAR + "2")
                .replaceAll("&3", ChatColor.COLOR_CHAR + "3")
                .replaceAll("&4", ChatColor.COLOR_CHAR + "4")
                .replaceAll("&5", ChatColor.COLOR_CHAR + "5")
                .replaceAll("&6", ChatColor.COLOR_CHAR + "6")
                .replaceAll("&7", ChatColor.COLOR_CHAR + "7")
                .replaceAll("&8", ChatColor.COLOR_CHAR + "8")
                .replaceAll("&9", ChatColor.COLOR_CHAR + "9")
                .replaceAll("&a", ChatColor.COLOR_CHAR + "a")
                .replaceAll("&A", ChatColor.COLOR_CHAR + "a")
                .replaceAll("&b", ChatColor.COLOR_CHAR + "b")
                .replaceAll("&B", ChatColor.COLOR_CHAR + "b")
                .replaceAll("&c", ChatColor.COLOR_CHAR + "c")
                .replaceAll("&C", ChatColor.COLOR_CHAR + "c")
                .replaceAll("&d", ChatColor.COLOR_CHAR + "d")
                .replaceAll("&D", ChatColor.COLOR_CHAR + "d")
                .replaceAll("&e", ChatColor.COLOR_CHAR + "e")
                .replaceAll("&E", ChatColor.COLOR_CHAR + "e")
                .replaceAll("&f", ChatColor.COLOR_CHAR + "f")
                .replaceAll("&F", ChatColor.COLOR_CHAR + "f");
    }

    @Override
    public @NotNull ReplaceManager chatcolorFormat() {
        return replaceAll("&l", ChatColor.COLOR_CHAR + "l")
                .replaceAll("&L", ChatColor.COLOR_CHAR + "l")
                .replaceAll("&m", ChatColor.COLOR_CHAR + "m")
                .replaceAll("&M", ChatColor.COLOR_CHAR + "m")
                .replaceAll("&n", ChatColor.COLOR_CHAR + "n")
                .replaceAll("&N", ChatColor.COLOR_CHAR + "n")
                .replaceAll("&o", ChatColor.COLOR_CHAR + "o")
                .replaceAll("&O", ChatColor.COLOR_CHAR + "o")
                .replaceAll("&r", ChatColor.COLOR_CHAR + "r")
                .replaceAll("&R", ChatColor.COLOR_CHAR + "r");
    }

    @Override
    public @NotNull ReplaceManager chatcolorMagic() {
        return replaceAll("&k", ChatColor.COLOR_CHAR + "k")
                .replaceAll("&K", ChatColor.COLOR_CHAR + "k");
    }

    @Override
    public @NotNull ReplaceManager chatcolorAll() {
        message = ChatColor.translateAlternateColorCodes('&', message);
        return this;
    }

    @Override
    public @NotNull ReplaceManager corePlayer(@NotNull CorePlayer corePlayer, @NotNull String replacement) {
        return replaceAll("%" + replacement + "UUID%", String.valueOf(corePlayer.getUuid()))
                .replaceAll("%" + replacement + "Name%", corePlayer.getName());
    }

    @Override
    public @NotNull ReplaceManager language(@NotNull Language language, @NotNull String replacement) {
        return replaceAll("%" + replacement + "Isocode%", language.getIsocode())
                .replaceAll("%" + replacement + "Name%", language.getName())
                .replaceAll("%" + replacement + "Displayname%", language.getDisplayname());
    }

    @Override
    public @NotNull String getMessage() {
        if (message.startsWith("text:{"))
            return TextComponent.toLegacyText(ComponentSerializer.parse(message));
        return message;
    }

    @Override
    public BaseComponent[] getBaseMessage() {
        if (message.startsWith("text:{"))
            return ComponentSerializer.parse(message);
        return TextComponent.fromLegacyText(message);
    }
}
