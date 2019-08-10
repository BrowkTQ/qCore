package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class Gamemode implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.gamemode")) {
            this.noPermission(sender);
            return true;
        }
        if (argument.length <= 0 || argument.length >= 3) {
            this.usage(sender, "gamemode <mode> <player>");
            return true;
        }
        if (argument.length == 1) {
            final Player p = (Player) sender;
            if (argument[0].equalsIgnoreCase("0") | argument[0].equalsIgnoreCase("Survival") | argument[0].equalsIgnoreCase("s")) {
                p.setGameMode(GameMode.SURVIVAL);
            }
            if (argument[0].equalsIgnoreCase("1") | argument[0].equalsIgnoreCase("Creative") | argument[0].equalsIgnoreCase("c")) {
                p.setGameMode(GameMode.CREATIVE);
            }
            if (argument[0].equalsIgnoreCase("2") | argument[0].equalsIgnoreCase("Adventure") | argument[0].equalsIgnoreCase("a")) {
                p.setGameMode(GameMode.ADVENTURE);
            }
            if (argument[0].equalsIgnoreCase("3") | argument[0].equalsIgnoreCase("Spectator") | argument[0].equalsIgnoreCase("sp")) {
                p.setGameMode(GameMode.SPECTATOR);
            }
            this.sendMessage(p, this.getMessage(this.getLanguage(p), "GAMEMODE").replace("<GAMEMODE>", p.getGameMode().toString().toLowerCase()));
            return true;
        }
        if (!sender.hasPermission("essentials.gamemode.others")) {
            this.noPermission(sender);
            return true;
        }
        final Player t = Bukkit.getPlayer(argument[1]);
        if (t == null) {
            this.noPlayer(sender);
            return true;
        }
        if (argument[0].equalsIgnoreCase("0") | argument[0].equalsIgnoreCase("Survival") | argument[0].equalsIgnoreCase("s")) {
            t.setGameMode(GameMode.SURVIVAL);
        }
        if (argument[0].equalsIgnoreCase("1") | argument[0].equalsIgnoreCase("Creative") | argument[0].equalsIgnoreCase("c")) {
            t.setGameMode(GameMode.CREATIVE);
        }
        if (argument[0].equalsIgnoreCase("2") | argument[0].equalsIgnoreCase("Adventure") | argument[0].equalsIgnoreCase("a")) {
            t.setGameMode(GameMode.ADVENTURE);
        }
        if (argument[0].equalsIgnoreCase("3") | argument[0].equalsIgnoreCase("Spectator") | argument[0].equalsIgnoreCase("sp")) {
            t.setGameMode(GameMode.SPECTATOR);
        }
        this.sendMessage(t, this.getMessage(this.getLanguage(t), "GAMEMODE").replace("<GAMEMODE>", t.getGameMode().toString().toLowerCase()));
        this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "GAMEMODE PLAYER").replace("<PLAYER>", t.getName()));
        return true;
    }
}
