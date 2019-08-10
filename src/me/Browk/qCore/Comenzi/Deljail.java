package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

public class Deljail implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.deljail")) {
            this.noPermission(sender);
            return true;
        }
        if (!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        final Player p = (Player) sender;
        if (argument.length != 0) {
            this.usage(p, "deljail");
            return true;
        }
        if (this.getJail() == null) {
            this.sendMessage(p, this.getMessage(this.getLanguage(p), "NO JAIL"));
            return true;
        }
        this.deleteJail();
        this.sendMessage(p, this.getMessage(this.getLanguage(p), "DELETE JAIL"));
        return true;
    }
}
