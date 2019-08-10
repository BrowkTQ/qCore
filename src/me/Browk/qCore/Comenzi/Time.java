package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class Time implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.time")) {
            this.noPermission(sender);
            return true;
        }
        if (argument.length == 0) {
            this.usage(sender, "time <day/night> <world>");
            return true;
        }
        if (argument.length == 1) {
            if (!(sender instanceof Player)) {
                this.noConsole(sender);
                return true;
            }
            final Player p = (Player) sender;
            if (argument[0].equalsIgnoreCase("day")) {
                p.getWorld().setTime(6000L);
                this.sendMessage(p, this.getMessage(this.getLanguage(p), "TIME").replace("<TIME>", this.replace(argument[0])).replace("<WORLD>", p.getWorld().getName()));
                return true;
            }
            if (argument[0].equalsIgnoreCase("night")) {
                p.getWorld().setTime(15000L);
                this.sendMessage(p, this.getMessage(this.getLanguage(p), "TIME").replace("<TIME>", this.replace(argument[0])).replace("<WORLD>", p.getWorld().getName()));
                return true;
            }
            this.usage(sender, "time <day/night> <world>");
            return true;
        } else {
            if (!sender.hasPermission("essentials.time.others")) {
                this.noPermission(sender);
                return true;
            }
            final World w = Bukkit.getWorld(argument[1]);
            if (w == null) {
                this.noPlayer(sender);
                return true;
            }
            if (argument[0].equalsIgnoreCase("day")) {
                w.setTime(6000L);
                this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "PTIME").replace("<TIME>", this.replace(argument[0])).replace("<WORLD>", w.getName()));
                return true;
            }
            if (argument[0].equalsIgnoreCase("night")) {
                w.setTime(15000L);
                this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "PTIME").replace("<TIME>", this.replace(argument[0])).replace("<WORLD>", w.getName()));
                return true;
            }
            this.usage(sender, "time <day/night> <world>");
            return true;
        }
    }
}
