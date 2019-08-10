package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import me.Browk.qCore.Custom.*;

public class Delhome implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.delhome")) {
            this.noPermission(sender);
            return true;
        }
        if (!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        final Player p = (Player) sender;
        if (argument.length == 0) {
            this.usage(p, "delhome <name>");
            return true;
        }
        final Jucator j = this.getJucator(p);
        final String home = argument[0];
        if (!j.getHomes().contains(home)) {
            this.sendMessage(p, this.getMessage(this.getLanguage(p), "NO HOME"));
            return true;
        }
        j.deleteHome(argument[0]);
        this.sendMessage(p, this.getMessage(this.getLanguage(p), "DELETE HOME"));
        return true;
    }
}
