package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import java.util.*;
import me.Browk.qCore.Custom.*;

public class Kit implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.kit")) {
            this.noPermission(sender);
            return true;
        }
        if (argument.length == 0) {
            if (!(sender instanceof Player)) {
                this.noConsole(sender);
                return true;
            }
            final Player p = (Player) sender;
            final List<String> l = new ArrayList<String>();
            for (final String k : this.getKits()) {
                if (p.hasPermission("essentials.kits." + k)) {
                    l.add(this.frumos(k));
                }
            }
            if (l.isEmpty()) {
                this.sendMessage(p, this.getMessage(this.getLanguage(p), "NO KIT"));
                return true;
            }
            p.sendMessage(this.replace("&fKits &8(&6" + l.size() + "&8)&f: &e" + this.shortList(l, "&e").toLowerCase() + "."));
            return true;
        } else if (argument[0].equalsIgnoreCase("create")) {
            if (!sender.hasPermission("essentials.kit.create")) {
                this.noPermission(sender);
                return true;
            }
            if (!(sender instanceof Player)) {
                this.noConsole(sender);
                return true;
            }
            final Player p = (Player) sender;
            if (argument.length != 3) {
                this.usage(p, "kit create <name> <time>");
                return true;
            }
            final String name = argument[1];
            final Integer time = this.isNumber(argument[2]);
            this.createKit(p, name, time);
            this.sendMessage(p, this.getMessage(this.getLanguage(p), "CREATE KIT").replace("<KIT>", name));
            return true;
        } else {
            if (argument.length != 2) {
                if (argument.length == 1) {
                    if (!(sender instanceof Player)) {
                        this.noConsole(sender);
                        return true;
                    }
                    final Player p = (Player) sender;
                    final Jucator j = this.getJucator(p);
                    final String name2 = argument[0];
                    if (!this.getKits().contains(name2.toLowerCase())) {
                        this.sendMessage(p, this.getMessage(this.getLanguage(p), "NO KIT").replace("<KIT>", name2));
                        return true;
                    }
                    if (!p.hasPermission("essentials.kit.cooldown.bypass")) {
                        if (j.getKitTime(name2) == -1) {
                            this.sendMessage(p, this.getMessage(this.getLanguage(p), "BYE KIT").replace("<KIT>", name2));
                            return true;
                        }
                        if (System.currentTimeMillis() < j.getKitTimeL(name2)) {
                            this.sendMessage(p, this.getMessage(this.getLanguage(p), "NEXT KIT").replace("<TIME>", this.timeformat(j.getKitTime(name2))).replace("<KIT>", name2));
                            return true;
                        }
                    }
                    if (!p.hasPermission("essentials.kits." + name2)) {
                        this.noPermission((CommandSender) p);
                        return true;
                    }
                    this.giveKit(p, name2);
                    this.sendMessage(p, this.getMessage(this.getLanguage(p), "KIT").replace("<KIT>", name2));
                }
                return true;
            }
            final String name3 = argument[0];
            final String target = argument[1];
            final Player t = Bukkit.getPlayer(target);
            if (t == null) {
                this.noPlayer(sender);
                return true;
            }
            if (!sender.hasPermission("essentials.kit.others")) {
                this.noPermission(sender);
                return true;
            }
            if (!this.getKits().contains(name3.toLowerCase())) {
                this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "NO KIT").replace("<KIT>", name3));
                return true;
            }
            this.giveKit(t, name3);
            this.sendMessage(t, this.getMessage(this.getLanguage(t), "KIT").replace("<KIT>", name3));
            return true;
        }
    }
}
