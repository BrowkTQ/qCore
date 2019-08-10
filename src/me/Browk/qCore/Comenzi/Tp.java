package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.event.player.*;
import me.Browk.qCore.Custom.*;

public class Tp implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.tp")) {
            this.noPermission(sender);
            return true;
        }
        if (!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        final Player p = (Player) sender;
        if (argument.length == 0) {
            this.usage(p, "tp <player> <otherPlayer>");
            return true;
        }
        final Player t = Bukkit.getPlayer(argument[0]);
        if (t == null) {
            this.noPlayer(sender);
            return true;
        }
        final Jucator tj = this.getJucator(t);
        if (tj.isTpToggle()) {
            this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "TP OFF").replace("<TARGET>", t.getName()));
            return true;
        }
        if (argument.length == 1) {
            p.teleport(t.getLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
            this.sendMessage(p, this.getMessage(this.getLanguage(p), "TELEPORT"));
            return true;
        }
        final Player t2 = Bukkit.getPlayer(argument[1]);
        if (t2 == null) {
            this.noPlayer(sender);
            return true;
        }
        final Jucator tj2 = this.getJucator(t2);
        if (tj2.isTpToggle()) {
            this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "TP OFF").replace("<TARGET>", t2.getName()));
            return true;
        }
        t.teleport(t2.getLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
        this.sendMessage(t, this.getMessage(this.getLanguage(t), "TELEPORT"));
        return true;
    }
}
