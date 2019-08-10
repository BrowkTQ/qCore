package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class Spawn implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.spawn")) {
            this.noPermission(sender);
            return true;
        }
        if (!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        final Player p = (Player) sender;
        if (argument.length == 0) {
            p.teleport(this.getSpawn());
            this.sendMessage(p, this.getMessage(this.getLanguage(p), "SPAWN TP"));
            return true;
        }
        if (!sender.hasPermission("essentials.spawn.others")) {
            this.noPermission(sender);
            return true;
        }
        final Player t = Bukkit.getPlayer(argument[0]);
        if (t == null) {
            this.noPlayer(sender);
            return true;
        }
        t.teleport(this.getSpawn());
        this.sendMessage(t, this.getMessage(this.getLanguage(t), "SPAWN TP"));
        return true;
    }
}
