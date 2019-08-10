package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import me.Browk.qCore.Custom.*;

public class Sethome implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.sethome")) {
            this.noPermission(sender);
            return true;
        }
        if (!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        final Player p = (Player) sender;
        final Jucator j = this.getJucator(p);
        Integer homes = j.getHomes().size();
        if(!p.hasPermission("essentials.home.multiple") && homes > 0) {
            this.sendMessage(p, this.getMessage(this.getLanguage(p), "MAX HOMES"));
            return true;
        }
        if(homes > 0) {
            if(!p.hasPermission("essentials.home.multiple." + (homes + 1))) {
                this.sendMessage(p, this.getMessage(this.getLanguage(p), "MAX HOMES"));
                return true;
            }
        }
        if (argument.length == 0) {
            j.setHome("home");
            this.sendMessage(p, this.getMessage(this.getLanguage(p), "SET HOME"));
            return true;
        }
        final String name = argument[0];
        j.setHome(name);
        this.sendMessage(p, this.getMessage(this.getLanguage(p), "SET HOME"));
        return true;
    }
}
