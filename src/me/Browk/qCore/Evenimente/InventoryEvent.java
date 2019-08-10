package me.Browk.qCore.Evenimente;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.*;
import me.Browk.qCore.Custom.*;
import java.util.*;
import org.bukkit.event.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

public class InventoryEvent implements Listener, Message, Utile {
    @EventHandler
    public void onClick(final InventoryClickEvent e) {
        final Player p = (Player) e.getWhoClicked();
        if (e.getSlotType().equals((Object) InventoryType.SlotType.OUTSIDE)) {
            return;
        }
        final Jucator j = this.getJucator(p);
        if (e.getInventory().getName().startsWith(this.replace("&8Vezi pachetul ")) || e.getInventory().getName().startsWith(this.replace("&8Invsee "))) {
            e.setCancelled(true);
            return;
        }
        if (e.getInventory().getName().equalsIgnoreCase(this.replace("&0Nick color:"))) {
            if (e.getCurrentItem().getType() == Material.WOOL) {
                if (this.getJucator(p).getTokens() <= 1500) {
                    this.sendMessage(p, this.getMessage(this.getLanguage(p), "NO MONEY"));
                    p.closeInventory();
                    return;
                }
                j.setNick(String.valueOf(this.getChatColorByString(e.getCurrentItem().getDurability())) + p.getName());
                p.closeInventory();
                if (!p.hasPermission("essentials.nickcolor")) {
                    j.setTokens(j.getTokens() - 1500);
                }
            }
            if (e.getCurrentItem().getType() == Material.BARRIER) {
                p.closeInventory();
            }
            e.setCancelled(true);
        }
        if (e.getInventory().getName().equalsIgnoreCase("Giveall") && e.getCurrentItem().getType().equals((Object) Material.BARRIER)) {
            e.setCancelled(true);
            if (j.isGiveall()) {
                for (final Player o : Bukkit.getOnlinePlayers()) {
                    ItemStack[] contents;
                    for (int length = (contents = e.getInventory().getContents()).length, k = 0; k < length; ++k) {
                        final ItemStack i = contents[k];
                        if (i != null && i.getType() != Material.AIR && i.getType() != Material.BARRIER) {
                            o.getInventory().addItem(new ItemStack[]{i});
                            this.sendMessage(o, this.getMessage(this.getLanguage(o), "ITEM").replace("<AMOUNT>", new StringBuilder(String.valueOf(i.getAmount())).toString()).replace("<ITEM>", i.getType().toString().toLowerCase()).replace("<PLAYER>", p.getName()));
                        }
                    }
                }
                j.setGiveall(false);
            }
            p.closeInventory();
        }
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
