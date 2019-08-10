package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import java.util.*;

public class Repair implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.repair")) {
            this.noPermission(sender);
            return true;
        }
        if (!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        final Player p = (Player) sender;
        if (argument.length == 0) {
            this.usage(p, "repair <hand/all>");
            return true;
        }
        if (argument[0].equalsIgnoreCase("hand")) {
            final ItemStack items = p.getItemInHand();
            if (this.isRepairAble(items.getType())) {
                items.setDurability((short) 0);
            }
            this.sendMessage(p, this.getMessage(this.getLanguage(p), "REPAIR").replace("<REPAIR>", items.getType().toString().toLowerCase()));
            return true;
        }
        if (argument[0].equalsIgnoreCase("all")) {
            final List<String> s = new ArrayList<String>();
            ItemStack[] contents;
            for (int length = (contents = p.getInventory().getContents()).length, i = 0; i < length; ++i) {
                final ItemStack items2 = contents[i];
                if (items2 != null && this.isRepairAble(items2.getType())) {
                    items2.setDurability((short) 0);
                    s.add(items2.getType().toString().toLowerCase());
                }
            }
            ItemStack[] armorContents;
            for (int length2 = (armorContents = p.getEquipment().getArmorContents()).length, j = 0; j < length2; ++j) {
                final ItemStack items2 = armorContents[j];
                if (items2 != null && this.isRepairAble(items2.getType())) {
                    items2.setDurability((short) 0);
                    s.add(items2.getType().toString().toLowerCase());
                }
            }
            this.sendMessage(p, this.getMessage(this.getLanguage(p), "REPAIR").replace("<REPAIR>", this.shortList(s, "&b")));
            return true;
        }
        this.usage(p, "repair <hand/all>");
        return true;
    }
}
