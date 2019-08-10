package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

public class Speed implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.speed")) {
            this.noPermission(sender);
            return true;
        }
        if (argument.length == 0) {
            this.usage(sender, "speed <1-10>");
            return true;
        }
        if (!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        final Player p = (Player) sender;
        final int i = this.isNumber(argument[0]);
        if (i > 10 || i < 0) {
            this.usage(p, "speed <1-10>");
        } else if (p.isFlying()) {
            p.setFlySpeed(i / 10.0f);
            this.sendMessage(p, this.getMessage(this.getLanguage(p), "SPEED").replace("<SPEED>", "fly").replace("<I>", argument[0]));
        } else {
            p.setWalkSpeed(i / 10.0f);
            this.sendMessage(p, this.getMessage(this.getLanguage(p), "SPEED").replace("<SPEED>", "walk").replace("<I>", argument[0]));
        }
        return true;
    }
}
