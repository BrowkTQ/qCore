package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import me.Browk.qCore.Custom.*;
import org.bukkit.inventory.*;

public class Giveall implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.giveall")) {
            this.noPermission(sender);
            return true;
        }
        if (!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        final Player p = (Player) sender;
        final Jucator j = this.getJucator(p);
        j.setGiveall(true);
        final Inventory inv = Bukkit.createInventory((InventoryHolder) null, 45, "Giveall");
        inv.setItem(44, this.creazaItem(Material.BARRIER, "&aConfirm", 1));
        p.openInventory(inv);
        return true;
    }
}
