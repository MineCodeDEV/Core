package dev.minecode.core.testSpigot;

import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.spigot.CoreSpigot;
import dev.minecode.core.testSpigot.command.TestSpigotCommand;
import dev.minecode.core.testSpigot.listener.MCPMReceiveListener;
import org.bukkit.plugin.java.JavaPlugin;

public class TestSpigot extends JavaPlugin {
    private static CorePlugin testSpigotPlugin;

    private static TestSpigot instance;

    public static CorePlugin getCorePlugin() {
        return testSpigotPlugin;
    }

    public static TestSpigot getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onEnable() {
        instance = this;
        testSpigotPlugin = CoreSpigot.getInstance().registerPlugin(this, true);
        getCommand("testspigot").setExecutor(new TestSpigotCommand());
        new MCPMReceiveListener();
    }
}
