package dev.minecode.core.bungeecord.api.object;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.object.CorePlayer;
import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.api.object.FileObject;
import dev.minecode.core.api.object.Language;
import dev.minecode.core.common.CoreCommon;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.UUID;

public class CorePlayerProvider implements CorePlayer {

    private static final UUID consoleUUID = new UUID(0, 0);
    private static final String consoleName = "CONSOLE";

    private static final FileObject playersFileObject = CoreAPI.getInstance().getFileManager().getPlayers();
    private static ConfigurationNode playersConf;

    private UUID uuid;
    private String name;
    private String languageIsocode;
    private boolean exists = false;
    private Statement statement;
    private ResultSet resultSet;

    public CorePlayerProvider(UUID uuid) {
        makeInstances();
        this.uuid = uuid;
        load();
    }

    public CorePlayerProvider(String name) {
        makeInstances();
        this.uuid = getUuid(name);
        load();
    }

    private static boolean create(UUID uuid, String name, String languageIsocode) {
        try {
            if (CoreAPI.getInstance().isUsingSQL()) {
                String tempLanguageIsocode = null;
                if (languageIsocode != null)
                    tempLanguageIsocode = "'" + languageIsocode + "'";
                CoreAPI.getInstance().getDatabaseManager().getStatement().executeUpdate("INSERT INTO minecode_players (UUID, NAME, LANGUAGE) VALUES ('" + uuid.toString() + "', '" + name + "', " + tempLanguageIsocode + ")");
                return true;
            }

            playersConf.node(uuid.toString(), "name").set(name);
            playersConf.node(uuid.toString(), "language").set(languageIsocode);
            return true;
        } catch (SQLException | SerializationException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static UUID getUuid(String name) {
        if (name != null && name.equals(consoleName)) return consoleUUID;

        ProxiedPlayer proxiedPlayer;
        if ((proxiedPlayer = ProxyServer.getInstance().getPlayer(name)) != null)
            return proxiedPlayer.getUniqueId();

        try {
            if (CoreAPI.getInstance().isUsingSQL()) {
                ResultSet resultSet = CoreAPI.getInstance().getDatabaseManager().getStatement().executeQuery("SELECT UUID FROM minecode_players WHERE UPPER(NAME) = UPPER('" + name + "')");
                if (resultSet.next())
                    return UUID.fromString(resultSet.getString("UUID"));
            } else
                for (Map.Entry<Object, ? extends ConfigurationNode> uuidNode : playersConf.childrenMap().entrySet())
                    if (uuidNode.getValue().node("name").getString().equalsIgnoreCase(name))
                        return UUID.fromString((String) uuidNode.getKey());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return CoreCommon.getInstance().getUuidFetcher().getUUID(name);
    }

    public static String getName(UUID uuid) {
        if (uuid == consoleUUID) return consoleName;

        ProxiedPlayer proxiedPlayer;
        if ((proxiedPlayer = ProxyServer.getInstance().getPlayer(uuid)) != null)
            return proxiedPlayer.getName();

        try {
            if (CoreAPI.getInstance().isUsingSQL()) {
                ResultSet resultSet = CoreAPI.getInstance().getDatabaseManager().getStatement().executeQuery("SELECT NAME FROM minecode_players WHERE UUID = '" + uuid + "'");
                if (resultSet.next())
                    return resultSet.getString("NAME");
            } else return playersConf.node(uuid.toString(), "name").getString();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return CoreCommon.getInstance().getUuidFetcher().getName(uuid);
    }

    public void makeInstances() {
        if (CoreAPI.getInstance().isUsingSQL())
            statement = CoreAPI.getInstance().getDatabaseManager().getStatement();
        else
            playersConf = playersFileObject.getConf();
    }

    public void load() {
        if (uuid == null) {
            exists = false;
            return;
        }

        if (uuid == consoleUUID || (name != null && name.equalsIgnoreCase(consoleName))) {
            uuid = consoleUUID;
            name = consoleName;
            exists = true;
            return;
        }

        try {
            if (CoreAPI.getInstance().isUsingSQL()) {
                resultSet = statement.executeQuery("SELECT UUID FROM minecode_players WHERE UUID = '" + uuid.toString() + "'");
                exists = resultSet.next();
            } else
                exists = !playersConf.node(String.valueOf(uuid)).empty();

            if (!exists) {
                name = getName(uuid);

                if (uuid != null && name != null) exists = create(uuid, name, null);
            } else reload();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public boolean reload() {
        if (uuid == consoleUUID || (name != null && name.equalsIgnoreCase(consoleName))) {
            uuid = consoleUUID;
            name = consoleName;
            exists = true;
            return true;
        }

        if (exists) {
            try {
                if (CoreAPI.getInstance().isUsingSQL()) {
                    resultSet = statement.executeQuery("SELECT * FROM minecode_players WHERE UUID = '" + uuid + "'");
                    if (resultSet.next()) {
                        name = resultSet.getString("NAME");
                        languageIsocode = resultSet.getString("LANGUAGE");
                        return true;
                    }
                } else {
                    name = playersConf.node(uuid.toString(), "name").getString();
                    languageIsocode = playersConf.node(uuid.toString(), "language").getString();
                    return true;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean save() {
        if (uuid == consoleUUID || (name != null && name.equalsIgnoreCase(consoleName))) return true;

        if (exists) {
            try {
                if (CoreAPI.getInstance().isUsingSQL()) {
                    resultSet.updateString("UUID", String.valueOf(uuid));
                    resultSet.updateString("NAME", name);
                    resultSet.updateString("LANGUAGE", languageIsocode);
                    resultSet.updateRow();
                } else {
                    playersConf.node(uuid.toString(), "name").set(name);
                    playersConf.node(uuid.toString(), "language").set(languageIsocode);
                }
                return true;
            } catch (SQLException | SerializationException throwables) {
                throwables.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public boolean setUuid(UUID uuid) {
        if (CoreCommon.getInstance().getUuidFetcher().getName(uuid) == null) return false;

        this.uuid = uuid;
        return true;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean setName(String name) {
        if (CoreCommon.getInstance().getUuidFetcher().getUUID(name) == null) return false;

        this.name = name;
        return true;
    }

    @Override
    public Language getLanguage(CorePlugin corePlugin) {
        if (languageIsocode != null)
            return CoreAPI.getInstance().getLanguageManager().getLanguage(corePlugin, languageIsocode);
        return CoreAPI.getInstance().getLanguageManager().getDefaultLanguage(corePlugin);
    }

    @Override
    public String getLanguageIsoCode() {
        return languageIsocode;
    }

    @Override
    public void setLanguage(String isocode) {
        this.languageIsocode = isocode;
    }

    @Override
    public boolean isLanguageEmpty() {
        return languageIsocode == null;
    }

    @Override
    public boolean isExists() {
        return exists;
    }
}
