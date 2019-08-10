package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Utile.*;
import me.Browk.qCore.Language.*;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.command.*;
import org.bukkit.*;
import org.bukkit.entity.*;

public class AdminChat implements CommandExecutor, Utile, Message {
    public boolean onCommand(final CommandSender sender, final Command comanda, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.adminchat")) {
            if(!(sender instanceof Player)) {
                this.noConsole(sender);
                return true;
            }
            Player p = (Player) sender;
            if (argument.length < 1) {
                this.usage(sender, "adminchat <message>");
                return true;
            }
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < argument.length; ++i) {
                sb.append(argument[i]).append(" ");
            }
            String msg = sb.toString();
            p.chat("/helpop " + msg);
            return true;
        }
        if(!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        Player p = (Player) sender;
        if (argument.length < 1) {
            this.usage(sender, "adminchat <message>");
            return true;
        }
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < argument.length; ++i) {
            sb.append(argument[i]).append(" ");
        }
        String msg = sb.toString();
        for (final Player o : Bukkit.getOnlinePlayers()) {
            if (o.hasPermission("essentials.adminchat")) {
                this.sendMessage(o, PlaceholderAPI.setPlaceholders(p, this.getMessage(this.getLanguage(o), "STAFFCHAT").replace("<PREFIX>", "%powerfulperms_firstprefix_Staff_Others_Donator_default%")).replace("<MESSAGE>", msg).replace("<PLAYER>", sender.getName()));
            }
        }
        return true;
    }
}
