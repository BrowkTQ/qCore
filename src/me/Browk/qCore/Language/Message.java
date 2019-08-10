package me.Browk.qCore.Language;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import me.Browk.qCore.Custom.*;
import org.bukkit.*;
import java.util.*;

public interface Message {
    default void sendMessage(final CommandSender sender, final String msg) {
        sender.sendMessage(msg);
    }

    default void sendMessage(final Player p, final String msg) {
        p.sendMessage(msg);
    }

    default void usage(final Player p, final String msg) {
        final Jucator j = this.getJucatorM(p);
        if (j.getLanguage().equalsIgnoreCase("RO")) {
            p.sendMessage(this.getMessage("RO", "USAGE").replace("<COMMAND>", msg));
        } else {
            p.sendMessage(this.getMessage("EN", "USAGE").replace("<COMMAND>", msg));
        }
    }

    default void usage(final CommandSender sender, final String msg) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(this.getMessage("EN", "USAGE").replace("<COMMAND>", msg));
        } else {
            final Player p = (Player) sender;
            final Jucator j = this.getJucatorM(p);
            if (j.getLanguage().equalsIgnoreCase("RO")) {
                p.sendMessage(this.getMessage("RO", "USAGE").replace("<COMMAND>", msg));
            } else {
                p.sendMessage(this.getMessage("EN", "USAGE").replace("<COMMAND>", msg));
            }
        }
    }

    default void noPlayer(final Player p) {
        final Jucator j = this.getJucatorM(p);
        if (j.getLanguage().equalsIgnoreCase("RO")) {
            p.sendMessage(this.getMessage("RO", "NO PLAYER"));
        } else {
            p.sendMessage(this.getMessage("EN", "NO PLAYER"));
        }
    }

    default void noPlayer(final CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(this.getMessage("EN", "NO PLAYER"));
        } else {
            final Player p = (Player) sender;
            final Jucator j = this.getJucatorM(p);
            if (j.getLanguage().equalsIgnoreCase("RO")) {
                p.sendMessage(this.getMessage("RO", "NO PLAYER"));
            } else {
                p.sendMessage(this.getMessage("EN", "NO PLAYER"));
            }
        }
    }

    default void broadcast(final String msg) {
        Bukkit.broadcastMessage(msg.replace("&", "\u010f\u017c\u02dd"));
    }

    default void noConsole(final CommandSender sender) {
        sender.sendMessage(this.getMessage("EN", "CONSOLE"));
    }

    default void noPermission(final CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(this.getMessage("EN", "NO PERMISSION"));
        } else {
            final Player p = (Player) sender;
            final Jucator j = this.getJucatorM(p);
            if (j.getLanguage().equalsIgnoreCase("RO")) {
                p.sendMessage(this.getMessage("RO", "NO PERMISSION"));
            } else {
                p.sendMessage(this.getMessage("EN", "NO PERMISSION"));
            }
        }
    }

    default String getLanguage(final CommandSender sender) {
        if (!(sender instanceof Player)) {
            return "EN";
        }
        final Player p = (Player) sender;
        final Jucator j = this.getJucatorM(p);
        if (j.getLanguage().equalsIgnoreCase("RO")) {
            return "RO";
        }
        return "EN";
    }

    default String getLanguage(final Player p) {
        final Jucator j = this.getJucatorM(p);
        if (j.getLanguage().equalsIgnoreCase("RO")) {
            return "RO";
        }
        return "EN";
    }

    default String getMessage(final String lang, final String path) {
        return Language.l.getMessage(lang, path);
    }

    default List<String> getListMessage(final String lang, final String path) {
        return Language.l.getListMessage(lang, path);
    }

    default Jucator getJucatorM(final Player p) {
        for (final Jucator j : Jucator.jucatorObjects) {
            if (j.getPlayer() == p) {
                return j;
            }
        }
        Jucator j = new Jucator(p);
        return j;
    }
}
