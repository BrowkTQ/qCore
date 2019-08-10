package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Utile.*;
import me.Browk.qCore.Language.*;
import org.bukkit.command.*;
import org.apache.commons.lang3.*;
import org.bukkit.*;
import me.Browk.qCore.MySQL.*;
import org.bukkit.entity.*;
import me.Browk.qCore.Custom.*;

public class Tokens implements CommandExecutor, Utile, Message {
    public boolean onCommand(final CommandSender sender, final Command comanda, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.tokens")) {
            this.noPermission(sender);
            return true;
        }
        if (argument.length == 0) {
            sender.sendMessage(this.replace("&8&m--+------------------------------------+--&r\n") + StringUtils.center(this.replace("&fqCore &7v" + this.getInstance().getDescription().getVersion()), 68) + "\n\n" + this.replace("&e&l>> &fAdmin commands:") + "\n" + this.replace("&6* &f/tokens give <player> <money>\n") + this.replace("&6* &f/tokens take <player> <money>\n") + this.replace("&6* &f/tokens reset <player>\n"));
            return true;
        }
        if (argument[0].equalsIgnoreCase("give")) {
            if (argument.length != 3) {
                this.usage(sender, "tokens give <player> <money>");
                return true;
            }
            final Player t = Bukkit.getPlayer(argument[1]);
            final int money = this.isNumber(argument[2]);
            if (t != null) {
                final Jucator j = this.getJucator(t);
                j.setTokens(j.getTokens() + money);
            }
            Database.getData().setInt("Global", "Tokens", "Player", t.getName(), Database.getData().getInt("Global", "Tokens", "Player", t.getName()) + money);
            this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "ECO GIVE").replace("<MONEY>", new StringBuilder(String.valueOf(money)).toString()).replace("<PLAYER>", t.getName()));
            return true;
        } else if (argument[0].equalsIgnoreCase("take")) {
            if (argument.length != 3) {
                this.usage(sender, "tokens take <player> <money>");
                return true;
            }
            final Player t = Bukkit.getPlayer(argument[1]);
            final int money = this.isNumber(argument[2]);
            if (t != null) {
                final Jucator j = this.getJucator(t);
                j.setTokens(j.getTokens() - money);
            }
            Database.getData().setInt("Global", "Tokens", "Player", t.getName(), Database.getData().getInt("Global", "Tokens", "Player", t.getName()) - money);
            this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "ECO TAKE").replace("<MONEY>", new StringBuilder(String.valueOf(money)).toString()).replace("<PLAYER>", t.getName()));
            return true;
        } else {
            if (!argument[0].equalsIgnoreCase("reset")) {
                return true;
            }
            if (argument.length != 2) {
                this.usage(sender, "tokens reset <player>");
                return true;
            }
            final Player t = Bukkit.getPlayer(argument[1]);
            if (t != null) {
                final Jucator i = this.getJucator(t);
                i.setTokens(0);
            }
            Database.getData().setInt("Global", "Tokens", "Player", t.getName(), 0);
            this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "ECO RESET").replace("<PLAYER>", t.getName()));
            return true;
        }
    }
}
