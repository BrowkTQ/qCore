package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import me.Browk.qCore.Custom.*;

public class Ignore implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.ignore")) {
            this.noPermission(sender);
            return true;
        }
        if (!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        final Player p = (Player) sender;
        if (argument.length == 0) {
            this.usage(p, "ignore <player>");
            return true;
        }
        final String t = argument[0];
        if (t.equalsIgnoreCase(p.getName())) {
            this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "YOURSELF"));
            return true;
        }
        final Jucator j = this.getJucator(p);
        if (j.getIgnore().contains(" " + String.valueOf(t))) {
            this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "NO IGNORE").replace("<PLAYER>", t));
            j.removeIgnore(t);
            return true;
        }
        j.addIgnore(t);
        this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "IGNORE").replace("<PLAYER>", t));
        return true;
    }
}
