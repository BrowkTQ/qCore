package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.entity.*;

public class Give implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.give")) {
            this.noPermission(sender);
            return true;
        }
        if (argument.length < 2 || argument.length > 3) {
            this.usage(sender, "give <player> <item> <amount>");
            return true;
        }
        final Player t = Bukkit.getPlayer(argument[0]);
        if (t == null) {
            this.noPlayer(sender);
            return true;
        }
        try {
            if (argument.length == 2) {
                String[] a = null;
                if(argument[1].contains(":")) {
                    a = argument[1].split(":");
                } else {
                    a = (argument[1] + ":0").split(":");
                }
                final String itemName = a[0].toUpperCase();
                final Integer itemName3 = this.isNumber(a[1]);
                final ItemStack item = new ItemStack(Material.valueOf(itemName), Material.valueOf(itemName).getMaxStackSize(), itemName3.shortValue());
                this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "GIVE").replace("<AMOUNT>", new StringBuilder(String.valueOf(item.getMaxStackSize())).toString()).replace("<ITEM>", Material.valueOf(a[0].toUpperCase()).toString()).replace("<PLAYER>", t.getName()));
                t.getInventory().addItem(new ItemStack[]{item});
                return true;
            }
            if (argument.length == 3) {
                String[] a = null;
                if(argument[1].contains(":")) {
                    a = argument[1].split(":");
                } else {
                    a = (argument[1] + ":0").split(":");
                }
                final String itemName = a[0].toUpperCase();
                final Integer itemName3 = this.isNumber(a[1]);
                final Integer amount = this.isNumber(argument[2]);
                final ItemStack item2 = new ItemStack(Material.valueOf(itemName), (int) amount, itemName3.shortValue());
                this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "GIVE").replace("<AMOUNT>", new StringBuilder().append(amount).toString()).replace("<ITEM>", Material.valueOf(a[0].toUpperCase()).toString()).replace("<PLAYER>", t.getName()));
                t.getInventory().addItem(new ItemStack[]{item2});
                return true;
            }
        } catch (Exception e) {
            try {
                if (argument.length == 2) {
                    String[] a = null;
                    if(argument[1].contains(":")) {
                        a = argument[1].split(":");
                    } else {
                        a = (argument[1] + ":0").split(":");
                    }
                    final Integer itemName2 = this.isNumber(a[0]);
                    final Integer itemName3 = this.isNumber(a[1]);
                    System.out.println(itemName3.shortValue());
                    final ItemStack item2 = new ItemStack(Material.getMaterial((int) itemName2), Material.getMaterial((int) itemName2).getMaxStackSize(), itemName3.shortValue());
                    this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "GIVE").replace("<AMOUNT>", new StringBuilder(String.valueOf(item2.getMaxStackSize())).toString()).replace("<ITEM>", Material.getMaterial((int) Integer.valueOf(a[0])).toString()).replace("<PLAYER>", t.getName()));
                    t.getInventory().addItem(new ItemStack[]{item2});
                    return true;
                }
                if (argument.length == 3) {
                    String[] a = null;
                    if(argument[1].contains(":")) {
                        a = argument[1].split(":");
                    } else {
                        a = (argument[1] + ":0").split(":");
                    }
                    final Integer itemName2 = this.isNumber(a[0]);
                    final Integer itemName3 = this.isNumber(a[1]);
                    final int amount2 = this.isNumber(argument[2]);
                    System.out.println(itemName3.shortValue());
                    final ItemStack item3 = new ItemStack(Material.getMaterial((int) itemName2), (int) amount2, itemName3.shortValue());
                    this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "GIVE").replace("<AMOUNT>", new StringBuilder().append(amount2).toString()).replace("<ITEM>", Material.getMaterial((int) Integer.valueOf(a[0])).toString()).replace("<PLAYER>", t.getName()));
                    t.getInventory().addItem(new ItemStack[]{item3});
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
