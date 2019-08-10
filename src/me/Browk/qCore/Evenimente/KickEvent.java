package me.Browk.qCore.Evenimente;

import me.Browk.qCore.Custom.Jucator;
import me.Browk.qCore.Utile.Utile;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.player.*;
import org.bukkit.event.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class KickEvent implements Listener, Utile {
    @EventHandler
    public void onQuit(final PlayerKickEvent e) {
        e.setLeaveMessage((String) null);
        final Player p = e.getPlayer();
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
