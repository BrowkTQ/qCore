package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Utile.*;
import me.Browk.qCore.Language.*;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import me.Browk.qCore.Custom.*;

public class Helpop implements CommandExecutor, Utile, Message {
    public boolean onCommand(final CommandSender sender, final Command comanda, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.helpop")) {
            this.noPermission(sender);
            return true;
        }
        if (argument.length < 1) {
            this.usage(sender, "helpop <message>");
            return true;
        }
        if (!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        final Player p = (Player) sender;
        final Jucator j = this.getJucator(p);
        if (j.getHelpopCounter() > System.currentTimeMillis()) {
            this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "HELPOP WAIT").replace("<TIME>", new StringBuilder().append((j.getHelpopCounter() - System.currentTimeMillis())/1000).toString()));
            return true;
        }
        j.setHelpopCounter(System.currentTimeMillis() + 1000*10);
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < argument.length; ++i) {
            sb.append(argument[i]).append(" ");
        }
        final String msg = sb.toString();
        for (final Player o : Bukkit.getOnlinePlayers()) {
            if (o.hasPermission("essentials.helpop.receive")) {
                o.playSound(o.getLocation(), this.getSound(), 1.0f, 1.0f);
                this.sendMessage(o, PlaceholderAPI.setPlaceholders(p, this.getMessage(this.getLanguage(o), "HELPOP")).replace("<MESSAGE>", msg).replace("<PLAYER>", sender.getName()));
            }
        }
        this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "HELPOP SENT"));
        return true;
    }
}
