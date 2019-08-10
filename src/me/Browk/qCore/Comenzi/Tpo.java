package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.event.player.*;

public class Tpo implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.tpo")) {
            this.noPermission(sender);
            return true;
        }
        if (!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        final Player p = (Player) sender;
        if (argument.length == 0) {
            this.usage(p, "tpo <player> <otherplayer>");
            return true;
        }
        final Player t = Bukkit.getPlayer(argument[0]);
        if (t == null) {
            this.noPlayer(sender);
            return true;
        }
        if (argument.length == 1) {
            p.teleport(t.getLocation(), PlayerTeleportEvent.TeleportCause.COMMAND);
            this.sendMessage(p, this.getMessage(this.getLanguage(p), "TELEPORT"));
            return true;
        }
        final Player t2 = Bukkit.getPlayer(argument[1]);
        if (t2 == null) {
            this.noPlayer(sender);
            return true;
        }
        t.teleport(t2.getLocation(), PlayerTeleportEvent.TeleportCause.COMMAND);
        this.sendMessage(t, this.getMessage(this.getLanguage(t), "TELEPORT"));
        return true;
    }
}
