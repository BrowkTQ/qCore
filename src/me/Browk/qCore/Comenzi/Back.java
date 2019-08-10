package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import me.Browk.qCore.Custom.*;

public class Back implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.back")) {
            this.noPermission(sender);
            return true;
        }
        if (!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        final Player p = (Player) sender;
        final Jucator j = this.getJucator(p);
        if (j.getBack() == null) {
            this.sendMessage(p, this.getMessage(this.getLanguage(p), "NO BACK"));
            return true;
        }
        p.teleport(j.getBack());
        this.sendMessage(p, this.getMessage(this.getLanguage(p), "BACK TP"));
        return true;
    }
}
