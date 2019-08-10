package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import me.Browk.qCore.Custom.*;

public class Tptoggle implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.tptoggle")) {
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
            if (j.isTpToggle()) {
                j.setTpToggle(false);
                this.sendMessage(p, this.getMessage(this.getLanguage(p), "TP TOGGLE").replace("<PLAYER>", p.getName()).replace("<TOGGLE>", this.getMessage(this.getLanguage(p), "OFF")));
            } else {
                j.setTpToggle(true);
                this.sendMessage(p, this.getMessage(this.getLanguage(p), "TP TOGGLE").replace("<PLAYER>", p.getName()).replace("<TOGGLE>", this.getMessage(this.getLanguage(p), "ON")));
            }
            return true;
        } else {
            if (!sender.hasPermission("essentials.tptoggle.others")) {
                this.noPermission(sender);
                return true;
            }
            final Player t = Bukkit.getPlayer(argument[0]);
            if (t == null) {
                this.noPlayer(sender);
                return true;
            }
            final Jucator j = this.getJucator(t);
            if (j.isTpToggle()) {
                j.setTpToggle(false);
                this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "TP TOGGLE").replace("<PLAYER>", t.getName()).replace("<TOGGLE>", this.getMessage(this.getLanguage(sender), "OFF")));
                this.sendMessage(t, this.getMessage(this.getLanguage(t), "TP TOGGLE").replace("<PLAYER>", t.getName()).replace("<TOGGLE>", this.getMessage(this.getLanguage(t), "OFF")));
            } else {
                j.setTpToggle(true);
                this.sendMessage(t, this.getMessage(this.getLanguage(t), "TP TOGGLE").replace("<PLAYER>", t.getName()).replace("<TOGGLE>", this.getMessage(this.getLanguage(t), "ON")));
                this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "TP TOGGLE").replace("<PLAYER>", t.getName()).replace("<TOGGLE>", this.getMessage(this.getLanguage(sender), "ON")));
            }
            return true;
        }
    }
}
