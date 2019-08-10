package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import me.Browk.qCore.Custom.*;

public class Pay implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.pay")) {
            this.noPermission(sender);
            return true;
        }
        if (!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        final Player p = (Player) sender;
        if (argument.length != 2) {
            this.usage(sender, "pay <player> <amount>");
            return true;
        }
        final Player t = Bukkit.getPlayer(argument[0]);
        if (t == null) {
            this.noPlayer(p);
            return true;
        }
        final Integer money = this.isNumber(argument[1]);
        if (money < 0) {
            this.sendMessage(p, this.getMessage(this.getLanguage(p), "POSITIVE"));
            return true;
        }
        final Jucator jp = this.getJucator(p);
        final Jucator jt = this.getJucator(t);
        if (jp.getMoney() < money) {
            this.sendMessage(p, this.getMessage(this.getLanguage(p), "NO MONEY"));
            return true;
        }
        jp.setMoney(jp.getMoney() - money);
        jt.setMoney(jt.getMoney() + money);
        this.sendMessage(p, this.getMessage(this.getLanguage(p), "PAY").replace("<PLAYER>", t.getName()).replace("<MONEY>", new StringBuilder().append(money).toString()));
        this.sendMessage(t, this.getMessage(this.getLanguage(t), "PAY RECIVE").replace("<PLAYER>", p.getName()).replace("<MONEY>", new StringBuilder().append(money).toString()));
        return true;
    }
}
