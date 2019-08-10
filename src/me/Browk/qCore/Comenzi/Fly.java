package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class Fly implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.fly")) {
            this.noPermission(sender);
            return true;
        }
        if (argument.length == 0) {
            if (!(sender instanceof Player)) {
                this.noConsole(sender);
                return true;
            }
            final Player p = (Player) sender;
            if (p.getAllowFlight()) {
                this.sendMessage(p, this.getMessage(this.getLanguage(p), "FLY").replace("<PLAYER>", p.getName()).replace("<FLY>", this.getMessage(this.getLanguage(p), "OFF")));
                p.setAllowFlight(false);
                p.setFlying(false);
            } else {
                p.setAllowFlight(true);
                p.setFlying(true);
                this.sendMessage(p, this.getMessage(this.getLanguage(p), "FLY").replace("<PLAYER>", p.getName()).replace("<FLY>", this.getMessage(this.getLanguage(p), "ON")));
            }
            return true;
        } else {
            if (!sender.hasPermission("essentials.fly.others")) {
                this.noPermission(sender);
                return true;
            }
            final Player t = Bukkit.getPlayer(argument[0]);
            if (t == null) {
                this.noPlayer(sender);
                return true;
            }
            if (t.isFlying()) {
                this.sendMessage(t, this.getMessage(this.getLanguage(t), "FLY").replace("<PLAYER>", t.getName()).replace("<FLY>", this.getMessage(this.getLanguage(t), "OFF")));
                this.sendMessage(sender, this.getMessage(this.getLanguage(t), "FLY").replace("<PLAYER>", t.getName()).replace("<FLY>", this.getMessage(this.getLanguage(sender), "OFF")));
                t.setAllowFlight(false);
                t.setFlying(false);
            } else {
                t.setAllowFlight(true);
                t.setFlying(true);
                this.sendMessage(sender, this.getMessage(this.getLanguage(t), "FLY").replace("<PLAYER>", t.getName()).replace("<FLY>", this.getMessage(this.getLanguage(sender), "ON")));
                this.sendMessage(t, this.getMessage(this.getLanguage(t), "FLY").replace("<PLAYER>", t.getName()).replace("<FLY>", this.getMessage(this.getLanguage(t), "ON")));
            }
            return true;
        }
    }
}
