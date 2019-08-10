package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class Weather implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.weather")) {
            this.noPermission(sender);
            return true;
        }
        if (argument.length == 0) {
            this.usage(sender, "weather <sun/rain> <world>");
            return true;
        }
        if (argument.length == 1) {
            if (!(sender instanceof Player)) {
                this.noConsole(sender);
                return true;
            }
            final Player p = (Player) sender;
            if (argument[0].equalsIgnoreCase("sun")) {
                p.getWorld().setStorm(false);
                p.getWorld().setThundering(false);
                p.getWorld().setWeatherDuration(24000);
                this.sendMessage(p, this.getMessage(this.getLanguage(p), "TIME").replace("<TIME>", this.replace(argument[0])).replace("<WORLD>", p.getWorld().getName()));
                return true;
            }
            if (argument[0].equalsIgnoreCase("rain")) {
                p.getWorld().setStorm(true);
                p.getWorld().setWeatherDuration(24000);
                this.sendMessage(p, this.getMessage(this.getLanguage(p), "TIME").replace("<TIME>", this.replace(argument[0])).replace("<WORLD>", p.getWorld().getName()));
                return true;
            }
            this.usage(sender, "weather <sun/rain> <world>");
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
            if (argument[0].equalsIgnoreCase("sun")) {
                w.setStorm(false);
                w.setThundering(false);
                w.setWeatherDuration(24000);
                this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "PTIME").replace("<TIME>", this.replace(argument[0])).replace("<WORLD>", w.getName()));
                return true;
            }
            if (argument[0].equalsIgnoreCase("rain")) {
                w.setStorm(true);
                w.setWeatherDuration(24000);
                this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "PTIME").replace("<TIME>", this.replace(argument[0])).replace("<WORLD>", w.getName()));
                return true;
            }
            this.usage(sender, "weather <sun/rain> <world>");
            return true;
        }
    }
}
