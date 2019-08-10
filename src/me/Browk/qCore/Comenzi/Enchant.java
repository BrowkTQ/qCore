package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.enchantments.*;
import java.util.*;
import org.bukkit.inventory.*;

public class Enchant implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.enchant")) {
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
        if (argument.length == 0) {
            final List<String> en = new ArrayList<String>();
            final ItemStack i = p.getItemInHand();
            try {
                Enchantment[] values;
                for (int length = (values = Enchantment.values()).length, j = 0; j < length; ++j) {
                    final Enchantment e = values[j];
                    if (this.isGoodEncahnt(i, e)) {
                        en.addAll(this.getEnchantmentName(e));
                    }
                }
            } catch (Exception e2) {
                this.sendMessage(p, this.getMessage(this.getLanguage(p), "NO ITEM"));
                return true;
            }
            p.sendMessage(this.replace("&7Enchantments &8(&3" + en.size() + "&8)&f: &b" + this.shortList(en, "&b") + "."));
            return true;
        }
        if (argument.length != 2) {
            this.usage(p, "enchant <type> <level>");
            return true;
        }
        final Enchantment ench = this.getEnchantByString(argument[0]);
        final Integer level = this.isNumber(argument[1]);
        if (ench == null || level <= 0 || level >= ench.getMaxLevel() + 1) {
            this.sendMessage(p, this.getMessage(this.getLanguage(p), "NO ENCHANT"));
            return true;
        }
        p.getItemInHand().addEnchantment(ench, (int) level);
        this.sendMessage(p, this.getMessage(this.getLanguage(p), "ENCHANT SUCCES").replace("<LEVEL>", new StringBuilder().append(level).toString()).replace("<ENCHANT>", ench.getName()).replace("<ITEM>", p.getItemInHand().getType().toString()));
        return true;
    }
}
