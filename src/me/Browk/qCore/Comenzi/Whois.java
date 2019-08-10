package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import net.md_5.bungee.api.chat.*;
import me.Browk.qCore.Custom.*;

public class Whois implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.whois")) {
            this.noPermission(sender);
            return true;
        }
        if (argument.length == 0) {
            this.usage(sender, "whois <player>");
            return true;
        }
        final Player t = Bukkit.getPlayer(argument[0]);
        if (t == null) {
            this.noPlayer(sender);
            return true;
        }
        final Jucator j = this.getJucator(t);
        final TextComponent message = new TextComponent(this.replace("Location: &e&nclick here"));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + argument[0]));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click sa te teleportezi!").create()));
        sender.sendMessage(this.replace("&8&m--+------------------------------------+--&r"));
        sender.sendMessage(this.replace("Nume: &e" + j.getNick()));
        sender.sendMessage(this.replace("Viata: &e" + t.getHealth() + "/" + t.getMaxHealth()));
        sender.sendMessage(this.replace("Saturatie: &e" + t.getFoodLevel() + "/ 20"));
        if (!(sender instanceof Player)) {
            sender.sendMessage(this.replace("Locatie: &e" + t.getWorld().getName() + " | " + t.getLocation().getX() + ", " + t.getLocation().getY() + ", " + t.getLocation().getZ()));
        } else {
            ((Player) sender).spigot().sendMessage((BaseComponent) message);
        }
        sender.sendMessage(this.replace("Bani: &e" + j.getMoney() + " ⛁"));
        sender.sendMessage(this.replace("IP Address: &e" + t.getAddress().toString().replace("/", "")));
        sender.sendMessage(this.replace("Gamemode: &e" + t.getGameMode().toString().toLowerCase()));
        sender.sendMessage(this.replace("Fly: &e" + t.getAllowFlight()));
        sender.sendMessage(this.replace("God: &e" + j.isGod()));
        sender.sendMessage(this.replace("OP: &e" + t.isOp()));
        return true;
    }
}
