package dev.minecode.core.api.manager;

import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.Statement;

public interface DatabaseManager {

    boolean connect();

    boolean disconnect();

    @Nullable Connection getConnection();

    @Nullable Statement getStatement();

    boolean isUsingSQL();

}
