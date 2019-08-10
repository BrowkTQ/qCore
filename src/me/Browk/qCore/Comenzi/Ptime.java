package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class Ptime implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.ptime")) {
            this.noPermission(sender);
            return true;
        }
        if (argument.length == 0) {
            this.usage(sender, "ptime <day/night> <player>");
            return true;
        }
        if (argument.length == 1) {
            if (!(sender instanceof Player)) {
                this.noConsole(sender);
                return true;
            }
            final Player p = (Player) sender;
            if (argument[0].equalsIgnoreCase("day")) {
                p.setPlayerTime(6000L, true);
                this.sendMessage(p, this.getMessage(this.getLanguage(p), "PTIME").replace("<TIME>", this.replace(argument[0])).replace("<PLAYER>", p.getName()));
                return true;
            }
            if (argument[0].equalsIgnoreCase("night")) {
                p.setPlayerTime(15000L, true);
                this.sendMessage(p, this.getMessage(this.getLanguage(p), "PTIME").replace("<TIME>", this.replace(argument[0])).replace("<PLAYER>", p.getName()));
                return true;
            }
            this.usage(sender, "ptime <day/night> <player>");
            return true;
        } else {
            if (!sender.hasPermission("essentials.ptime.others")) {
                this.noPermission(sender);
                return true;
            }
            final Player t = Bukkit.getPlayer(argument[1]);
            if (t == null) {
                this.noPlayer(sender);
                return true;
            }
            if (argument[0].equalsIgnoreCase("day")) {
                t.setPlayerTime(6000L, true);
                this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "PTIME").replace("<TIME>", this.replace(argument[0])).replace("<PLAYER>", t.getName()));
                return true;
            }
            if (argument[0].equalsIgnoreCase("night")) {
                t.setPlayerTime(15000L, true);
                this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "PTIME").replace("<TIME>", this.replace(argument[0])).replace("<PLAYER>", t.getName()));
                return true;
            }
            this.usage(sender, "ptime <day/night> <player>");
            return true;
        }
    }
}
