package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class Top implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.top")) {
            this.noPermission(sender);
            return true;
        }
        if (!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        final Player p = (Player) sender;
        final World w = p.getWorld();
        final double x = p.getLocation().getX();
        final double z = p.getLocation().getZ();
        final double y = p.getWorld().getHighestBlockYAt((int) x, (int) z);
        final Location l = new Location(w, x, y, z);
        p.teleport(l);
        this.sendMessage(p, this.getMessage(this.getLanguage(p), "TELEPORT"));
        return true;
    }
}
