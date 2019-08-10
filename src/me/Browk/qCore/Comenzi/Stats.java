package me.Browk.qCore.Comenzi;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.command.*;
import me.Browk.qCore.MySQL.*;
import org.apache.commons.lang3.*;
import org.bukkit.*;

public class Stats implements CommandExecutor, Message, Utile {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] argument) {
        if (!sender.hasPermission("essentials.stats")) {
            this.noPermission(sender);
            return true;
        }
        if (argument.length == 0) {
            this.usage(sender, "stats <player>");
            return true;
        }
        final String target = argument[0];
        if (Database.getData().isRegistered(this.getInstance().server, "Player", target)) {
            final String ip = Database.getData().getString("Global", "Vote", "Player", target);
            sender.sendMessage(this.replace("&8&m--+------------------------------------+--&r"));
            sender.sendMessage(StringUtils.center(this.replace("&6- &fStatistici " + target + " &6-"), 68));
            sender.sendMessage(this.replace("&fVoturi: &e" + ip));
            try {
                sender.sendMessage(this.replace("Timp jucat: &e" + this.getTime(Bukkit.getPlayerExact(target).getStatistic(Statistic.PLAY_ONE_TICK))));
            } catch (Exception e) {
                sender.sendMessage(this.replace("Timp jucat: &e" + this.getTime(Database.getData().getInt(this.getInstance().server, "Time", "Player", target))));
            }
            sender.sendMessage(this.replace("&8&m--+------------------------------------+--&r"));
        } else {
            this.noPlayer(sender);
        }
        return true;
    }
}
