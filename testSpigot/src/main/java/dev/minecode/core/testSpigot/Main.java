package dev.minecode.core.testSpigot;

import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.spigot.CoreSpigot;
import dev.minecode.core.testSpigot.command.TestCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static CorePlugin testPlugin;

    private static Main instance;

    public static CorePlugin getCorePlugin() {
        return testPlugin;
    }

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onEnable() {
        instance = this;
        testPlugin = CoreSpigot.getInstance().registerPlugin(this, true);
        getCommand("test").setExecutor(new TestCommand());
    }
}
