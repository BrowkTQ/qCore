package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import me.Browk.qCore.Custom.*;

public class Balance implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.balance")) {
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
            this.sendMessage(p, this.getMessage(this.getLanguage(p), "BALANCE").replace("<MONEY>", j.getMoney().toString()));
            return true;
        } else {
            if (!sender.hasPermission("essentials.balance.others")) {
                this.noPermission(sender);
                return true;
            }
            final Player t = Bukkit.getPlayer(argument[0]);
            if (t == null) {
                this.noPlayer(sender);
                return true;
            }
            final Jucator j = this.getJucator(t);
            this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "BALANCE PLAYER").replace("<PLAYER>", t.getName()).replace("<MONEY>", j.getMoney().toString()));
            return true;
        }
    }
}
