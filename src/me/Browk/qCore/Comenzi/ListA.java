package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class ListA implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.list")) {
            this.noPermission(sender);
            return true;
        }
        String msg = this.getMessage(this.getLanguage(sender), "LIST");
        if (this.getStaff((Player) sender).startsWith("PULA")) {
            msg = msg.replace("<STAFF>", "-");
        }
        if (this.getDonor((Player) sender).startsWith("PULA")) {
            msg = msg.replace("<DONOR>", "-");
        }
        this.sendMessage(sender, msg.replace("<STAFF>", this.getStaff((Player) sender)).replace("<DONOR>", this.getDonor((Player) sender)).replace("<PLAYERS>", new StringBuilder(String.valueOf(Bukkit.getOnlinePlayers().size())).toString()));
        return true;
    }
}
