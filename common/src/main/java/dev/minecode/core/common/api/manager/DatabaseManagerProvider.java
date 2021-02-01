package dev.minecode.core.common.api.manager;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.DatabaseManager;
import org.spongepowered.configurate.ConfigurationNode;

import java.sql.*;

public class DatabaseManagerProvider implements DatabaseManager {

    private Connection connection;
    private String host, database, username, password;
    private int port;
    private boolean sql;
    private Statement statement;

    public DatabaseManagerProvider() {
        setData();
        if (sql) {
            connect();
            checkTables();
        }
    }

    private void setData() {
        ConfigurationNode conf = CoreAPI.getInstance().getFileManager().getConfig().getConf();
        host = conf.node("database", "host").getString();
        port = conf.node("database", "port").getInt();
        database = conf.node("database", "database").getString();
        username = conf.node("database", "username").getString();
        password = conf.node("database", "password").getString();
        sql = conf.node("database", "enabled").getBoolean();
    }

    @Override
    public void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
        } catch (SQLException throwables) {
            System.out.println("A connection to the MySQL database could not be established!");
        }
    }

    @Override
    public void disconnect() {
        try {
            if (connection != null || !connection.isClosed() || connection.isValid(2))
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void checkTables() {
        try {
            getStatement().executeUpdate("CREATE TABLE IF NOT EXISTS minecode_players (ID INT, UUID VARCHAR (37), NAME VARCHAR (16), LANGUAGE VARCHAR (5), CONSTRAINT uuid_pk PRIMARY KEY (ID))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() {
        try {
            if (CoreAPI.getInstance().isUsingSQL()) {
                if (connection == null || connection.isClosed() || !connection.isValid(2))
                    connect();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    @Override
    public Statement getStatement() {
        getConnection();
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return statement;
    }
}
