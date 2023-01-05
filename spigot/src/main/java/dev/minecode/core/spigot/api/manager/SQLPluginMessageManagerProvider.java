package dev.minecode.core.spigot.api.manager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.SQLPluginMessageManager;
import dev.minecode.core.spigot.CoreSpigot;
import dev.minecode.core.spigot.event.MineCodePluginMessageReceiveEvent;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.UUID;

public class SQLPluginMessageManagerProvider implements SQLPluginMessageManager {

    private int interval;
    private BukkitTask bukkitTask;

    private Statement statement;
    private Gson gson;
    private Type type;

    public SQLPluginMessageManagerProvider() {
        if (!CoreAPI.getInstance().getDatabaseManager().isUsingSQL()) return;
        makeInstances();
    }

    private void makeInstances() {
        interval = CoreAPI.getInstance().getFileManager().getNetwork().getRoot().node("interval").getInt(20);
        statement = CoreAPI.getInstance().getDatabaseManager().getStatement();
        gson = new Gson();
        type = new TypeToken<HashMap<String, String>>() {
        }.getType();
    }

    @Override
    public void startChecking() {
        // Falls noch ein anderer Timer läuft, wird dieser abgebrochen
        cancelChecking();
        bukkitTask = Bukkit.getScheduler().runTaskTimerAsynchronously(CoreSpigot.getInstance(), () -> checkForPluginMessages(), interval, interval);
    }

    @Override
    public boolean cancelChecking() {
        if (bukkitTask != null) {
            if (Bukkit.getScheduler().isCurrentlyRunning(bukkitTask.getTaskId()) || Bukkit.getScheduler().isQueued(bukkitTask.getTaskId())) {
                bukkitTask.cancel();
                return true;
            }
        }
        return false;
    }

    @Override
    public void checkForPluginMessages() {
        if (!CoreAPI.getInstance().getNetworkManager().isServernameSet()) return;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM minecode_pluginmessages WHERE TARGETSERVER = '" + CoreAPI.getInstance().getNetworkManager().getServername() + "' ORDER BY TIME ASC");

            while (resultSet.next()) {
                String channel = resultSet.getString("CHANNEL");
                String senderserver = resultSet.getString("SENDERSERVER");
                String message = resultSet.getString("MESSAGE");
                Bukkit.getScheduler().runTask(CoreSpigot.getInstance(), () ->
                        Bukkit.getPluginManager().callEvent(new MineCodePluginMessageReceiveEvent(channel, senderserver, gson.fromJson(message, type))));
                resultSet.deleteRow();
            }
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendPluginMessage(String targetServer, String channel, HashMap<String, String> message) {
        try {
            if (CoreAPI.getInstance().getDatabaseManager().isUsingSQL()) {
                String messageJson = gson.toJson(message);
                CoreAPI.getInstance().getDatabaseManager().getStatement().executeUpdate("INSERT INTO minecode_pluginmessages (ID, SENDERSERVER, TARGETSERVER, CHANNEL, MESSAGE) VALUES ('" + getRandomID() + "', '" + CoreAPI.getInstance().getNetworkManager().getServername() + "', '" + targetServer + "', '" + channel + "', '" + messageJson + "')");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getRandomID() {
        String rndID = UUID.randomUUID().toString();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM minecode_pluginmessages WHERE ID = '" + rndID + "'");
            if (resultSet.next()) return getRandomID();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rndID;
    }

    @Override
    public void setInterval(int interval) {
        this.interval = interval;
    }

}
