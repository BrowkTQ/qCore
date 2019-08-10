package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;

public class Itemdb implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.itemdb")) {
            this.noPermission(sender);
            return true;
        }
        if (!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        final Player p = (Player) sender;
        if (p.getItemInHand() != null) {
            final ItemStack i = p.getInventory().getItemInHand();
            this.sendMessage(p, this.getMessage(this.getLanguage(p), "ITEMDB").replace("<DATA>", new StringBuilder(String.valueOf(i.getDurability())).toString()).replace("<ITEMID>", new StringBuilder(String.valueOf(i.getTypeId())).toString()).replace("<TYPE>", i.getType().toString()));
            return true;
        }
        this.sendMessage(p, this.getMessage(this.getLanguage(p), "NO ITEM"));
        return true;
    }
}
