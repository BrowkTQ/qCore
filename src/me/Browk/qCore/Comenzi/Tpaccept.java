package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import me.Browk.qCore.Custom.*;
import org.bukkit.event.player.PlayerTeleportEvent;

public class Tpaccept implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.tpaccept")) {
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
        if (!j.isTpa()) {
            t.teleport(p.getLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
            this.sendMessage(t, this.getMessage(this.getLanguage(t), "TELEPORT"));
            this.sendMessage(t, this.getMessage(this.getLanguage(t), "REQUEST ACCEPT").replace("<PLAYER>", p.getName()));
        } else if (j.isTpa()) {
            p.teleport(t.getLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
            this.sendMessage(p, this.getMessage(this.getLanguage(p), "TELEPORT"));
            this.sendMessage(t, this.getMessage(this.getLanguage(t), "REQUEST ACCEPT").replace("<PLAYER>", p.getName()));
        }
        j.setTp("NU");
        j.setTp_time(0);
        return true;
    }
}
