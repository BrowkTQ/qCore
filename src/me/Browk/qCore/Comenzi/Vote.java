package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import me.Browk.qCore.Custom.*;

public class Vote implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.vote")) {
            this.noPermission(sender);
            return true;
        }
        if (argument.length != 0) {
            return true;
        }
        if (!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        final Player p = (Player) sender;
        final Jucator j = this.getJucator(p);
        sender.sendMessage(this.replace("&8&m--+------------------------------------+--&r"));
        this.sendMessage(p, this.getMessage(this.getLanguage(p), "VOTE MSG").replace("<VOTE>", j.getVote().toString()));
        sender.sendMessage(this.replace("&8&m--+------------------------------------+--&r"));
        return true;
    }
}
