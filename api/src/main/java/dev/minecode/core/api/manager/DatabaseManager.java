package dev.minecode.core.api.manager;

import java.sql.Connection;
import java.sql.Statement;

public interface DatabaseManager {

    void connect();

    void disconnect();

    Connection getConnection();

    Statement getStatement();

}
