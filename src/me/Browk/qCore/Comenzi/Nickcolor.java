package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

public class Nickcolor implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        final Player p = (Player) sender;
        if (!sender.hasPermission("essentials.nickcolor") && this.getJucator(p).getTokens() <= 1500) {
            this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "NO MONEY"));
            return true;
        }
        final Inventory inv = Bukkit.createInventory((InventoryHolder) null, 45, this.replace("&0Nick color:"));
        inv.setItem(10, this.creazaItem(Material.WOOL, "&aLime", 5));
        inv.setItem(11, this.creazaItem(Material.WOOL, "&2Green", 13));
        inv.setItem(12, this.creazaItem(Material.WOOL, "&eYellow", 4));
        inv.setItem(13, this.creazaItem(Material.WOOL, "&6Orange", 1));
        inv.setItem(14, this.creazaItem(Material.WOOL, "&9Blue", 11));
        inv.setItem(15, this.creazaItem(Material.WOOL, "&5Purple", 10));
        inv.setItem(16, this.creazaItem(Material.WOOL, "&dPink", 2));
        inv.setItem(20, this.creazaItem(Material.WOOL, "&3Dark Aqua", 9));
        inv.setItem(21, this.creazaItem(Material.WOOL, "&bAqua", 3));
        inv.setItem(22, this.creazaItem(Material.WOOL, "&fWhite", 0));
        inv.setItem(23, this.creazaItem(Material.WOOL, "&7Light Gray", 8));
        inv.setItem(24, this.creazaItem(Material.WOOL, "&8Gray", 7));
        inv.setItem(40, this.creazaItem(Material.STAINED_GLASS_PANE, "&fYour color", this.getColorByString(this.getJucator(p).getNick())));
        inv.setItem(44, this.creazaItem(Material.BARRIER, "&cClose", 1));
        p.openInventory(inv);
        return true;
    }

    public ItemStack creazaItem(final Material material, final String nume, final int i) {
        final ItemStack item = new ItemStack(material, (int) (short) i);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(this.replace(nume));
        meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES});
        meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_UNBREAKABLE});
        meta.spigot().setUnbreakable(true);
        item.setItemMeta(meta);
        item.setDurability((short) i);
        item.setAmount(1);
        return item;
    }
}
