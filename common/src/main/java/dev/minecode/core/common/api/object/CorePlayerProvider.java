package dev.minecode.core.common.api.object;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.object.CorePlayer;
import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.api.object.FileObject;
import dev.minecode.core.api.object.Language;
import dev.minecode.core.common.CoreCommon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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

    private static final FileObject dataFileObject = CoreAPI.getInstance().getFileManager().getPlayers();
    private static ConfigurationNode dataRoot;

    private UUID uuid;
    private String name;
    private String languageIsocode;
    private boolean exists = false;
    private Statement statement;
    private ResultSet resultSet;

    public CorePlayerProvider(@NotNull UUID uuid) {
        makeInstances();
        this.uuid = uuid;
        load();
    }

    public CorePlayerProvider(@NotNull String name) {
        makeInstances();
        this.uuid = getUuid(name);
        load();
    }

    private static boolean create(@NotNull UUID uuid, @NotNull String name) {
        try {
            if (CoreAPI.getInstance().getDatabaseManager().isUsingSQL()) {
                CoreAPI.getInstance().getDatabaseManager().getStatement().executeUpdate("INSERT INTO minecode_players (UUID, NAME, LANGUAGE) VALUES ('" + uuid + "', '" + name + "', " + null + ")");
                return true;
            }

            dataRoot.node(uuid.toString(), "name").set(name);
            return true;
        } catch (SQLException | SerializationException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static UUID getUuid(@NotNull String name) {
        if (name.equals(consoleName)) return consoleUUID;

        try {
            if (CoreAPI.getInstance().getDatabaseManager().isUsingSQL()) {
                ResultSet resultSet = CoreAPI.getInstance().getDatabaseManager().getStatement().executeQuery("SELECT UUID FROM minecode_players WHERE UPPER(NAME) = UPPER('" + name + "')");
                if (resultSet.next())
                    return UUID.fromString(resultSet.getString("UUID"));
            } else
                for (Map.Entry<Object, ? extends ConfigurationNode> uuidNode : dataRoot.childrenMap().entrySet())
                    if (uuidNode.getValue().node("name").getString().equalsIgnoreCase(name))
                        return UUID.fromString((String) uuidNode.getKey());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return CoreCommon.getInstance().getUuidFetcher().getUUID(name);
    }

    public static String getName(@NotNull UUID uuid) {
        if (uuid == consoleUUID) return consoleName;

        try {
            if (CoreAPI.getInstance().getDatabaseManager().isUsingSQL()) {
                ResultSet resultSet = CoreAPI.getInstance().getDatabaseManager().getStatement().executeQuery("SELECT NAME FROM minecode_players WHERE UUID = '" + uuid + "'");
                if (resultSet.next())
                    return resultSet.getString("NAME");
            } else return dataRoot.node(uuid.toString(), "name").getString();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return CoreCommon.getInstance().getUuidFetcher().getName(uuid);
    }

    public void makeInstances() {
        if (CoreAPI.getInstance().getDatabaseManager().isUsingSQL())
            statement = CoreAPI.getInstance().getDatabaseManager().getStatement();
        else dataRoot = dataFileObject.getRoot();
    }

    public void load() {
        if (uuid == null) return;

        if (uuid == consoleUUID || (name != null && name.equalsIgnoreCase(consoleName))) {
            uuid = consoleUUID;
            name = consoleName;
            exists = true;
            return;
        }

        try {
            if (CoreAPI.getInstance().getDatabaseManager().isUsingSQL()) {
                resultSet = statement.executeQuery("SELECT UUID FROM minecode_players WHERE UUID = '" + uuid.toString() + "'");
                exists = resultSet.next();
            } else exists = !dataRoot.node(String.valueOf(uuid)).empty();

            if (!exists) {
                if (name == null) name = getName(uuid);
                if (uuid != null && name != null) exists = create(uuid, name);
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
                if (CoreAPI.getInstance().getDatabaseManager().isUsingSQL()) {
                    resultSet = statement.executeQuery("SELECT * FROM minecode_players WHERE UUID = '" + uuid + "'");
                    if (resultSet.next()) {
                        name = resultSet.getString("NAME");
                        languageIsocode = resultSet.getString("LANGUAGE");
                        return true;
                    }
                } else {
                    name = dataRoot.node(uuid.toString(), "name").getString();
                    languageIsocode = dataRoot.node(uuid.toString(), "language").getString();
                    return true;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean save() {
        if (uuid == consoleUUID || (name != null && name.equalsIgnoreCase(consoleName))) return true;

        if (exists) {
            try {
                if (CoreAPI.getInstance().getDatabaseManager().isUsingSQL()) {
                    resultSet.updateString("UUID", String.valueOf(uuid));
                    resultSet.updateString("NAME", name);
                    resultSet.updateString("LANGUAGE", languageIsocode);
                    resultSet.updateRow();
                } else {
                    dataRoot.node(uuid.toString(), "name").set(name);
                    dataRoot.node(uuid.toString(), "language").set(languageIsocode);
                }
                return true;
            } catch (SQLException | SerializationException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public @NotNull UUID getUuid() {
        return uuid;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public void setName(@NotNull String name) {
        this.name = name;
    }

    @Override
    public @NotNull Language getLanguage(@NotNull CorePlugin corePlugin) {
        if (languageIsocode != null)
            return CoreAPI.getInstance().getLanguageManager().getLanguage(corePlugin, languageIsocode);
        return CoreAPI.getInstance().getLanguageManager().getDefaultLanguage(corePlugin);
    }

    @Override
    public String getLanguageIsoCode() {
        return languageIsocode;
    }

    @Override
    public void setLanguage(@Nullable String isocode) {
        this.languageIsocode = isocode;
    }

    @Override
    public boolean isLanguageEmpty() {
        return languageIsocode == null;
    }

    public boolean isExists() {
        return exists;
    }
}
