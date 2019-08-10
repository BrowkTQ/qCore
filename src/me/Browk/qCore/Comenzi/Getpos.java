package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class Getpos implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.getpos")) {
            this.noPermission(sender);
            return true;
        }
        if (argument.length == 0) {
            if (!(sender instanceof Player)) {
                this.noConsole(sender);
                return true;
            }
            final Player p = (Player) sender;
            this.sendMessage(p, this.getMessage(this.getLanguage(p), "GETPOS").replace("<Z>", new StringBuilder(String.valueOf(this.aranjeazDouble(p.getLocation().getZ()))).toString()).replace("<Y>", new StringBuilder(String.valueOf(this.aranjeazDouble(p.getLocation().getY()))).toString()).replace("<X>", new StringBuilder(String.valueOf(this.aranjeazDouble(p.getLocation().getX()))).toString()).replace("<PLAYER>", p.getName()));
            return true;
        } else {
            final Player t = Bukkit.getPlayer(argument[0]);
            if (t == null) {
                this.noPlayer(sender);
                return true;
            }
            this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "GETPOS").replace("<Z>", new StringBuilder(String.valueOf(this.aranjeazDouble(t.getLocation().getZ()))).toString()).replace("<Y>", new StringBuilder(String.valueOf(this.aranjeazDouble(t.getLocation().getY()))).toString()).replace("<X>", new StringBuilder(String.valueOf(this.aranjeazDouble(t.getLocation().getX()))).toString()).replace("<PLAYER>", t.getName()));
            return true;
        }
    }
}
