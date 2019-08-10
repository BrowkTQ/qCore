package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class Tppos implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.tppos")) {
            this.noPermission(sender);
            return true;
        }
        if (!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        final Player p = (Player) sender;
        if (argument.length != 3) {
            this.usage(p, "tppos <x> <y> <z>");
            return true;
        }
        final int x = this.isNumber(argument[0]);
        final int y = this.isNumber(argument[1]);
        final int z = this.isNumber(argument[2]);
        final Location l = new Location(p.getWorld(), (double) x, (double) y, (double) z);
        p.teleport(l);
        this.sendMessage(p, this.getMessage(this.getLanguage(p), "TELEPORT"));
        return true;
    }
}
