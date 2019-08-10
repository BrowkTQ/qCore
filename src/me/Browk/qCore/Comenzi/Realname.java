package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import net.md_5.bungee.api.*;
import java.util.*;
import me.Browk.qCore.Custom.*;

public class Realname implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.realname")) {
            this.noPermission(sender);
            return true;
        }
        if (argument.length == 0) {
            this.usage(sender, "realname <nick>");
            return true;
        }
        final String nick = this.getRealName(argument[0]);
        if (nick.equalsIgnoreCase("NU")) {
            this.noPlayer(sender);
            return true;
        }
        final Player t = Bukkit.getPlayer(nick);
        if (t == null) {
            this.noPlayer(sender);
            return true;
        }
        this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "REALNAME").replace("<NICK>", argument[0]).replace("<NAME>", t.getName()));
        return true;
    }

    public String getRealName(final String nick) {
        for (final Player p : Bukkit.getOnlinePlayers()) {
            final Jucator j = this.getJucator(p);
            if (ChatColor.stripColor(this.replace(j.getNick())).equalsIgnoreCase(nick)) {
                return p.getName();
            }
            if (ChatColor.stripColor(p.getName()).equalsIgnoreCase(nick)) {
                return p.getName();
            }
        }
        return "NU";
    }
}
