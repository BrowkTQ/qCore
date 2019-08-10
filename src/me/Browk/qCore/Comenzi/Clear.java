package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.*;

public class Clear implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.clearinventory")) {
            this.noPermission(sender);
            return true;
        }
        if (argument.length == 0) {
            if (!(sender instanceof Player)) {
                this.noConsole(sender);
                return true;
            }
            final Player p = (Player) sender;
            this.sendMessage(p, this.getMessage(this.getLanguage(p), "CLEAR").replace("<PLAYER>", p.getName()));
            p.getInventory().setArmorContents((ItemStack[]) null);
            p.getInventory().clear();
            return true;
        } else {
            final Player t = Bukkit.getPlayer(argument[0]);
            if (t == null) {
                this.noPlayer(sender);
                return true;
            }
            this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "CLEAR").replace("<PLAYER>", t.getName()));
            t.getInventory().setArmorContents((ItemStack[]) null);
            t.getInventory().clear();
            return true;
        }
    }
}
