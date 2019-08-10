package me.Browk.qCore.Evenimente;

import me.Browk.qCore.Utile.*;
import java.util.*;
import java.net.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.*;
import org.bukkit.entity.*;

public class LoginEvent implements Utile, Listener {
    public HashMap<UUID, InetAddress> ad;

    public LoginEvent() {
        this.ad = new HashMap<UUID, InetAddress>();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerLogin(final PlayerLoginEvent e) {
        final InetAddress addr = e.getRealAddress();
        if (!this.allow(addr)) {
            this.ad.put(e.getPlayer().getUniqueId(), addr);
            e.setKickMessage(this.replace("&cTrebuie sa folosesti DNS-ul: &4play.minecraft-romania.ro"));
            e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
            return;
        }
        switch (e.getResult()) {
            case KICK_FULL:
                Player player = e.getPlayer();
                if (player.hasPermission("essentials.joinfullserver")) {
                    e.allow();
                    return;
                }
                break;
            default:
                break;
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onAsyncLogin(final AsyncPlayerPreLoginEvent e) {
        final Player player = Bukkit.getServer().getPlayerExact(e.getName().toLowerCase());
        if (player == null) {
            return;
        }
        if (player.isOnline()) {
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, this.replace("&cEsti deja conectat pe server!"));
        }
    }
}
