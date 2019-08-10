package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import java.util.*;
import me.Browk.qCore.Custom.*;

public class KitPreview implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (argument.length == 0) {
            if (!(sender instanceof Player)) {
                this.noConsole(sender);
                return true;
            }
            final Player p = (Player) sender;
            final List<String> l = new ArrayList<String>();
            for (final String k : this.getKits()) {
                l.add(this.frumos(k));
            }
            if (l.isEmpty()) {
                this.sendMessage(p, this.getMessage(this.getLanguage(p), "NO KIT"));
                return true;
            }
            p.sendMessage(this.replace("&fKits &8(&6" + l.size() + "&8)&f: &e" + this.shortList(l, "&e").toLowerCase() + "."));
            return true;
        } else if (argument.length == 1) {
            if (!(sender instanceof Player)) {
                this.noConsole(sender);
                return true;
            }
            final Player p = (Player) sender;
            final String name2 = argument[0];
            if (!this.getKits().contains(name2.toLowerCase())) {
                this.sendMessage(p, this.getMessage(this.getLanguage(p), "NO KIT").replace("<KIT>", name2));
                return true;
            }
            p.openInventory(this.previewKit(name2.toLowerCase()));
            this.sendMessage(p, this.getMessage(this.getLanguage(p), "KIT").replace("<KIT>", name2));
        }
        return true;
    }
}
