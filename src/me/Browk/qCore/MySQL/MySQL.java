package me.Browk.qCore.MySQL;

import me.Browk.qCore.Utile.*;
import java.sql.*;

public class MySQL implements Utile {
    private static Connection conn;

    public void connect(final String host, final String database, final int port, final String user, final String password) {
        if (!this.isConnected()) {
            try {
                MySQL.conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&useSSL=false&?useUnicode=yes", user, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void disconnect() {
        if (this.isConnected()) {
            try {
                MySQL.conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isConnected() {
        try {
            return MySQL.conn != null && !MySQL.conn.isClosed() && !MySQL.conn.isValid(5);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Connection getConnection() {
        return MySQL.conn;
    }
}
