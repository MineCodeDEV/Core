package dev.minecode.core.spigot.listener;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.spigot.CoreSpigot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginListener implements Listener {

    @EventHandler
    public void handlePluginDisable(PluginDisableEvent event) {
        CorePlugin corePlugin = CoreAPI.getInstance().getPluginManager().getPlugin(event.getPlugin().getName());
        if (corePlugin == null) return;

        CoreAPI.getInstance().getPluginManager().unregisterPlugin(corePlugin);

        if (!CoreSpigot.getInstance().getMainClass().equals(event.getPlugin())) return;

        if (CoreAPI.getInstance().getPluginManager().getPlugins().isEmpty()) return;

        CorePlugin newCorePlugin = CoreAPI.getInstance().getPluginManager().getPlugins().get(0);
        //TODO: Nicht sicher, ob man so eine Class zu JavaPlugin casten kann
        CoreSpigot.getInstance().setMainClass(JavaPlugin.getPlugin(newCorePlugin.getMainClass()));
    }

}
