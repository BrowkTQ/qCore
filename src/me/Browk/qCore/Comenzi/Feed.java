package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class Feed implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.feed")) {
            this.noPermission(sender);
            return true;
        }
        if (argument.length == 0) {
            if (!(sender instanceof Player)) {
                this.noConsole(sender);
                return true;
            }
            final Player p = (Player) sender;
            this.sendMessage(p, this.getMessage(this.getLanguage(p), "FEED"));
            p.setFoodLevel(20);
            return true;
        } else {
            if (!sender.hasPermission("essentials.feed.others")) {
                this.noPermission(sender);
                return true;
            }
            final Player t = Bukkit.getPlayer(argument[0]);
            if (t == null) {
                this.noPlayer(sender);
                return true;
            }
            this.sendMessage(t, this.getMessage(this.getLanguage(t), "FEED"));
            t.setFoodLevel(20);
            this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "FEED PLAYER").replace("<PLAYER>", t.getName()));
            return true;
        }
    }
}
