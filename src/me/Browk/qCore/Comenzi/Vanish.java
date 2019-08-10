package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import me.Browk.qCore.Custom.*;

public class Vanish implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.vanish")) {
            this.noPermission(sender);
            return true;
        }
        if (argument.length == 0) {
            if (!(sender instanceof Player)) {
                this.noConsole(sender);
                return true;
            }
            final Player p = (Player) sender;
            final Jucator j = this.getJucator(p);
            if (j.isVanish()) {
                j.setVanish(false);
                this.sendMessage(p, this.getMessage(this.getLanguage(p), "VANISH").replace("<PLAYER>", p.getName()).replace("<VANISH>", this.getMessage(this.getLanguage(p), "OFF")));
                for (final Player o : Bukkit.getOnlinePlayers()) {
                    if (!o.hasPermission("essentials.vanish.bypass")) {
                        o.showPlayer(p);
                    }
                }
            } else {
                j.setVanish(true);
                for (final Player o : Bukkit.getOnlinePlayers()) {
                    if (!o.hasPermission("essentials.vanish.bypass")) {
                        o.hidePlayer(p);
                    }
                }
                this.sendMessage(p, this.getMessage(this.getLanguage(p), "VANISH").replace("<PLAYER>", p.getName()).replace("<VANISH>", this.getMessage(this.getLanguage(p), "ON")));
            }
            return true;
        } else {
            if (!sender.hasPermission("essentials.vanish.others")) {
                this.noPermission(sender);
                return true;
            }
            final Player t = Bukkit.getPlayer(argument[0]);
            if (t == null) {
                this.noPlayer(sender);
                return true;
            }
            final Jucator j = this.getJucator(t);
            if (j.isVanish()) {
                j.setVanish(false);
                this.sendMessage(t, this.getMessage(this.getLanguage(t), "VANISH").replace("<PLAYER>", t.getName()).replace("<VANISH>", this.getMessage(this.getLanguage(t), "OFF")));
                this.sendMessage(sender, this.getMessage(this.getLanguage(t), "VANISH").replace("<PLAYER>", t.getName()).replace("<VANISH>", this.getMessage(this.getLanguage(sender), "OFF")));
                for (final Player o : Bukkit.getOnlinePlayers()) {
                    if (!o.hasPermission("essentials.vanish.bypass")) {
                        o.showPlayer(t);
                    }
                }
            } else {
                j.setVanish(true);
                for (final Player o : Bukkit.getOnlinePlayers()) {
                    if (!o.hasPermission("essentials.vanish.bypass")) {
                        o.hidePlayer(t);
                    }
                }
                this.sendMessage(sender, this.getMessage(this.getLanguage(t), "VANISH").replace("<PLAYER>", t.getName()).replace("<VANISH>", this.getMessage(this.getLanguage(sender), "ON")));
                this.sendMessage(t, this.getMessage(this.getLanguage(t), "VANISH").replace("<PLAYER>", t.getName()).replace("<VANISH>", this.getMessage(this.getLanguage(t), "ON")));
            }
            return true;
        }
    }
}
