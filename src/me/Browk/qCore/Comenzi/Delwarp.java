package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

public class Delwarp implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.delwarp")) {
            this.noPermission(sender);
            return true;
        }
        if (!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        final Player p = (Player) sender;
        if (argument.length == 0) {
            this.usage(p, "delwarp <name>");
            return true;
        }
        final String name = argument[0];
        if (!this.getWarps().contains(this.frumos(name))) {
            this.sendMessage(p, this.getMessage(this.getLanguage(p), "NO WARP"));
            return true;
        }
        this.deleteWarp(name);
        this.sendMessage(p, this.getMessage(this.getLanguage(p), "DELETE WARP"));
        return true;
    }
}
