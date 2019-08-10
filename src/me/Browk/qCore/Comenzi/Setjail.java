package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

public class Setjail implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.setjail")) {
            this.noPermission(sender);
            return true;
        }
        if (!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        final Player p = (Player) sender;
        if (argument.length != 0) {
            this.usage(p, "setjail");
            return true;
        }
        this.setJail(p);
        this.sendMessage(p, this.getMessage(this.getLanguage(p), "SET JAIL"));
        return true;
    }
}
