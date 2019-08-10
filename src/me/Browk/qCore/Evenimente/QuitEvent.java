package me.Browk.qCore.Evenimente;

import me.Browk.qCore.Utile.*;
import org.bukkit.event.player.*;
import java.util.*;
import java.text.*;
import org.bukkit.*;
import me.Browk.qCore.Custom.*;
import org.bukkit.entity.*;
import java.sql.*;
import org.bukkit.event.*;

public class QuitEvent implements Listener, Utile {
    @EventHandler
    public void onQuit(final PlayerQuitEvent e) {
        final Player p = e.getPlayer();
        e.setQuitMessage((String) null);
        final Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Europe/Bucharest"));
        final SimpleDateFormat dt = new SimpleDateFormat("hh:mm dd/MM/yyyy ");
        final String what = "Ip='" + p.getAddress().toString().replace("/", "") + "',LastOnline='" + dt.format(c.getTime()) + "',LastLocation='" + this.seterilizeLocation(p.getLocation()) + "',Time='" + p.getStatistic(Statistic.PLAY_ONE_TICK) + "'";
        try {
            final Connection connection = this.getInstance().mysql.getConnection();
            final PreparedStatement prepared = connection.prepareStatement("UPDATE " + this.getInstance().server + " SET " + what + " WHERE Player= '" + p.getName() + "';");
            prepared.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        getJucator(p).setMoney(getJucator(p).getMoney());
        Jucator.jucatorObjects.remove(this.getJucator(p));
    }
}
