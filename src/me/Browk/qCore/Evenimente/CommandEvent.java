package me.Browk.qCore.Evenimente;

import me.Browk.qCore.Utile.*;
import me.Browk.qCore.Language.*;
import org.bukkit.event.player.*;
import org.bukkit.entity.*;
import java.util.*;
import me.Browk.qCore.Custom.*;
import org.bukkit.event.*;

public class CommandEvent implements Listener, Utile, Message {
    @EventHandler
    public void onCommand(final PlayerCommandPreprocessEvent e) {
        final Player p = e.getPlayer();
        final Jucator j = this.getJucator(p);
        if (j.haveJail()) {
            e.setCancelled(true);
            this.sendMessage(p, this.getMessage(this.getLanguage(p), "JAIL CMD").replace("<TIME>", this.timeformat(j.getJailTime())));
        }
        if (e.getMessage().equalsIgnoreCase("/gmc") || e.getMessage().equalsIgnoreCase("/creative")) {
            e.setCancelled(true);
            p.performCommand("gm 1");
        }
        if (e.getMessage().equalsIgnoreCase("/gms") || e.getMessage().equalsIgnoreCase("/survival")) {
            e.setCancelled(true);
            p.performCommand("gm 0");
        }
        if (e.getMessage().equalsIgnoreCase("/gma") || e.getMessage().equalsIgnoreCase("/adventure")) {
            e.setCancelled(true);
            p.performCommand("gm 2");
        }
        if (e.getMessage().equalsIgnoreCase("/day")) {
            e.setCancelled(true);
            p.performCommand("time day");
        }
        if (e.getMessage().equalsIgnoreCase("/night")) {
            e.setCancelled(true);
            p.performCommand("time night");
        }
        if (e.getMessage().equalsIgnoreCase("/sun")) {
            e.setCancelled(true);
            p.performCommand("weather sun");
        }
        if (e.getMessage().equalsIgnoreCase("/rain")) {
            e.setCancelled(true);
            p.performCommand("weather rain");
        }
    }
}
