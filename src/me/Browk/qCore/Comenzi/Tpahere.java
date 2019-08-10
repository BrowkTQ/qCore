package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import me.Browk.qCore.Custom.*;

public class Tpahere implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.tpahere")) {
            this.noPermission(sender);
            return true;
        }
        if (!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        final Player p = (Player) sender;
        if (argument.length == 0) {
            this.usage(p, "tpahere <player>");
            return true;
        }
        final Player t = Bukkit.getPlayer(argument[0]);
        if (t == null) {
            this.noPlayer(sender);
            return true;
        }
        final Jucator tj = this.getJucator(t);
        if (tj.isTpToggle()) {
            this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "TP OFF").replace("<TARGET>", t.getName()));
            return true;
        }
        tj.setTp_time(30);
        tj.setTp(p.getName());
        tj.setTpa(true);
        this.sendMessage(t, this.getMessage(this.getLanguage(t), "TPAHERE").replace("<PLAYER>", p.getName()));
        this.sendMessage(p, this.getMessage(this.getLanguage(p), "REQUEST SENT"));
        return true;
    }
}
