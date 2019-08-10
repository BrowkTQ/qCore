package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Utile.*;
import me.Browk.qCore.Language.*;
import org.bukkit.command.*;
import org.apache.commons.lang3.*;
import org.bukkit.*;
import me.Browk.qCore.MySQL.*;
import org.bukkit.entity.*;
import me.Browk.qCore.Custom.*;

public class Eco implements CommandExecutor, Utile, Message {
    public boolean onCommand(final CommandSender sender, final Command comanda, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.eco")) {
            this.noPermission(sender);
            return true;
        }
        if (argument.length == 0) {
            sender.sendMessage(this.replace("&8&m--+------------------------------------+--&r"));
            sender.sendMessage(StringUtils.center(this.replace("&fqCore &7v" + this.getInstance().getDescription().getVersion()), 68));
            sender.sendMessage(this.replace(" "));
            sender.sendMessage(this.replace("&e&l>> &fAdmin commands:"));
            sender.sendMessage(this.replace("&6* &f/eco give <player> <money>"));
            sender.sendMessage(this.replace("&6* &f/eco take <player> <money>"));
            sender.sendMessage(this.replace("&6* &f/eco reset <player>"));
            return true;
        }
        if (argument[0].equalsIgnoreCase("give")) {
            if (argument.length != 3) {
                this.usage(sender, "eco give <player> <money>");
                return true;
            }
            final Player t = Bukkit.getPlayer(argument[1]);
            final int money = this.isNumber(argument[2]);
            if (t != null) {
                final Jucator j = this.getJucator(t);
                j.setMoney(j.getMoney() + money);
            }
            Database.getData().setInt(this.getInstance().server, "Money", "Player", t.getName(), Database.getData().getInt(this.getInstance().server, "Money", "Player", t.getName()) + money);
            this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "ECO GIVE").replace("<MONEY>", new StringBuilder(String.valueOf(money)).toString()).replace("<PLAYER>", t.getName()));
            return true;
        } else if (argument[0].equalsIgnoreCase("take")) {
            if (argument.length != 3) {
                this.usage(sender, "eco take <player> <money>");
                return true;
            }
            final Player t = Bukkit.getPlayer(argument[1]);
            final int money = this.isNumber(argument[2]);
            if (t != null) {
                final Jucator j = this.getJucator(t);
                j.setMoney(j.getMoney() - money);
            }
            Database.getData().setInt(this.getInstance().server, "Money", "Player", t.getName(), Database.getData().getInt(this.getInstance().server, "Money", "Player", t.getName()) - money);
            this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "ECO TAKE").replace("<MONEY>", new StringBuilder(String.valueOf(money)).toString()).replace("<PLAYER>", t.getName()));
            return true;
        } else {
            if (!argument[0].equalsIgnoreCase("reset")) {
                return true;
            }
            if (argument.length != 2) {
                this.usage(sender, "eco reset <player>");
                return true;
            }
            final Player t = Bukkit.getPlayer(argument[1]);
            if (t != null) {
                final Jucator i = this.getJucator(t);
                i.setMoney(0);
            }
            Database.getData().setInt(this.getInstance().server, "Money", "Player", t.getName(), 0);
            this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "ECO RESET").replace("<PLAYER>", t.getName()));
            return true;
        }
    }
}
