package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import me.Browk.qCore.Custom.*;

public class Tpall implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.tpall")) {
            this.noPermission(sender);
            return true;
        }
        if (!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        final Player p = (Player) sender;
        if (argument.length != 0) {
            this.usage(p, "tpall");
            return true;
        }
        for (final Player t : Bukkit.getOnlinePlayers()) {
            final Jucator tj = this.getJucator(t);
            if (!tj.isTpToggle()) {
                t.teleport(p.getLocation());
                this.sendMessage(t, this.getMessage(this.getLanguage(t), "TELEPORT"));
            }
        }
        this.sendMessage(p, this.getMessage(this.getLanguage(p), "TPALL").replace("<PL>", new StringBuilder(String.valueOf(Bukkit.getOnlinePlayers().size())).toString()));
        return true;
    }
}
