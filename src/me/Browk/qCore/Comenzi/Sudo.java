package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Utile.*;
import me.Browk.qCore.Language.*;
import org.bukkit.command.*;
import org.bukkit.*;
import org.bukkit.entity.*;

public class Sudo implements CommandExecutor, Utile, Message {
    public boolean onCommand(final CommandSender sender, final Command comanda, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.sudo")) {
            this.noPermission(sender);
            return true;
        }
        if (argument.length < 2) {
            this.usage(sender, "sudo <player> <message>");
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
        t.chat(msg);
        this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "SUDO").replace("<PLAYER>", t.getName()).replace("<MSG>", msg));
        return true;
    }
}
