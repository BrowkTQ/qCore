package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Utile.*;
import me.Browk.qCore.Language.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

public class Killall implements CommandExecutor, Utile, Message {
    public boolean onCommand(final CommandSender sender, final Command comanda, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.killall")) {
            this.noPermission(sender);
            return true;
        }
        if (!(sender instanceof Player)) {
            this.noConsole(sender);
            return true;
        }
        final Player p = (Player) sender;
        if (argument.length != 1) {
            this.usage(sender, "killall <mobs|animals|villagers|items>");
            return true;
        }
        if(argument[0].equalsIgnoreCase("mobs")) {
            int x = 0;
            for (final Entity d : p.getWorld().getEntities()) {
                if (d.getType() == EntityType.SKELETON || d.getType() == EntityType.SPIDER || d.getType() == EntityType.CREEPER || d.getType() == EntityType.SNOWMAN || d.getType() == EntityType.BLAZE || d.getType() == EntityType.CAVE_SPIDER || d.getType() == EntityType.WITCH || d.getType() == EntityType.WITHER || d.getType() == EntityType.ENDERMAN || d.getType() == EntityType.ZOMBIE || d.getType() == EntityType.GHAST || d.getType() == EntityType.GIANT || d.getType() == EntityType.SLIME || d.getType() == EntityType.SILVERFISH || d.getType() == EntityType.MAGMA_CUBE || d.getType() == EntityType.PIG_ZOMBIE) {
                    d.remove();
                    ++x;
                }
            }
            this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "KILLALL").replace("<X>", new StringBuilder(String.valueOf(x)).toString()).replace("<TYPE>", "monstri"));
            return true;
        }
        if(argument[0].equalsIgnoreCase("animals")) {
            int x = 0;
            for (final Entity d : p.getWorld().getEntities()) {
                if (d.getType() == EntityType.CHICKEN || d.getType() == EntityType.COW || d.getType() == EntityType.PIG || d.getType() == EntityType.SHEEP || d.getType() == EntityType.RABBIT || d.getType() == EntityType.HORSE) {
                    d.remove();
                    ++x;
                }
            }
            this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "KILLALL").replace("<X>", new StringBuilder(String.valueOf(x)).toString()).replace("<TYPE>", "animale"));
            return true;
        }
        if(argument[0].equalsIgnoreCase("villagers")) {
            int x = 0;
            for (final Entity d : p.getWorld().getEntities()) {
                if (d.getType() == EntityType.VILLAGER) {
                    d.remove();
                    ++x;
                }
            }
            this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "KILLALL").replace("<X>", new StringBuilder(String.valueOf(x)).toString()).replace("<TYPE>", "villageri"));
            return true;
        }
        if(argument[0].equalsIgnoreCase("items")) {
            int x = 0;
            for (final Entity d : p.getWorld().getEntities()) {
                if (d.getType() == EntityType.DROPPED_ITEM || d.getType() == EntityType.EGG || d.getType() == EntityType.BOAT) {
                    d.remove();
                    ++x;
                }
            }
            this.sendMessage(sender, this.getMessage(this.getLanguage(sender), "KILLALL").replace("<X>", new StringBuilder(String.valueOf(x)).toString()).replace("<TYPE>", "obiecte"));
            return true;
        }
        this.usage(sender, "killall <mobs|animals|villagers|items>");
        return true;
    }
}
