package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.event.player.*;
import me.Browk.qCore.Custom.*;

public class Tphere implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.tphere")) {
            this.noPermission(sender);
            return true;
        }
        if (!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        final Player p = (Player) sender;
        if (argument.length != 1) {
            this.usage(p, "tphere <player>");
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
            t.teleport(p.getLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
            this.sendMessage(t, this.getMessage(this.getLanguage(t), "TELEPORT"));
            return true;
        }
        return true;
    }
}
