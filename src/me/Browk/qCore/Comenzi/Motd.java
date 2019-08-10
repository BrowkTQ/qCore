package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.Message;
import me.Browk.qCore.Utile.Utile;
import me.clip.placeholderapi.PlaceholderAPI;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Motd implements CommandExecutor, Utile, Message {
    public boolean onCommand(final CommandSender sender, final Command comanda, final String label, final String[] argument) {
        if (!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        Player p = (Player) sender;
        if(this.getInstance().getConfig().getBoolean("Welcome.Motd.enable")) {
            String motd = "";
            for(String s : this.getInstance().getConfig().getStringList("Welcome.Motd.message")) {
                if(!motd.equalsIgnoreCase("")) {
                    motd += "\n";
                }
                if(s.startsWith("<center>")) {
                    s = s.replace("<center>", "");
                    motd += StringUtils.center(this.replace(s), 68);
                } else {
                    if(s.equalsIgnoreCase("%blank%")) {
                        motd += s.replace("%blank%", "    &f");
                    } else {
                        motd += this.replace(s);
                    }
                }
            }
            p.sendMessage(PlaceholderAPI.setPlaceholders(p, motd));
        }
        return true;
    }
}
