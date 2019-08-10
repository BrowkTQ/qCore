package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Utile.*;
import me.Browk.qCore.Language.*;
import org.bukkit.command.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import me.Browk.qCore.Custom.*;

public class Msg implements CommandExecutor, Utile, Message {
    public boolean onCommand(final CommandSender sender, final Command comanda, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.msg")) {
            this.noPermission(sender);
            return true;
        }
        if (argument.length < 2) {
            this.usage(sender, "msg <player> <message>");
            return true;
        }
        final StringBuilder sb = new StringBuilder();
        for (int i = 1; i < argument.length; ++i) {
            sb.append(argument[i]).append(" ");
        }
        final String msg = sb.toString();
        final String target = argument[0];
        final Player t = Bukkit.getPlayer(target);
        if (t == null) {
            this.noPlayer(sender);
            return true;
        }
        final Jucator tj = this.getJucator(t);
        if (tj.isMsgToggle()) {
            this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "MSG OFF").replace("<TARGET>", t.getName()));
            return true;
        }
        if (tj.getIgnore().contains(sender.getName())) {
            this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "IGNORE MSG"));
            return true;
        }
        tj.setReply(sender.getName());
        if (sender instanceof Player) {
            this.getJucator((Player) sender).setReply(t.getName());
        }
        t.sendMessage(String.valueOf(this.replace(new StringBuilder("&8[&6").append(sender.getName()).append("&8 -> &fmine&8] &f").toString())) + msg);
        sender.sendMessage(String.valueOf(this.replace(new StringBuilder("&8[&f").append("eu &8-> &6").append(t.getName()).append("&8] &f").toString())) + msg);
        for (final Player o : Bukkit.getOnlinePlayers()) {
            if (this.getJucator(o).isSpy()) {
                this.sendMessage(o, String.valueOf(this.replace(new StringBuilder("&cSpy: &6").append(sender.getName()).append(" &8>> &6").append(t.getName()).append("&8: &7").toString())) + msg);
            }
        }
        return true;
    }
}
