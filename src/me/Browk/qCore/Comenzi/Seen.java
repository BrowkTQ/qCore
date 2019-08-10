package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import me.Browk.qCore.MySQL.*;
import org.bukkit.*;
import org.apache.commons.lang3.*;
import org.bukkit.entity.*;
import net.md_5.bungee.api.chat.*;

public class Seen implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.seen")) {
            this.noPermission(sender);
            return true;
        }
        if (argument.length == 0) {
            this.usage(sender, "seen <player>");
            return true;
        }
        final String target = argument[0];
        if (Database.getData().isRegistered(this.getInstance().server, "Player", target)) {
            final String ip = Database.getData().getString(this.getInstance().server, "IP", "Player", target);
            String laston = Database.getData().getString(this.getInstance().server, "LastOnline", "Player", target);
            if (Bukkit.getPlayer(target) == null) {
                laston = Database.getData().getString(this.getInstance().server, "LastOnline", "Player", target);
            } else {
                laston = "&aONLINE";
            }
            final String[] ipd = ip.split(":");
            final TextComponent message = new TextComponent(this.replace("&fIp: &e" + ipd[0]));
            message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://ipinfo.io/" + ipd[0]));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Mergi catre informatiile IP-ului!").create()));
            sender.sendMessage(this.replace("&8&m--+------------------------------------+--&r"));
            sender.sendMessage(StringUtils.center(this.replace("&6- &eSeen " + target + " &6-"), 68));
            if (sender instanceof Player) {
                ((Player) sender).spigot().sendMessage((BaseComponent) message);
            } else {
                sender.sendMessage(this.replace("&fIp: &e" + ipd[0]));
            }
            sender.sendMessage(this.replace("&fUltima conectare: &e" + laston));
        } else {
            this.noPlayer(sender);
        }
        return true;
    }
}
