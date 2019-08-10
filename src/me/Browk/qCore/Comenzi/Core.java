package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Utile.*;
import me.Browk.qCore.Language.*;
import org.bukkit.command.*;
import org.apache.commons.lang3.*;
import java.io.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.configuration.file.*;

public class Core implements CommandExecutor, Utile, Message {
    public boolean onCommand(final CommandSender sender, final Command comanda, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.admin")) {
            this.noPermission(sender);
            return true;
        }
        if (argument.length == 0) {
            sender.sendMessage(this.replace("&8&m--+------------------------------------+--&r"));
            sender.sendMessage(StringUtils.center(this.replace("&fqCore &av" + this.getInstance().getDescription().getVersion()), 68));
            sender.sendMessage(this.replace(" "));
            sender.sendMessage(this.replace("&e&l>> &fAdmin commands:"));
            sender.sendMessage(this.replace("&6* &f/core reload"));
            return true;
        }
        if (argument[0].equalsIgnoreCase("reload")) {
            final File messageFile = new File(this.getInstance().getDataFolder(), "customization.yml");
            final FileConfiguration message = (FileConfiguration) YamlConfiguration.loadConfiguration(messageFile);
            try {
                this.getInstance().reloadConfig();
                message.load(messageFile);
                sender.sendMessage(this.replace("&aConfig reload completed."));
            } catch (Exception e) {
                sender.sendMessage(this.replace("&cConfig reload failed."));
            }
            return true;
        }
        return true;
    }
}
