package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import me.Browk.qCore.Custom.*;

public class Socialspy implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.socialspy")) {
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
            if (j.isSpy()) {
                j.setSpy(false);
                this.sendMessage(p, this.getMessage(this.getLanguage(p), "SPY").replace("<PLAYER>", p.getName()).replace("<SPY>", this.getMessage(this.getLanguage(p), "OFF")));
            } else {
                j.setSpy(true);
                this.sendMessage(p, this.getMessage(this.getLanguage(p), "SPY").replace("<PLAYER>", p.getName()).replace("<SPY>", this.getMessage(this.getLanguage(p), "ON")));
            }
            return true;
        } else {
            if (!sender.hasPermission("essentials.SPY.others")) {
                this.noPermission(sender);
                return true;
            }
            final Player t = Bukkit.getPlayer(argument[0]);
            if (t == null) {
                this.noPlayer(sender);
                return true;
            }
            final Jucator j = this.getJucator(t);
            if (j.isSpy()) {
                j.setSpy(false);
                this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "SPY").replace("<PLAYER>", t.getName()).replace("<SPY>", this.getMessage(this.getLanguage(sender), "OFF")));
                this.sendMessage(t, this.getMessage(this.getLanguage(t), "SPY").replace("<PLAYER>", t.getName()).replace("<SPY>", this.getMessage(this.getLanguage(t), "OFF")));
            } else {
                j.setSpy(true);
                this.sendMessage(t, this.getMessage(this.getLanguage(t), "SPY").replace("<PLAYER>", t.getName()).replace("<SPY>", this.getMessage(this.getLanguage(t), "ON")));
                this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "SPY").replace("<PLAYER>", t.getName()).replace("<SPY>", this.getMessage(this.getLanguage(sender), "ON")));
            }
            return true;
        }
    }
}
