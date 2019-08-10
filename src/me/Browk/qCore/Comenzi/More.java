package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class More implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.more")) {
            this.noPermission(sender);
            return true;
        }
        if (!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        final Player p = (Player) sender;
        if (p.getItemInHand() == null && p.getItemInHand().getType() == Material.AIR) {
            this.sendMessage(p, this.getMessage(this.getLanguage(p), "NO ITEM"));
            return true;
        }
        p.getInventory().getItemInHand().setAmount(p.getInventory().getItemInHand().getMaxStackSize());
        this.sendMessage(p, this.getMessage(this.getLanguage(p), "MORE").replace("<ITEM>", p.getInventory().getItemInHand().getType().toString().toLowerCase().replace("_", " ")));
        return true;
    }
}
