package me.Browk.qCore.MySQL;

import me.Browk.qCore.Utile.*;
import me.Browk.qCore.*;
import java.util.*;
import java.sql.*;

public class Database implements Utile {
    public static Database getData() {
        return Main.intstanta.database;
    }

    public void createTable(final String name, final List<String> integers, final List<String> strings) {
        String integerliste = "";
        for (final String inte : integers) {
            integerliste = String.valueOf(integerliste) + ", " + inte + " INT(255)";
        }
        String stringliste = "";
        for (final String string : strings) {
            stringliste = String.valueOf(stringliste) + ", " + string + " VARCHAR(255)";
        }
        try {
            final Connection connection = this.getInstance().mysql.getConnection();
            final PreparedStatement prepared = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + name + " (id INT AUTO_INCREMENT PRIMARY KEY" + stringliste + integerliste + ")");
            prepared.execute();
            prepared.close();
        } catch (SQLException ex) {
        }
    }

    public void setInt(final String tablename, final String column, final String where, final String what, final int integer) {
        try {
            final Connection connection = this.getInstance().mysql.getConnection();
            final PreparedStatement prepared = connection.prepareStatement("UPDATE " + tablename + " SET " + column + "= '" + integer + "' WHERE " + where + "= '" + what + "';");
            prepared.execute();
            prepared.close();
        } catch (SQLException ex) {
        }
    }

    public void setString(final String tablename, final String column, final String where, final String what, final String integer) {
        try {
            final Connection connection = this.getInstance().mysql.getConnection();
            final PreparedStatement prepared = connection.prepareStatement("UPDATE " + tablename + " SET " + column + "= '" + integer + "' WHERE " + where + "= '" + what + "';");
            prepared.execute();
            prepared.close();
        } catch (SQLException ex) {
        }
    }

    public String getString(final String tablename, final String column, final String where, final String what) {
        String string = "Default";
        try {
            final Connection connection = this.getInstance().mysql.getConnection();
            final PreparedStatement prepared = connection.prepareStatement("SELECT * FROM " + tablename + " WHERE " + where + "= '" + what + "';");
            final ResultSet rs = prepared.executeQuery();
            if (rs.next()) {
                string = rs.getString(column);
            }
            rs.close();
            prepared.close();
        } catch (SQLException ex) {
        }
        return string;
    }

    public Integer getInt(final String tablename, final String column, final String where, final String what) {
        int i = 0;
        try {
            final Connection connection = this.getInstance().mysql.getConnection();
            final PreparedStatement prepared = connection.prepareStatement("SELECT * FROM " + tablename + " WHERE " + where + "= '" + what + "';");
            final ResultSet rs = prepared.executeQuery();
            if (rs.next()) {
                i = rs.getInt(column);
            }
            rs.close();
            prepared.close();
        } catch (SQLException ex) {
        }
        return i;
    }

    public boolean isRegistered(final String tablename, final String where, final String what) {
        boolean is = false;
        try {
            final Connection connection = this.getInstance().mysql.getConnection();
            final PreparedStatement prepared = connection.prepareStatement("SELECT * FROM `" + tablename + "` WHERE `Player` LIKE '" + what + "';");
            final ResultSet rs = prepared.executeQuery();
            if (rs.next()) {
                is = true;
            }
            rs.close();
            prepared.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return is;
    }

    public void addColum(final String name, final boolean str) {
        String type = "";
        if (str) {
            type = "CHAR";
        } else {
            type = "INT";
        }
        try {
            final Connection connection = this.getInstance().mysql.getConnection();
            final PreparedStatement prepared = connection.prepareStatement("ALTER TABLE `Global` ADD `" + name + "` " + type + " NOT NULL ;");
            prepared.execute();
        } catch (SQLException ex) {
        }
    }

    public void register(final String tablename, final List<String> values) {
        String names = "";
        String names2 = "";
        for (final String value : values) {
            final String[] valuesplit = value.split("-");
            names = String.valueOf(names) + valuesplit[0] + ", ";
            names2 = String.valueOf(names2) + "'" + valuesplit[1] + "', ";
        }
        names = names.substring(0, names.length() - 2);
        names2 = names2.substring(0, names2.length() - 2);
        try {
            final Connection connection = this.getInstance().mysql.getConnection();
            final PreparedStatement prepared = connection.prepareStatement("INSERT INTO " + tablename + " (" + names + ") VALUES (" + names2 + ")");
            prepared.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
