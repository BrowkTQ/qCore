package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.inventory.*;

public class Item implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.item")) {
            this.noPermission(sender);
            return true;
        }
        if (!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        final Player p = (Player) sender;
        if (argument.length < 1 || argument.length > 2) {
            this.usage(sender, "item <item> <amount>");
            return true;
        }
        try {
            if (argument.length == 1) {
                final String itemName = argument[0].toUpperCase();
                final ItemStack item = new ItemStack(Material.valueOf(itemName), Material.valueOf(itemName).getMaxStackSize());
                this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "ITEM").replace("<AMOUNT>", new StringBuilder(String.valueOf(item.getMaxStackSize())).toString()).replace("<ITEM>", Material.valueOf(argument[0].toUpperCase()).toString()).replace("<PLAYER>", p.getName()));
                p.getInventory().addItem(new ItemStack[]{item});
                return true;
            }
            if (argument.length == 2) {
                final String itemName = argument[0].toUpperCase();
                final Integer amount = this.isNumber(argument[1]);
                final ItemStack item2 = new ItemStack(Material.valueOf(itemName), (int) amount);
                this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "ITEM").replace("<AMOUNT>", new StringBuilder().append(amount).toString()).replace("<ITEM>", Material.valueOf(argument[0].toUpperCase()).toString()).replace("<PLAYER>", p.getName()));
                p.getInventory().addItem(new ItemStack[]{item2});
                return true;
            }
        } catch (Exception e) {
            try {
                if (argument.length == 1) {
                    final Integer itemName2 = this.isNumber(argument[0]);
                    final ItemStack item2 = new ItemStack(Material.getMaterial((int) itemName2), Material.getMaterial((int) itemName2).getMaxStackSize());
                    this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "ITEM").replace("<AMOUNT>", new StringBuilder(String.valueOf(item2.getMaxStackSize())).toString()).replace("<ITEM>", Material.getMaterial((int) Integer.valueOf(argument[0])).toString()).replace("<PLAYER>", p.getName()));
                    p.getInventory().addItem(new ItemStack[]{item2});
                    return true;
                }
                if (argument.length == 2) {
                    final Integer itemName2 = this.isNumber(argument[0]);
                    final Integer amount2 = this.isNumber(argument[1]);
                    final ItemStack item3 = new ItemStack(Material.getMaterial((int) itemName2), (int) amount2);
                    this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "ITEM").replace("<AMOUNT>", new StringBuilder().append(amount2).toString()).replace("<ITEM>", Material.getMaterial((int) Integer.valueOf(argument[0])).toString()).replace("<PLAYER>", p.getName()));
                    p.getInventory().addItem(new ItemStack[]{item3});
                    return true;
                }
                return true;
            } catch (Exception e2) {
                this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "NO ITEM"));
            }
        }
        return true;
    }
}
