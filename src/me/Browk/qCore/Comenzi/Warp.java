package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import java.util.*;

public class Warp implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.warp")) {
            this.noPermission(sender);
            return true;
        }
        if (!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        final Player p = (Player) sender;
        if (argument.length == 0) {
            final List<String> en = this.getWarps();
            p.sendMessage(this.replace("&7Warps &8(&3" + en.size() + "&8)&f: &b" + this.shortList(en, "&b") + "."));
            return true;
        }
        final String warp = argument[0];
        if (this.getWarp(warp) == null) {
            this.sendMessage(p, this.getMessage(this.getLanguage(p), "NO WARP"));
            return true;
        }
        p.teleport(this.unSeterilizeLocation(this.getWarp(warp)));
        this.sendMessage(p, this.getMessage(this.getLanguage(p), "TELEPORT"));
        return true;
    }
}
