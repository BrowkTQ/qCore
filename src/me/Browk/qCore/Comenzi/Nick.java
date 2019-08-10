package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import me.Browk.qCore.Custom.*;

public class Nick implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.nick")) {
            this.noPermission(sender);
            return true;
        }
        if (argument.length == 0) {
            this.usage(sender, "nick <name>");
            return true;
        }
        if (argument.length == 1) {
            if (!(sender instanceof Player)) {
                this.noConsole(sender);
                return true;
            }
            final Player p = (Player) sender;
            final Jucator j = this.getJucator(p);
            if (!argument[0].equalsIgnoreCase(p.getName()) && Bukkit.getPlayerExact(ChatColor.stripColor(this.replace(argument[0]))) != null && Bukkit.getPlayerExact(ChatColor.stripColor(this.replace(argument[0]))).getName() != sender.getName()) {
                this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "YOURSELF"));
                return true;
            }
            if (argument[0].equalsIgnoreCase("off")) {
                j.setNick(p.getName());
            } else {
                j.setNick(argument[0]);
            }
            this.sendMessage(p, this.getMessage(this.getLanguage(p), "NICK").replace("<NICK>", this.replace(argument[0])).replace("<PLAYER>", p.getName()));
            return true;
        } else {
            if (!sender.hasPermission("essentials.nick.others")) {
                this.noPermission(sender);
                return true;
            }
            final Player t = Bukkit.getPlayer(argument[1]);
            if (t == null) {
                this.noPlayer(sender);
                return true;
            }
            final Jucator j = this.getJucator(t);
            j.setNick(argument[0]);
            this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "NICK").replace("<NICK>", this.replace(argument[0])).replace("<PLAYER>", t.getName()));
            return true;
        }
    }
}
