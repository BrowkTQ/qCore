package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Utile.*;
import me.Browk.qCore.Language.*;
import org.bukkit.command.*;
import org.bukkit.*;
import org.bukkit.entity.*;

public class Broadcast implements CommandExecutor, Utile, Message {
    public boolean onCommand(final CommandSender sender, final Command comanda, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.broadcast")) {
            this.noPermission(sender);
            return true;
        }
        if (argument.length < 1) {
            this.usage(sender, "broadcast <message>");
            return true;
        }
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < argument.length; ++i) {
            sb.append(argument[i]).append(" ");
        }
        final String msg = sb.toString();
        for (final Player o : Bukkit.getOnlinePlayers()) {
            this.sendMessage(o, ChatColor.translateAlternateColorCodes('&', this.getMessage(this.getLanguage(o), "BROADCAST").replace("<MESSAGE>", msg)));
        }
        return true;
    }
}
