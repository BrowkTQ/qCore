package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.*;

public class Invsee implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.invsee")) {
            this.noPermission(sender);
            return true;
        }
        if (!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        final Player p = (Player) sender;
        if (argument.length == 0) {
            Inventory inv = Bukkit.createInventory(null, 45, this.replace("&8Invsee " + p.getName() + ":"));
            inv.setContents(p.getInventory().getContents());
            p.openInventory(inv);
            return true;
        }
        final Player t = Bukkit.getPlayer(argument[0]);
        if (t == null) {
            this.noPlayer(sender);
            return true;
        }
        if(p.hasPermission("essentials.invsee.moveitems")) {
            p.openInventory((Inventory) t.getInventory());
            return true;
        } else {
            Inventory inv = Bukkit.createInventory(null, 45, this.replace("&8Invsee " + t.getName() + ":"));
            inv.setContents(t.getInventory().getContents());
            p.openInventory(inv);
            return true;
        }
    }
}
