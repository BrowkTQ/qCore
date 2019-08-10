package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import me.Browk.qCore.Custom.*;

public class Jail implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.jail")) {
            this.noPermission(sender);
            return true;
        }
        if (argument.length < 1 || argument.length > 3) {
            this.usage(sender, "jail <player> <time> <reason>");
            return true;
        }
        final Player t = Bukkit.getPlayer(argument[0]);
        if (t == null) {
            this.noPlayer(sender);
            return true;
        }
        t.teleport(this.getJail());
        final Jucator j = this.getJucator(t);
        if (j.haveJail()) {
            j.giveJail(0, "Unjail", sender.getName());
            this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "UNJAIL").replace("<PLAYER>", t.getName()));
            return true;
        }
        if (argument[1].contains("s")) {
            j.giveJail(Integer.valueOf(argument[1].replace("s", "")), argument[2], sender.getName());
        }
        if (argument[1].contains("m")) {
            j.giveJail(Integer.valueOf(argument[1].replace("m", "")) * 60, argument[2], sender.getName());
        }
        if (argument[1].contains("h")) {
            j.giveJail(Integer.valueOf(argument[1].replace("h", "")) * 3600, argument[2], sender.getName());
        }
        if (argument[1].contains("d")) {
            j.giveJail(Integer.valueOf(argument[1].replace("d", "")) * 86400, argument[2], sender.getName());
        }
        this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "ADMIN JAIL").replace("<PLAYER>", t.getName()));
        return true;
    }
}
