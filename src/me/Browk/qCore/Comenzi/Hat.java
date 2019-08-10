package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;

public class Hat implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.hat")) {
            this.noPermission(sender);
            return true;
        }
        if (!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        final Player p = (Player) sender;
        if (p.getItemInHand() != null) {
            final ItemStack i = p.getInventory().getHelmet();
            p.getInventory().setHelmet(p.getItemInHand());
            p.getInventory().setItemInHand(i);
            this.sendMessage(p, this.getMessage(this.getLanguage(p), "HAT"));
            return true;
        }
        this.sendMessage(p, this.getMessage(this.getLanguage(p), "NO ITEM"));
        return true;
    }
}
