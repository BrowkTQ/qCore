package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import me.Browk.qCore.Custom.*;

public class Home implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.home")) {
            this.noPermission(sender);
            return true;
        }
        if (!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        final Player p = (Player) sender;
        final Jucator j = this.getJucator(p);
        if (argument.length == 0) {
            if (j.getHomes().size() != 1 && !j.getHomes().isEmpty()) {
                if (j.getHomes().size() >= 0) {
                    this.sendMessage(p, this.replace("Case existente: &e" + this.shortList(j.getHomes(), "&e")));
                    return true;
                }
            }
            try {
                if(j.getHome(0) != null) {
                    p.teleport(j.getHome(0));
                    this.sendMessage(p, this.getMessage(this.getLanguage(p), "HOME TP"));
                }
            } catch (Exception e) {
                this.sendMessage(p, this.getMessage(this.getLanguage(p), "NO HOME"));
            }
            return true;
        }
        if (!argument[0].contains(":")) {
            final Integer home = this.isNumber(argument[0]) - 1;
            try {
                p.teleport(j.getHome(home));
                this.sendMessage(p, this.getMessage(this.getLanguage(p), "HOME TP"));
            } catch (Exception e2) {
                try {
                    final String homed = argument[0];
                    if (j.getHomes().size() == 1 || j.getHomes().isEmpty() || j.getHome(homed).getBlock() == null) {
                        this.sendMessage(p, this.replace("Case existente: &e" + this.shortList(j.getHomes(), "&e")));
                        return true;
                    }
                    p.teleport(j.getHome(homed));
                    this.sendMessage(p, this.getMessage(this.getLanguage(p), "HOME TP"));
                } catch (Exception e3) {
                    this.sendMessage(p, this.replace("Case existente: &e" + this.shortList(j.getHomes(), "&e")));
                }
                return true;
            }
            return true;
        }
        if (!sender.hasPermission("essentials.home.others")) {
            this.noPermission(sender);
            return true;
        }
        final String home2 = argument[0];
        final String[] s = home2.split(":");
        final String target = s[0];
        final Integer number = this.isNumber(s[1]);
        if (this.getHomes(target).size() == 1 || this.getHomes(target).isEmpty() || this.getHome(target, number).getBlock() == null || this.getHomes(target).size() < number + 1) {
            this.sendMessage(p, this.replace("Case existente: &e" + this.shortList(this.getHomes(target), "&e")));
            return true;
        }
        p.teleport(this.getHome(target, number));
        this.sendMessage(p, this.getMessage(this.getLanguage(p), "HOME TP"));
        return true;
    }
}
