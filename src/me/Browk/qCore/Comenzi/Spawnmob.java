package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.block.*;
import java.util.*;

public class Spawnmob implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.spawnmob")) {
            this.noPermission(sender);
            return true;
        }
        if (!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        final Player p = (Player) sender;
        final Block b = p.getTargetBlock((Set) null, 100);
        if (argument.length == 0) {
            final List<String> en = new ArrayList<String>();
            EntityType[] values;
            for (int length = (values = EntityType.values()).length, i = 0; i < length; ++i) {
                final EntityType e = values[i];
                en.add(e.toString().toLowerCase());
            }
            p.sendMessage(this.replace("&7Entity &8(&3" + en.size() + "&8)&f: &b" + this.shortList(en, "&b") + "."));
            return true;
        }
        if (EntityType.valueOf(argument[0].toUpperCase()) == null) {
            this.sendMessage(p, this.getMessage(this.getLanguage(p), "NO ENTITY"));
            return true;
        }
        b.getLocation().getWorld().spawnEntity(b.getLocation(), EntityType.valueOf(argument[0].toUpperCase()));
        this.sendMessage(p, this.getMessage(this.getLanguage(p), "SPAWNMOB"));
        return true;
    }
}
