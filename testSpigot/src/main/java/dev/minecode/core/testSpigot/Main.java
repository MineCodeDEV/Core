package dev.minecode.core.testSpigot;

import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.spigot.CoreSpigot;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static CorePlugin testPlugin;

    public static CorePlugin getCorePlugin() {
        return testPlugin;
    }

    @Override
    public void onEnable() {
        testPlugin = CoreSpigot.getInstance().registerPlugin(this, true);
        getCommand("test").setExecutor(new TestCommand());
    }

    @Override
    public void onDisable() {
    }


}
