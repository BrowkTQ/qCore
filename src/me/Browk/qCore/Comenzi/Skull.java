package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.entity.*;

public class Skull implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.skull")) {
            this.noPermission(sender);
            return true;
        }
        if (argument.length == 0) {
            this.usage(sender, "skull <player>");
            return true;
        }
        final String target = argument[0];
        final ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        final SkullMeta smeta = (SkullMeta) item.getItemMeta();
        smeta.setOwner(target);
        smeta.setDisplayName(this.replace("&b" + target));
        item.setItemMeta((ItemMeta) smeta);
        if (!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        final Player p = (Player) sender;
        p.getInventory().addItem(new ItemStack[]{item});
        this.sendMessage(p, this.getMessage(this.getLanguage(p), "SKULL").replace("<PLAYER>", target));
        return true;
    }
}
