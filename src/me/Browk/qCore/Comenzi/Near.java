package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

public class Near implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.near")) {
            this.noPermission(sender);
            return true;
        }
        if (!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        final Player p = (Player) sender;
        if (this.getNear(p).isEmpty()) {
            this.sendMessage(p, this.getMessage(this.getLanguage(p), "NEAR").replace("<TARGET>", "NONE"));
            return true;
        }
        this.sendMessage(p, this.getMessage(this.getLanguage(p), "NEAR").replace("<TARGET>", this.shortList(this.getNear(p), "&b")));
        return true;
    }
}
