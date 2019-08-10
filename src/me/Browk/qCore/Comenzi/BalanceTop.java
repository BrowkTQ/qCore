package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;

public class BalanceTop implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.balancetop")) {
            this.noPermission(sender);
            return true;
        }
        if (argument.length == 0) {
            this.balanceTop(sender, 0);
            return true;
        }
        final int page = this.isNumber(argument[0]);
        if (page > 10) {
            return true;
        }
        this.balanceTop(sender, page - 1);
        return true;
    }
}
