package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Utile.*;
import me.Browk.qCore.Language.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class Exp implements CommandExecutor, Utile, Message {
    public boolean onCommand(final CommandSender sender, final Command comanda, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.exp")) {
            this.noPermission(sender);
            return true;
        }
        if (argument.length == 0) {
            if (!(sender instanceof Player)) {
                this.noConsole(sender);
                return true;
            }
            final Player p = (Player) sender;
            this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "EXP").replace("<XP>", new StringBuilder(String.valueOf(p.getExp())).toString()).replace("<PLAYER>", p.getName()));
            return true;
        } else if (argument[0].equalsIgnoreCase("give")) {
            if (argument.length != 3) {
                this.usage(sender, "exp give <player> <xp>");
                return true;
            }
            final Player t = Bukkit.getPlayer(argument[1]);
            final int xp = this.isNumber(argument[2]);
            if (t == null) {
                this.noPlayer(sender);
                return true;
            }
            t.setExp(t.getExp() + xp);
            this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "EXP GIVE").replace("<XP>", new StringBuilder(String.valueOf(xp)).toString()).replace("<PLAYER>", t.getName()));
            return true;
        } else if (argument[0].equalsIgnoreCase("take")) {
            if (argument.length != 3) {
                this.usage(sender, "exp take <player> <xp>");
                return true;
            }
            final Player t = Bukkit.getPlayer(argument[1]);
            final int xp = this.isNumber(argument[2]);
            if (t == null) {
                this.noPlayer(sender);
                return true;
            }
            t.setExp(t.getExp() - xp);
            this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "EXP TAKE").replace("<XP>", new StringBuilder(String.valueOf(xp)).toString()).replace("<PLAYER>", t.getName()));
            return true;
        } else {
            if (!argument[0].equalsIgnoreCase("reset")) {
                return true;
            }
            if (argument.length != 2) {
                this.usage(sender, "exp reset <player>");
                return true;
            }
            final Player t = Bukkit.getPlayer(argument[1]);
            if (t == null) {
                this.noPlayer(sender);
                return true;
            }
            t.setExp(0.0f);
            this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "EXP RESET").replace("<PLAYER>", t.getName()));
            return true;
        }
    }
}