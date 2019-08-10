package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;

public class Help implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.help")) {
            this.noPermission(sender);
            return true;
        }
        int page = 0;
        if (argument.length == 0) {
            page = 1;
        } else {
            page = this.isNumber(argument[0]);
        }
        if(this.getInstance().helpc.getStringList("Help-Pages." + page).isEmpty()) {
            this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "PAGE NOT VALID"));
            return true;
        }
        String list = "";
        for(String str : this.getInstance().helpc.getStringList("Help-Pages." + page)) {
            list += str + "\n";
        }
        this.sendMessage(sender, this.replace(this.getInstance().helpc.getString("Header").replace("<PAGE>", new StringBuilder(String.valueOf(page + 1)).toString()) + "\n" + list + this.getInstance().helpc.getString("Footer").replace("<PAGE>", new StringBuilder(String.valueOf(page + 1)).toString())));
        return true;
    }
}























