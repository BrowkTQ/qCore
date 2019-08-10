package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import me.Browk.qCore.Custom.*;

public class Tpdeny implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.tpdeny")) {
            this.noPermission(sender);
            return true;
        }
        if (!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        final Player p = (Player) sender;
        final Jucator j = this.getJucator(p);
        final Player t = Bukkit.getPlayer(j.getTp());
        if (t == null) {
            this.sendMessage(p, this.getMessage(this.getLanguage(p), "NO TP"));
            return true;
        }
        this.sendMessage(t, this.getMessage(this.getLanguage(t), "REQUEST DENY").replace("<PLAYER>", p.getName()));
        j.setTp("NU");
        j.setTp_time(0);
        return true;
    }
}
