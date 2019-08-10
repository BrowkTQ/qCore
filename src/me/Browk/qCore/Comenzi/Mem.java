package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import java.text.*;
import me.Browk.qCore.Custom.*;
import org.bukkit.*;
import java.util.*;

public class Mem implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.mem")) {
            this.noPermission(sender);
            return true;
        }
        final DecimalFormat sdf = new DecimalFormat("##.##");
        sender.sendMessage(this.replace("&8&m--+------------------------------------+--&r"));
        sender.sendMessage(this.replace("Uptime: &e" + this.getUptime()));
        sender.sendMessage(this.replace("TPS: &e" + sdf.format(Lag.getTPS())));
        sender.sendMessage(String.valueOf(this.replace(new StringBuilder("Memorie folosita: &e").append(Runtime.getRuntime().freeMemory() / 1024L / 1024L).toString())) + " MB");
        sender.sendMessage(this.replace("Memorie maxima: &e" + String.valueOf(Runtime.getRuntime().maxMemory() / 1024L / 1024L) + " MB"));
        sender.sendMessage(this.replace("Chunks: &e" + this.getChunks()));
        sender.sendMessage(this.replace("Entitati: &e" + this.getEntities()));
        sender.sendMessage(this.replace("Jucatori: &e" + Bukkit.getOnlinePlayers().size()));
        final List<String> s = new ArrayList<String>();
        for (final World w : Bukkit.getWorlds()) {
            s.add(w.getName());
        }
        sender.sendMessage(this.replace("Worlds: &e" + this.shortList(s, "&e")));
        return true;
    }

    public int getEntities() {
        int i = 0;
        try {
            for (final World w : Bukkit.getWorlds()) {
                i += w.getEntities().size();
            }
        } catch (Exception ex) {
        }
        return i;
    }

    public int getChunks() {
        int i = 0;
        try {
            for (final World w : Bukkit.getWorlds()) {
                i += w.getLoadedChunks().length;
            }
        } catch (Exception ex) {
        }
        return i;
    }
}
