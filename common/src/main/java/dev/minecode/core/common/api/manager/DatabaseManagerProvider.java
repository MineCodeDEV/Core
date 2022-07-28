package dev.minecode.core.common.api.manager;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.DatabaseManager;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;

import java.sql.*;

public class DatabaseManagerProvider implements DatabaseManager {

    private final ConfigurationNode root;

    private Connection connection;
    private String host, database, username, password;
    private int port;
    private Statement statement;

    private final boolean usingSQL;

    public DatabaseManagerProvider() {
        root = CoreAPI.getInstance().getFileManager().getConfig().getRoot();
        usingSQL = root.node("database", "enable").getBoolean();

        setData();
        if (usingSQL) {
            connect();
            checkTables();
        }
    }

    private void setData() {
        host = root.node("database", "host").getString();
        port = root.node("database", "port").getInt();
        database = root.node("database", "database").getString();
        username = root.node("database", "username").getString();
        password = root.node("database", "password").getString();
    }

    @Override
    public boolean connect() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean disconnect() {
        try {
            if (connection != null || !connection.isClosed() || connection.isValid(2)) {
                connection.close();
                connection = null;
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public void checkTables() {
        try {
            getStatement().executeUpdate("CREATE TABLE IF NOT EXISTS minecode_players (UUID VARCHAR (37), NAME VARCHAR (16), LANGUAGE VARCHAR (5), PRIMARY KEY (UUID))");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public @Nullable Connection getConnection() {
        try {
            if (CoreAPI.getInstance().getDatabaseManager().isUsingSQL())
                if (connection == null || connection.isClosed() || !connection.isValid(2))
                    connect();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    @Override
    public @Nullable Statement getStatement() {
        try {
            statement = getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return statement;
    }

    @Override
    public boolean isUsingSQL() {
        return usingSQL;
    }
}
